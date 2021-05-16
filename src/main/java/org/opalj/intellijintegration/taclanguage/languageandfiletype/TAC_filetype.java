/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.taclanguage.languageandfiletype;

import com.intellij.openapi.fileTypes.LanguageFileType;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.opalj.intellijintegration.globalData.GlobalData;

public class TAC_filetype extends LanguageFileType {

  public static final Icon ICON = new ImageIcon("icons/opal.png");
  public static final TAC_filetype INSTANCE = new TAC_filetype();

  private TAC_filetype() {
    super(TAC.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "TAC";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Three-address-code";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return GlobalData.DISASSEMBLED_FILE_ENDING_TAC;
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return ICON;
  }
}
