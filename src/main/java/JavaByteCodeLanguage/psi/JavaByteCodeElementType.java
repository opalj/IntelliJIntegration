/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package JavaByteCodeLanguage.psi;

import JavaByteCodeLanguage.LanguageAndFileType.JavaByteCode;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Element types correspond to rules in our grammar (@see .bnf file), e.g. an INSTR or J_TYPE or
 * DEF_METHOD_NAME or ...
 *
 * <p>These element types basically form the AST tree, each one being a node
 *
 * @see IElementType for further details
 */
public class JavaByteCodeElementType extends IElementType {
  public JavaByteCodeElementType(@NotNull String debugName) {
    super(debugName, JavaByteCode.INSTANCE);
  }
}
