/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.opalintegration;

import com.intellij.openapi.vfs.VirtualFile;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.opalj.da.ClassFileReader;
import org.opalj.intellijintegration.globalData.GlobalData;
import scala.Some;

@Deprecated
@SuppressWarnings("all")
public class HtmlProducer {
  private static String classPath;

  public static VirtualFile prepareHtml(
      @NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile virtualFile) {
    return OpalUtil.prepare(project, GlobalData.DISASSEMBLED_FILE_ENDING_HTML, virtualFile, null);
  }

  static String JavaClassToHtmlForm(VirtualFile virtualClassFile) {
    classPath = virtualClassFile.getPath();
    String JavaHTMLClass = JavaClassToHTMLForm(classPath);
    return JavaHTMLClass;
  }

  private static String JavaClassToHTMLForm(String classPath) {
    Path path = Paths.get(classPath);
    File file = path.toFile();
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
              + JavaScript.jsOpenField()
              + JavaScript.jsOpenMethod()
              + JavaScript.jsScrollToField()
              + JavaScript.jsScrollToID()
              + cf.classFileToXHTML(new Some(classPath)).toString()
              + "\n</body>\n</html>";

      toHtmlAsString = fixInitSymbols(toHtmlAsString);
    } catch (IOException e) {
      toHtmlAsString =
          "<html><body><h1>Something went wrong in OpalUtil.toHTMLForm()</h1></body></html>";
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
}
