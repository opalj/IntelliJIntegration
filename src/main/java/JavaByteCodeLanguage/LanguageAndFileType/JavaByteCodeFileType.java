/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package JavaByteCodeLanguage.LanguageAndFileType;

import com.intellij.openapi.fileTypes.LanguageFileType;
import globalData.GlobalData;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A file type that associates itself with our JavaByteCode language.
 *
 * @see LanguageFileType for further details.
 */
public class JavaByteCodeFileType extends LanguageFileType {
  public static final JavaByteCodeFileType INSTANCE = new JavaByteCodeFileType();

  private JavaByteCodeFileType() {
    super(JavaByteCode.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "Javabytecode file";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "javabytecode file description";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return GlobalData.DISASSEMBLED_FILE_ENDING_JBC;
  }

  @Nullable
  @Override
  public Icon getIcon() {
    // We do not associate an icon with this file type
    return null;
  }
}
