package JavaByteCodeLanguage.psi;

import JavaByteCodeLanguage.LanguageAndFileType.JavaByteCodeFileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import org.jetbrains.annotations.NotNull;

public class JavaByteCodeElementFactory {
  public static JavaByteCodeType createType(Project project, String name) {
    final JavaByteCodeFile file = createFile(project, name);
    return (JavaByteCodeType) file.getFirstChild();
  }

  public static JavaByteCodeMethodName createMethodName(Project project, String name) {
    final JavaByteCodeFile file = createFile(project, name);
    return (JavaByteCodeMethodName) file.getFirstChild();
  }

  @NotNull
  private static JavaByteCodeFile createFile(Project project, String text) {
    String name = "dummy.jbc";
    return (JavaByteCodeFile)
        PsiFileFactory.getInstance(project)
            .createFileFromText(name, JavaByteCodeFileType.INSTANCE, text);
  }
}
