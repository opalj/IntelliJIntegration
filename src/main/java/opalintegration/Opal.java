package opalintegration;

import Compile.Compiler;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.*;
import globalData.GlobalData;
import java.io.*;
import java.lang.Deprecated;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.jar.JarInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.opalj.bi.AccessFlags;
import org.opalj.bi.AccessFlagsContexts;
import org.opalj.br.*;
import org.opalj.br.analyses.JavaProject;
import org.opalj.br.analyses.Project;
import org.opalj.br.instructions.*;
import org.opalj.collection.IntIterator;
import org.opalj.collection.RefIterator;
import org.opalj.collection.immutable.ConstArray;
import org.opalj.collection.immutable.RefArray;
import org.opalj.da.ClassFileReader;
import org.opalj.tac.*;
import org.opalj.value.KnownTypedValue;
import scala.Function0;
import scala.Function1;
import scala.Some;

public class Opal {
  // uriProject wird benötigt um die OpalFrameworks mit dem Project zu verbinden.
  private static Project<URL> uriProject;
  // noch nicht wichtig könnted
  private static JavaProject javaProject;
  static Function1<Method, TACode<TACMethodParameter, DUVar<KnownTypedValue>>> methodTACodeFunction;
  // INTELIJ VARS
  private static com.intellij.openapi.project.Project project;
  private static String classPath;

  // =====================================================================================
  // =====================================================================================
  // =====================================================================================

  /**
   * Returns an OPAL ClassFile for a given class file. The ClassFile from OPAL
   * contains necessary information to produce the desired bytecode and TAC
   * representations for a class file.
   *
   * @param virtualClassFile
   * @return an OPAL ClassFile
   */
  @Nullable
  private static ClassFile getClassFile(@NotNull VirtualFile virtualClassFile) {
    String filepath = virtualClassFile.getPath();
    Project<URL> uriProject = Project.apply(new File(filepath));
    ConstArray<ClassFile> classFileConstArray = uriProject.allProjectClassFiles();

    if (classFileConstArray.length() > 0) {
      System.out.println("Apply(0): " + classFileConstArray.apply(0));
      return classFileConstArray.apply(0);
    }
    // else (might be) JAR
    else if (virtualClassFile.getCanonicalPath().contains("jar!")) {
      System.out.println("getClassFile() JAR? : " + virtualClassFile.getName());
      String jarFileRoot = getJarFileRoot(virtualClassFile);
      return createClassFileFromJar(jarFileRoot);
    }
    // TODO: what to do in this case?
    else {
      return null;
    }
  }

  /**
   *
   * @param virtualClassFile - a class file (assumed to be located in a JAR)
   * @return the path to the JAR
   */
  private static String getJarFileRoot(@NotNull VirtualFile virtualClassFile) {
    String jarPathWithoutClassExtension =
        virtualClassFile.getParent().getPath()
            + File.separator
            + virtualClassFile.getNameWithoutExtension();
    // this\is\the\jarPath -> this/is/the/jarPath
    jarPathWithoutClassExtension = jarPathWithoutClassExtension.replaceAll("\\\\", "/");

    String jarFileRoot =
        jarPathWithoutClassExtension.substring(0, jarPathWithoutClassExtension.indexOf("!/"));
    return jarFileRoot;
  }



  /**
   *
   * @param jarFileRoot - path to a JAR
   * @return
   */
  // TODO: this gets called for classes we haven't clicked on as well?
  private static ClassFile createClassFileFromJar(String jarFileRoot) {
    Project<URL> uriProject = Project.apply(new File(jarFileRoot));

    Object classObj =
        Project.JavaClassFileReader(uriProject.logContext(), uriProject.config())
            .ClassFiles(
                new Function0<JarInputStream>() {
                  @Override
                  public JarInputStream apply() {
                    try {
                      return new JarInputStream(new FileInputStream(new File(jarFileRoot)));
                    } catch (IOException e) {
                      // ....
                    }

                    System.out.println("ABOUT TO RETURN NULL (1)");
                    return null;
                  }
                })
            .head()
            ._1();

    ClassFile classFileFromJar = null;
    if (classObj instanceof ClassFile) {
      classFileFromJar = (ClassFile) classObj;
    }

    return classFileFromJar;
  }

  /**
   * create the bytecode representation (of OPAL) for a given class file
   *
   * @param classFile ...
   * @return the bytecode representation as a String
   */
  private static String createBytecodeString(ClassFile classFile) {
    StringBuilder byteCodeString = new StringBuilder();
    String threeTimeNL = "\n\n\n";
    byteCodeString
        .append(AccessFlags.classFlagsToJava(classFile.accessFlags()))
        .append(" ")
        .append(classFile.fqn().replace("/", "."));
    //    ByteCodeString.append(AccessFlags.toString(classFile.accessFlags(),
    // AccessFlagsContexts.CLASS())).append( " ").append(classFile.fqn().replace("/", "."));
    if (classFile.superclassType().isDefined()) {
      byteCodeString.append(" extends ").append(classFile.superclassType().get().toJava());
    }
    if (classFile.interfaceTypes().length() > 0) {
      byteCodeString.append(" implements ");
      for (int j = 0; j < classFile.interfaceTypes().length(); j++) {
        byteCodeString.append(classFile.interfaceTypes().apply(j).toJava()).append(" ");
      }
    }
    byteCodeString
        .append("\n// Source File: ")
        .append(classFile.sourceFile().isDefined() ? classFile.sourceFile().get() : "")
        .append(" -- Version: (")
        .append(classFile.jdkVersion())
        .append(") -- Size: \n");
    // TODO Constant Pool Maybe working with DA.
    byteCodeString.append(ByteCodeFieldToString(classFile));
    RefArray<Annotation> annotations = classFile.annotations();
    for (int i = 0; i < annotations.length(); i++) {
      Annotation annotation = annotations.apply(i);
      byteCodeString.append("//").append(annotation.toJava()).append("\n");
    }
    byteCodeString.append(byteCodeMethodsToString(classFile));

    return byteCodeString.toString();
  }
  /**
   * creates a bytecode representation of the implemented methods of the passed classfile
   *
   * @param classFile ...
   * @return the methodsbytecode representation as a String
   */
  public static String byteCodeMethodsToString(ClassFile classFile) {
    StringBuilder byteCodeString = new StringBuilder();
    StringVisitor stringVisitor = new StringVisitor();
    // RefArray<Method> methods = classFile.methods();
    RefIterator<Method> methods = classFile.methodsWithBody();
    byteCodeString.append("Methods\n\n");
    // for (int j = 0; j < methods.length(); j++) {
    // Method method = methods.apply(j);
    while (methods.hasNext()) {
      Method method = methods.next();
      if (method.body().isDefined()) {
        Code body = method.body().get();
        Instruction[] instructions = body.instructions();
        byteCodeString
            .append(method.toString().replaceFirst("\\).*", ")"))
            .append(" { ")
            .append("// [size :")
            .append(body.codeSize())
            .append(" bytes, max Stack: ")
            .append(body.maxStack())
            .append("] \n");
        byteCodeString.append("\tPC\tLine\tInstruction\n");
        for (int k = 0; k < instructions.length; k++) {
          if (instructions[k] != null) {
            try {
              String instruction;
              instruction = stringVisitor.accept(instructions[k]);
              // }else(instructions[k] instanceof INVOKEVIRTUAL)
              // TODO replace \n (and \t...??) and the likes with spaces
              instruction = instruction.replaceAll("\n", " ");
              instruction = instruction.replaceAll("\t", " ");

              byteCodeString
                  .append("\t")
                  .append(k)
                  .append("\t")
                  .append(body.lineNumber(k).get().toString())
                  .append("\t\t")
                  .append(instruction)
                  .append("\n");
            } catch (NoSuchElementException e) {
              return "Issue with Java 5? \n\n " + e.getMessage();
            }
          }
        }
        if (body.localVariableTable().isDefined()) {
          RefArray<LocalVariable> refArrayOption = body.localVariableTable().get();
          byteCodeString
              .append("\n\nLocalVariableTable // [size: ")
              .append(refArrayOption.length())
              .append(" item(s)]\n");
          for (int k = 0; k < refArrayOption.length(); k++) {
            LocalVariable localVariable = refArrayOption.apply(k);
            byteCodeString
                .append("[")
                .append(localVariable.startPC())
                .append(" > ")
                .append(localVariable.length())
                .append(") => ")
                .append(localVariable.fieldType().toJava())
                .append(" ")
                .append(localVariable.name())
                .append("\n");
          }
        }
        // method.attributes
        //        RefArray<Attribute> attributes = method.attributes();
        //        for (int i = 0; i < attributes.length(); i++) {
        //          Attribute attribute = attributes.apply(i);
        //          byteCodeString.append(
        //                  // attribute.toString() + " " +
        // attribute.getClass().getAnnotations().length + "\n");
        //        }
        // method.attributes
        // method.exexceptionTable()
        //        if (method.exceptionTable().isDefined()) {
        //          ExceptionTable exceptionTable = method.exceptionTable().get();
        //          byteCodeString.append("ExceptionTable\n");
        //          RefIterator<ObjectType> exceptions = exceptionTable.exceptions().iterator();
        //          while (exceptions.hasNext()) {
        //            ObjectType exception = exceptions.next();
        //
        // byteCodeString.append(exception.id()).append("\t").append(exception.toJava()).append("\n");
        //          }
        //        }
        // end method.exceptionTable
        //        if (method.runtimeVisibleAnnotations().iterator().hasNext()) {
        //          byteCodeString.append("RuntimeVisibleAnnotations\n");
        //          RefIterator<Annotation> annotationRefIterator =
        // method.runtimeVisibleAnnotations().iterator();
        //          while (annotationRefIterator.hasNext()) {
        //            byteCodeString.append(annotationRefIterator.next().toJava());
        //          }
        //        }
        //        if (method.runtimeInvisibleAnnotations().iterator().hasNext()) {
        //          byteCodeString.append("RuntimeInvisibleAnnotations\n");
        //          RefIterator<Annotation> annotationRefIterator =
        // method.runtimeInvisibleAnnotations().iterator();
        //          while (annotationRefIterator.hasNext()) {
        //            byteCodeString.append(annotationRefIterator.next().toJava());
        //          }
        //        }
        if (body.stackMapTable().isDefined()) {
          StackMapTable stackMapTable = body.stackMapTable().get();
          RefArray<StackMapFrame> stackMapFrameRefArray = stackMapTable.stackMapFrames();
          byteCodeString
              .append("StackMapTable")
              .append("\n")
              .append("//PC\tName\tframeType\toffsetDelta\n");
          IntIterator pcIterator = stackMapTable.pcs().iterator();
          for (int i = 0; i < stackMapFrameRefArray.length(); i++) {
            StackMapFrame stackMapFrame = stackMapFrameRefArray.apply(i);
            Class<? extends StackMapFrame> frameClass = stackMapFrame.getClass();
            int pc = pcIterator.next();
            byteCodeString
                .append(pc + " ")
                .append(frameClass.getName() + " ")
                .append(stackMapFrame.frameType() + " ")
                .append(stackMapFrame.offset(0) - 1 + "\n");
          }
        }
      }
      byteCodeString.append("}").append("\n\n\n");
    }

    return byteCodeString.toString();
  }
  /**
   * creates a bytecode representation of the implemented fields of the passed classfile
   *
   * @param classFile ...
   * @return the bytecode representation as a String
   */
  public static String ByteCodeFieldToString(ClassFile classFile) {
    StringBuilder byteCodeString = new StringBuilder();
    RefArray<Field> fields = classFile.fields();
    byteCodeString.append("Fields\n");
    for (int j = 0; j < fields.length(); j++) {
      Field field = fields.apply(j);
      // TODO: if access flags empty don't insert empty space before fieldType()
      byteCodeString
          .append(AccessFlags.toString(field.accessFlags(), AccessFlagsContexts.FIELD()))
          .append(" ")
          .append(field.fieldType().toJava())
          .append(" ")
          .append(field.name())
          .append("\n");
    }
    byteCodeString.append("\n\n\n");
    return byteCodeString.toString();
  }
  // =====================================================================================
  // =====================================================================================
  // =====================================================================================

  /**
   * Creates the Three-Address-Code for a given class file
   *
   * @param classFile
   * @param filepath  - the path of the class file
   * @return the TAC for the class file
   */
  @NotNull
  public static String createTacString(@NotNull ClassFile classFile, String filepath) {
    StringBuilder tacCodeString = new StringBuilder();
    uriProject = Project.apply(new File(filepath));
    javaProject = new JavaProject(uriProject);
    methodTACodeFunction = javaProject.project().get(DefaultTACAIKey$.MODULE$);

    // iterate through the methods and generate the TAC for each
    RefArray<Method> methods = classFile.methods();
    for (int j = 0; j < methods.length(); j++) {
      Method method = methods.apply(j);
      if (method.body().isDefined()) {
        System.out.println(method.toJava());
        tacCodeString.append(method.toJava()).append("\n");
        TACode<TACMethodParameter, DUVar<KnownTypedValue>> TacCode =
            methodTACodeFunction.apply(method);
        tacCodeString.append(ToTxt.apply(TacCode).mkString("\n"));
        tacCodeString.append("\n\n\n");
      }
    } // for(j)

    return tacCodeString.toString();
  }

  // ====================================================================================
  // ====================================================================================

  @Deprecated
  public static String JavaClassToHtmlForm(VirtualFile virtualClassFile) {
    classPath = virtualClassFile.getPath();
    String JavaHTMLClass = JavaClassToHTMLForm(classPath);
    return JavaHTMLClass;
  }

  @Deprecated
  public static String JavaClassToHTMLForm(String classPath) {
    Path path = Paths.get(classPath);
    File file = path.toFile();
    // TODO scala.collection.immutable.List<Object> classFileList;
    String toHtmlAsString;
    try (FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis)) {
      // get the class file and construct the HTML string
      org.opalj.da.ClassFile cf = (org.opalj.da.ClassFile) ClassFileReader.ClassFile(dis).head();
      cf.cpToXHTML();
      // ordentliches HTML Code
      toHtmlAsString =
          "<html>\n<head>\n<style>"
              + cf.TheCSS() // cf.TheCSS()
              + "</style>\n</head>\n<body>\n"
              + jsOpenField()
              + jsOpenMethod()
              + jsScrollToField()
              + jsScrollToID()
              + cf.classFileToXHTML(new Some(classPath)).toString()
              + "\n</body>\n</html>";

      // TODO: is this ok? (talk to M. Eichberg?)
      toHtmlAsString = fixInitSymbols(toHtmlAsString);
    } catch (IOException e) {
      toHtmlAsString =
          "<html><body><h1>Something went wrong in Opal.toHTMLForm()</h1></body></html>";
    }
    return toHtmlAsString;
  }

  @Deprecated
  private static String fixInitSymbols(String htmlString) {
    // e.g. id="&lt;clinit&gt;()V" should become id="<clinit>()V"
    final String regex = "(id=\"|data-name=\")(&lt;)(\\w+)(&gt;)([^\"]*\")";
    final Pattern p = Pattern.compile(regex);
    final Matcher m = p.matcher(htmlString);

    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      String replacement = m.group(1) + "<" + m.group(3) + ">" + m.group(5);
      m.appendReplacement(sb, replacement);
    }
    m.appendTail(sb);
    htmlString = sb.toString();

    return htmlString;
  }

  @Deprecated
  private static String jsOpenField() {
    String script =
        "<script>\n"
            + "function openFields() {\n"
            + "   document.getElementsByClassName(\"fields\")[0].getElementsByTagName(\"details\")[0].open = true;"
            + "}\n"
            + "</script>\n";

    return script;
  }

  @Deprecated
  private static String jsOpenMethod() {
    String script =
        "<script>\n"
            + "function openMethods() {\n"
            + "   document.getElementsByClassName(\"methods\")[0].getElementsByTagName(\"details\")[0].open = true;"
            + "}\n"
            + "</script>\n";

    return script;
  }

  @Deprecated
  private static String jsScrollToField() {
    // differentiate between light and dark IDE theme
    String lightThemeHighlight = "\"#FDFF47\"";
    String darkThemeHighlight = "\"#ABCDEF\"";

    //    String highlightColor = JBColor.isBright() ? lightThemeHighlight : darkThemeHighlight;
    String highlightColor = lightThemeHighlight;
    String originalColor = "\"#FFFFFF\""; // TODO: need one for dark theme as well

    // orig (e.g. get it from CSS)?
    String script =
        "<script>\n"
            + "function scrollToField(dataName) {\n"
            + "    var elements = document.getElementsByClassName(\"field details\");\n"
            + "    for(var i=0; i < elements.length; i++) {\n"
            + "       var element = elements[i];\n"
            + "       if(element.getAttribute(\"data-name\") == dataName) {\n"
            + "         element.scrollIntoView();\n"
            + "         element.style.backgroundColor = "
            + highlightColor
            + ";\n"
            + "         window.setTimeout(function(){\n"
            + "           element.style.backgroundColor = "
            + originalColor
            + ";\n"
            + "         }, 2000);\n"
            + "         return;\n"
            + "       }\n"
            + "    }\n"
            + "}\n"
            + "</script>\n";

    return script;
  }

  @Deprecated
  private static String jsScrollToID() {
    // differentiate between light and dark IDE theme
    String lightThemeHighlight = "\"#FDFF47\"";
    String darkThemeHighlight = "\"#ABCDEF\"";

    //    String highlightColor = JBColor.isBright() ? lightThemeHighlight : darkThemeHighlight;
    String highlightColor = lightThemeHighlight;
    String originalColor = "\"#F7F7F7\""; // TODO: need one for dark theme as well

    // orig (e.g. get it from CSS)?
    String script =
        "<script>\n"
            + "function scrollTo(elementId) {\n"
            + "    var element = document.getElementById(elementId);\n"
            + "    element.scrollIntoView(); \n"
            + "    element.open  = true;\n"
            + "    element.style.backgroundColor = "
            + highlightColor
            + ";\n"
            + "    window.setTimeout(function(){\n"
            + "    element.style.backgroundColor = "
            + originalColor
            + ";\n"
            + "    }, 2000);\n"
            + "}\n"
            + "</script>\n";

    return script;
  }

  // ====================================================================================
  // ====================================================================================

  public static void setProject(com.intellij.openapi.project.Project inteliProject) {
    project = inteliProject;
  }

  @Deprecated
  public static VirtualFile prepareHtml(
      @NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile virtualFile) {
    // All files selected in the "Project"-View
    setProject(project);
    Compiler.make(project);
    File preparedFile = prepare(GlobalData.DISASSEMBLED_FILE_ENDING_HTML, virtualFile, null);
    return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(preparedFile);
  } // prepareHtml()


  /**
   * Creates the Three-Address-Code for the given class file
   *
   * @param project     - the project the file belongs to
   * @param virtualFile - the class file for which we want to create the TAC
   * @return a file (.tac) which contains the TAC for the class file
   */
  public static VirtualFile prepareTAC(
      @NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile virtualFile) {
    setProject(project);
    Compiler.make(project);
    File preparedFile = prepare(GlobalData.DISASSEMBLED_FILE_ENDING_TAC, virtualFile, null);
    return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(preparedFile);
  }


  /**
   * Creates the Java bytecode for the given class file
   *
   * @param project     - the project the file belongs to
   * @param virtualFile - the class file for which we want to create the bytecode
   * @return a file (.jbc) which contains the bytecode for the class file
   */
  public static VirtualFile prepareJBC(
      @NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile virtualFile) {
    setProject(project);
    Compiler.make(project);
    File preparedFile = prepare(GlobalData.DISASSEMBLED_FILE_ENDING_JBC, virtualFile, null);
    return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(preparedFile);
  }


  /**
   * Does the actual preparation of the desired output
   *
   * @param prepareID   - defines the type of file to be generated (TAC or Bytecode)
   * @param virtualFile - the class file
   * @param olderFile   -
   * @return the TAC or Bytecode file
   */
  public static File prepare(
          @NotNull String prepareID, @NotNull VirtualFile virtualFile, @Nullable VirtualFile olderFile) {
    classPath = virtualFile.getParent().getPath();
    String fileName = virtualFile.getNameWithoutExtension();
    String representableForm = null;
    ClassFile classFile = getClassFile(virtualFile);
    switch (prepareID) {
      case GlobalData.DISASSEMBLED_FILE_ENDING_HTML:
        fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_HTML);
        representableForm = Opal.JavaClassToHtmlForm(virtualFile);
        break;
      case GlobalData.DISASSEMBLED_FILE_ENDING_TAC:
        fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_TAC);
        classFile = getClassFile(virtualFile);
        representableForm = Opal.createTacString(classFile, virtualFile.getPath());
        break;
      case GlobalData.DISASSEMBLED_FILE_ENDING_JBC:
        fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_JBC);
        classFile = getClassFile(virtualFile);
        representableForm = Opal.createBytecodeString(classFile);
        break;
    }
    if (olderFile != null) {
      fileName = olderFile.getPath();
    }
    return writeContentToFile(fileName, representableForm, false, olderFile == null ? false : true);
  }

  /**
   * an auxiliary method that writes 'content' to a 'file' (main purpose of this method is to
   * encapsulate the try/catch block)
   *
   * @param filename the Name of the temporal file
   * @param content content to write into the file
   */
  private static File writeContentToFile(
      String filename, String content, boolean append, boolean olderFile) {
    try {
      File file = null;
      if (!olderFile) {
        String[] filenameArray = filename.split("\\.");
        file = FileUtil.createTempFile(filenameArray[0], "." + filenameArray[1], true);
      } else {
        file = new File(filename);
      }
      FileUtil.writeToFile(file, content, append);
      return file;
    } catch (IOException e) {
      e.printStackTrace();
    }
    // TODO not null
    return null;
  }
}
