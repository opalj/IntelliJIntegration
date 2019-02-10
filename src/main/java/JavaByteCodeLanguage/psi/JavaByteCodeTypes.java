// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import JavaByteCodeLanguage.psi.impl.*;

public interface JavaByteCodeTypes {

  IElementType PROPERTY = new JavaByteCodeElementType("PROPERTY");

  IElementType COMMENT = new JavaByteCodeTokenType("COMMENT");
  IElementType CRLF = new JavaByteCodeTokenType("CRLF");
  IElementType INST = new JavaByteCodeTokenType("INST");
  IElementType LINE = new JavaByteCodeTokenType("LINE");
  IElementType PC = new JavaByteCodeTokenType("PC");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == PROPERTY) {
        return new JavaByteCodePropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
