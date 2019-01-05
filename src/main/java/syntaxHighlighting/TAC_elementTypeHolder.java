// This is a generated file. Not intended for manual editing.
package syntaxHighlighting;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import syntaxHighlighting.impl.TACPropertyImpl;

public interface TAC_elementTypeHolder {

    IElementType PROPERTY = new TAC_elementType("PROPERTY");

    IElementType COMMENT = new TAC_tokenType("COMMENT");
    IElementType CRLF = new TAC_tokenType("CRLF");
    IElementType KEY = new TAC_tokenType("KEY");
    IElementType SEPARATOR = new TAC_tokenType("SEPARATOR");
    IElementType VALUE = new TAC_tokenType("VALUE");

    class Factory {
        public static PsiElement createElement(ASTNode node) {
            IElementType type = node.getElementType();
            if (type == PROPERTY) {
                return new TACPropertyImpl(node);
            }
            throw new AssertionError("Unknown element type: " + type);
        }
    }
}
