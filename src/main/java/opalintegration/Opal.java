package opalintegration;

import Compile.Compiler;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import globalData.GlobalData;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.jar.JarInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.opalj.bi.AccessFlags;
import org.opalj.br.ClassFile;
import org.opalj.br.Code;
import org.opalj.br.LocalVariable;
import org.opalj.br.Method;
import org.opalj.br.analyses.JavaProject;
import org.opalj.br.analyses.Project;
import org.opalj.br.instructions.Instruction;
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

  private static ClassFile getClassFile(VirtualFile virtualClassFile) {
    String filepath = virtualClassFile.getPath();
    Project<URL> uriProject = Project.apply(new File(filepath));
    ConstArray<ClassFile> classFileConstArray = uriProject.allProjectClassFiles();

    if (classFileConstArray.length() > 0) {
      System.out.println("Apply(0): " + classFileConstArray.apply(0));
      return classFileConstArray.apply(0);
    }
    // else (might be) JAR
    else {
      System.out.println("getClassFile() JAR? : " + virtualClassFile.getName());
      String jarFileRoot = getJarFileRoot(virtualClassFile);
      return createClassFileFromJar(jarFileRoot);
    }
  }

  private static String getJarFileRoot(VirtualFile virtualClassFile) {
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
    String myString = "// ";
    myString =
        myString + AccessFlags.classFlagsToJava(classFile.accessFlags()) + " " + classFile.fqn();
    if (classFile.superclassType().isDefined())
      myString = myString + " extends " + classFile.superclassType().get().toJava();
    if (classFile.interfaceTypes().length() > 0) {
      myString = myString + " implements ";
      for (int j = 0; j < classFile.interfaceTypes().length(); j++) {
        myString = myString + classFile.interfaceTypes().apply(j).toJava() + " ";
      }
    }
    myString =
        myString
            + "\n// Source File: "
            + classFile.sourceFile()
            + " Version: "
            + classFile.jdkVersion()
            + " Size: \n";

    // TODO Constant Pool Maybe working with DA.
    RefArray<Method> methods = classFile.methods();
    for (int j = 0; j < methods.length(); j++) {
      Method method = methods.apply(j);
      if (method.body().isDefined()) {
        Code body = method.body().get();
        Instruction[] instructions = body.instructions();
        myString =
            myString
                + method.toString()
                + "// [size :"
                + body.codeSize()
                + " bytes, max Stack: "
                + body.maxStack()
                + ", max Locals: "
                + body.maxLocals()
                + "] \n";
        myString = myString + "\tPC\tLine\tInstruction\n";
        for (int k = 0; k < instructions.length; k++) {
          if (instructions[k] != null) {
            try {
              myString =
                  myString
                      + "\t"
                      + k
                      + "\t"
                      + body.lineNumber(k).get().toString()
                      + "\t\t"
                      + instructions[k]
                      + "\n";
            } catch (NoSuchElementException e) {
              return "Issue with Java 5? \n\n " + e.getMessage();
            }
          }
        }
        if (body.localVariableTable().isDefined()) {
          RefArray<LocalVariable> refArrayOption = body.localVariableTable().get();
          myString =
              myString
                  + "\n\nLocalVariableTable // [size: "
                  + refArrayOption.length()
                  + " item(s)]\n";
          for (int k = 0; k < refArrayOption.length(); k++) {
            LocalVariable localVariable = refArrayOption.apply(k);
            myString =
                myString
                    + "["
                    + localVariable.startPC()
                    + " > "
                    + localVariable.length()
                    + ") => "
                    + localVariable.fieldType().toJava()
                    + " "
                    + localVariable.name()
                    + "\n";
          }
        }
        myString = myString + "\n\n\n";
      }
    }

    return myString;
  }

  // =====================================================================================
  // =====================================================================================
  // =====================================================================================

  public static String JavaClassToTACForm(ClassFile classFile, String filepath) {
    String JavaTACClassString = threeWayDisassemblerString(classFile, filepath);
    return JavaTACClassString;
  }

  public static String threeWayDisassemblerString(ClassFile classFile, String filepath) {
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

    System.out.println("TAC CODE TO STRING: " + tacCodeString);
    return tacCodeString.toString();
  }

  // ====================================================================================
  // ====================================================================================

  public static String JavaClassToHtmlForm(VirtualFile virtualClassFile) {
    classPath = virtualClassFile.getPath();
    String JavaHTMLClass = JavaClassToHTMLForm(classPath);
    return JavaHTMLClass;
  }

  public static String JavaClassToHTMLForm(String classPath) {
    Path path = Paths.get(classPath);
    File file = path.toFile();
    // TODO scala.collection.immutable.List<Object> classFileList;
    String toHtmlAsString;
    try (FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis)) {
      // get the class file and construct the HTML string
      org.opalj.da.ClassFile cf = (org.opalj.da.ClassFile) ClassFileReader.ClassFile(dis).head();

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

  private static String jsOpenField() {
    String script =
        "<script>\n"
            + "function openFields() {\n"
            + "   document.getElementsByClassName(\"fields\")[0].getElementsByTagName(\"details\")[0].open = true;"
            + "}\n"
            + "</script>\n";

    return script;
  }

  private static String jsOpenMethod() {
    String script =
        "<script>\n"
            + "function openMethods() {\n"
            + "   document.getElementsByClassName(\"methods\")[0].getElementsByTagName(\"details\")[0].open = true;"
            + "}\n"
            + "</script>\n";

    return script;
  }

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

  // TODO check if date is create & up2date GOT ERROS
  public static VirtualFile prepareHtml(
      @NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile virtualFile) {
    // All files selected in the "Project"-View
    setProject(project);
    Collection<VirtualFile> virtualFilesByName =
        FilenameIndex.getVirtualFilesByName(
            project,
            virtualFile
                .getNameWithoutExtension()
                .concat(".")
                .concat(GlobalData.DISASSEMBLED_FILE_ENDING_HTML),
            GlobalSearchScope.projectScope(project));
    if (!Compiler.make(project) || virtualFilesByName.isEmpty()) {
      // Save the decompiled code to a file
      LocalFileSystem.getInstance()
          .refreshAndFindFileByIoFile(
              prepare(GlobalData.DISASSEMBLED_FILE_ENDING_TAC, virtualFile));
      LocalFileSystem.getInstance()
          .refreshAndFindFileByIoFile(
              prepare(GlobalData.DISASSEMBLED_FILE_ENDING_JBC, virtualFile));
      return LocalFileSystem.getInstance()
          .refreshAndFindFileByIoFile(
              prepare(GlobalData.DISASSEMBLED_FILE_ENDING_HTML, virtualFile));
    }
    return virtualFilesByName.stream().findFirst().get();
  } // prepareHtml()

  public static VirtualFile prepareTAC(
      @NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile virtualFile) {
    setProject(project);
    Collection<VirtualFile> virtualFilesByName =
        FilenameIndex.getVirtualFilesByName(
            project,
            virtualFile
                .getNameWithoutExtension()
                .concat(".")
                .concat(GlobalData.DISASSEMBLED_FILE_ENDING_TAC),
            GlobalSearchScope.projectScope(project));
    if (!Compiler.make(project) || virtualFilesByName.isEmpty()) {
      LocalFileSystem.getInstance()
          .refreshAndFindFileByIoFile(
              prepare(GlobalData.DISASSEMBLED_FILE_ENDING_HTML, virtualFile));
      LocalFileSystem.getInstance()
          .refreshAndFindFileByIoFile(
              prepare(GlobalData.DISASSEMBLED_FILE_ENDING_JBC, virtualFile));
      return LocalFileSystem.getInstance()
          .refreshAndFindFileByIoFile(
              prepare(GlobalData.DISASSEMBLED_FILE_ENDING_TAC, virtualFile));
    }
    return virtualFilesByName.stream().findFirst().get();
  }

  // this should be called by constructor
  public static VirtualFile prepareJBC(
      @NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile virtualFile) {
    setProject(project);
    Collection<VirtualFile> virtualFilesByName =
        FilenameIndex.getVirtualFilesByName(
            project,
            virtualFile
                .getNameWithoutExtension()
                .concat(".")
                .concat(GlobalData.DISASSEMBLED_FILE_ENDING_JBC),
            GlobalSearchScope.projectScope(project));
    if (!Compiler.make(project) || virtualFilesByName.isEmpty()) {
      LocalFileSystem.getInstance()
          .refreshAndFindFileByIoFile(
              prepare(GlobalData.DISASSEMBLED_FILE_ENDING_HTML, virtualFile));
      LocalFileSystem.getInstance()
          .refreshAndFindFileByIoFile(
              prepare(GlobalData.DISASSEMBLED_FILE_ENDING_TAC, virtualFile));
      return LocalFileSystem.getInstance()
          .refreshAndFindFileByIoFile(
              prepare(GlobalData.DISASSEMBLED_FILE_ENDING_JBC, virtualFile));
    }

    return virtualFilesByName.stream().findFirst().get();
  }

  // todo SaveFile Class erweitern?
  private static File prepare(@NotNull String prepareID, VirtualFile virtualFile) {
    String basePath = project.getBasePath();
    classPath = virtualFile.getParent().getPath();
    String absPath =
        basePath
            + File.separator
            + GlobalData.DISASSEMBLED_FILES_DIR
            + classPath.substring(basePath.length());

    String fileName = virtualFile.getNameWithoutExtension();
    String representableForm = null;
    ClassFile classFile;
    switch (prepareID) {
      case GlobalData.DISASSEMBLED_FILE_ENDING_HTML:
        fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_HTML);
        representableForm = Opal.JavaClassToHtmlForm(virtualFile);
        break;
      case GlobalData.DISASSEMBLED_FILE_ENDING_TAC:
        fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_TAC);
        classFile = getClassFile(virtualFile);
        representableForm = Opal.JavaClassToTACForm(classFile, virtualFile.getPath());
        break;
      case GlobalData.DISASSEMBLED_FILE_ENDING_JBC:
        fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_JBC);
        classFile = getClassFile(virtualFile);
        representableForm = Opal.createBytecodeString(classFile);
        break;
    }

    File disassembledFile = new File(absPath + File.separator + fileName);
    writeContentToFile(disassembledFile, representableForm, false);
    return disassembledFile;
  }

  /**
   * an auxiliary method that writes 'content' to a 'file' (main purpose of this method is to
   * encapsulate the try/catch block)
   *
   * @param file file to write to
   * @param content content to write into the file
   */
  private static void writeContentToFile(File file, String content, boolean append) {
    try {
      FileUtil.writeToFile(file, content, append);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
