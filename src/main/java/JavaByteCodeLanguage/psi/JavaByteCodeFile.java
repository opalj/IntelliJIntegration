package JavaByteCodeLanguage.psi;

import JavaByteCodeLanguage.LanguageAndFileType.JavaByteCode;
import JavaByteCodeLanguage.LanguageAndFileType.JavaByteCodeFileType;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;

/** defines the root of the .jbc file */
public class JavaByteCodeFile extends PsiFileBase {
  public JavaByteCodeFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, JavaByteCode.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return JavaByteCodeFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return "Java Byte Code File";
  }

  @Override
  public Icon getIcon(int flags) {
    return null;
  }
}
