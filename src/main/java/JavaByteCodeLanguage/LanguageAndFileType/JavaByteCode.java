/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package JavaByteCodeLanguage.LanguageAndFileType;

import com.intellij.lang.Language;
import globalData.GlobalData;

/**
 * A singleton which represents our language for the bytecode. In particular, it defines the file
 * ending.
 */
public class JavaByteCode extends Language {
  public static final JavaByteCode INSTANCE = new JavaByteCode();

  // the ID is the file ending associated with the JavaByteCode language
  private static final String ID = GlobalData.DISASSEMBLED_FILE_ENDING_JBC;

  private JavaByteCode() {
    super(ID);
  }
}
