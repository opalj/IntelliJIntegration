/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.opalj.intellijintegration.JavaByteCodeLanguage.LanguageAndFileType.JavaByteCode;

/**
 * Token types correspond to the tokens of our language (@see .bnf file), e.g. '@' or 'class' or
 * 'BLOCK_COMMENT' or 'MNEMONIC' or ':' or ...
 *
 * <p>These token types can, for example, be used for syntax highlighting (i.e. each token type can
 * be associated with some color)
 *
 * @see IElementType for further details
 */
public class JavaByteCodeTokenType extends IElementType {

  public JavaByteCodeTokenType(@NotNull String debugName) {
    super(debugName, JavaByteCode.INSTANCE);
  }

  @Override
  public String toString() {
    return "JBCTokenType." + super.toString();
  }
}
