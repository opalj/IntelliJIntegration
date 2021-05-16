/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.opalj.intellijintegration.JavaByteCodeLanguage.LanguageAndFileType.JavaByteCode;
import org.opalj.intellijintegration.JavaByteCodeLanguage.LanguageAndFileType.JavaByteCodeFileType;

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
