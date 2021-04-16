/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.taclanguage.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.opalj.intellijintegration.taclanguage.languageandfiletype.TAC;
import org.opalj.intellijintegration.taclanguage.languageandfiletype.TAC_filetype;

public class TAC_file extends PsiFileBase {

  public TAC_file(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, TAC.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return TAC_filetype.INSTANCE;
  }

  @Override
  public String toString() {
    return "TAC File";
  }

  @Override
  public Icon getIcon(int flags) {
    return super.getIcon(flags);
  }
}
