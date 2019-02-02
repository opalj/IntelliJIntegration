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
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.opalj.ai.ValuesDomain;
import org.opalj.br.Method;
import org.opalj.br.analyses.JavaProject;
import org.opalj.br.analyses.Project;
import org.opalj.collection.immutable.ConstArray;
import org.opalj.collection.immutable.RefArray;
import org.opalj.da.ClassFileReader;
import org.opalj.tac.*;
import org.opalj.value.KnownTypedValue;
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

  private static void Test(String filepath) {
    Project<URL> uriProject = Project.apply(new File(filepath));
    JavaProject javaProject = new JavaProject(uriProject);
    javaProject
        .project()
        .parForeachMethodWithBody(
            null,
            16,
            (mi) -> {
              TACode<TACMethodParameter, DUVar<ValuesDomain.Value>> taCode =
                  TACAI.apply(javaProject.project(), mi.method(), null);
              String taCodeAsString = ToTxt.apply(taCode).mkString("\n");
              String Name = mi.classFile().thisType().toJava() + mi.method().name();
              System.out.println("test");
              try {
                FileOutputStream outputStream = new FileOutputStream(Name);
                byte[] strToBytes = taCodeAsString.getBytes();
                outputStream.write(strToBytes);
              } catch (IOException e) {
                e.printStackTrace();
              }
              System.out.println();
              return 1;
            });
  }

  public static VirtualFile SomeTest(VirtualFile virtualClassFile) {
    File myFile = null;
    try {
      Process exec = Runtime.getRuntime().exec("javap -c " + virtualClassFile.getPath());
      Scanner scanner = new Scanner(exec.getInputStream()).useDelimiter("\\A");
      String myString = "";
      while (scanner.hasNextLine()) myString = myString.concat(scanner.nextLine()).concat("\n");
      FileUtil.writeToFile(
          myFile =
              new File(
                  virtualClassFile.getParent().getPath()
                      + File.separator
                      + virtualClassFile.getNameWithoutExtension()
                      + ".dis"),
          myString);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(myFile);
  }

  public static String JavaClasstoTACForm(VirtualFile virtualClassFile) {
    classPath = virtualClassFile.getPath();
    String JavaTACClassString = threeWayDisassemblerString(classPath);
    return JavaTACClassString;
  }

  public static String threeWayDisassemblerString(String filepath) {
    StringBuilder tacCodeString = new StringBuilder();
    uriProject = Project.apply(new File(filepath));
    javaProject = new JavaProject(uriProject);
    ConstArray<org.opalj.br.ClassFile> classFileConstArray =
        javaProject.project().allProjectClassFiles();
    methodTACodeFunction = javaProject.project().get(DefaultTACAIKey$.MODULE$);
    for (int i = 0; i < classFileConstArray.length(); i++) {
      org.opalj.br.ClassFile classFile = classFileConstArray.apply(i);
      RefArray<Method> methods = classFile.methods();
      //            TODO     classFile.fields(); soll nicht
      //            TODO     classFile.attributes();
      for (int j = 0; j < methods.length(); j++) {
        Method method = methods.apply(j);
        if (method.body().isDefined()) {
          System.out.println(method.toJava());
          TACode<TACMethodParameter, DUVar<KnownTypedValue>> TacCode =
              methodTACodeFunction.apply(method);
          tacCodeString.append(ToTxt.apply(TacCode).mkString("\n"));
        }
      } // for(j)
    } // for(i)

    // TODO
    try {
      return new String(tacCodeString.toString().getBytes(), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      return null;
    }

    //        return tacCodeString;
  }

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

  private static String myCSS(String theirCSS) {
    // if(JBColor.isBright())
    //    return theirCSS;

    int firstColorIndex = theirCSS.indexOf("#");
    theirCSS = theirCSS.replaceFirst("background-color: #F6F6F6", "background-color: #abcdef");

    String[] colorSplit = theirCSS.split("color:  ");
    String[] backgroundColorSplit = theirCSS.split("background-color:  ");

    // 0:  body (no visible change)
    // 1:  div#source ... background: rgb(...)
    // 2:  !! background-color: rgb(...)
    // 3:  .nested-details ... background-color: #...
    // 4:  summary ... background-color: #...
    // 5:  div.method, details.method ... background-color: #...
    // 6:  .annotation
    // 7:  .index, .pc
    // 8:  .line
    // 9:  .reservedwords,.verification
    // 10: .constant_value
    // 11: .attribute_name
    // 12: .close

    return theirCSS;
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
      return LocalFileSystem.getInstance()
          .refreshAndFindFileByIoFile(
              prepare(GlobalData.DISASSEMBLED_FILE_ENDING_TAC, virtualFile));
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
    String represetableForm = null;
    if (prepareID.equals(GlobalData.DISASSEMBLED_FILE_ENDING_HTML)) {
      fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_HTML);
      represetableForm = Opal.JavaClassToHtmlForm(virtualFile);
    } else if (prepareID.equals(GlobalData.DISASSEMBLED_FILE_ENDING_TAC)) {
      fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_TAC);
      represetableForm = Opal.JavaClasstoTACForm(virtualFile);
    }
    File disassembledFile = new File(absPath + File.separator + fileName);
    try {
      FileUtil.writeToFile(disassembledFile, represetableForm, false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return disassembledFile;
  }
}
