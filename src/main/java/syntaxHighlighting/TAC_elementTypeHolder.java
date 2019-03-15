// This is a generated file. Not intended for manual editing.
package syntaxHighlighting;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import syntaxHighlighting.psi.TAC_elementType;
import syntaxHighlighting.psi.TAC_tokenType;
import syntaxHighlighting.psi.impl.*;

public interface TAC_elementTypeHolder {

  IElementType J_TYPE = new TAC_elementType("J_TYPE");
  IElementType PROPERTY = new TAC_elementType("PROPERTY");
  IElementType TYPE = new TAC_elementType("TYPE");
  IElementType NEW_LINE = new TAC_elementType("NEW_LINE");

  IElementType AT = new TAC_tokenType("AT");
  IElementType BLOCK_COMMENT = new TAC_tokenType("BLOCK_COMMENT");
  IElementType CLASS = new TAC_tokenType("CLASS");
  IElementType COLON = new TAC_tokenType("COLON");
  IElementType COMMA = new TAC_tokenType("COMMA");
  IElementType COMMENT = new TAC_tokenType("COMMENT");
  IElementType CONSTMETHODNAMES = new TAC_tokenType("CONSTMETHODNAMES");
  IElementType CRLF = new TAC_tokenType("CRLF");
  IElementType DOT = new TAC_tokenType("DOT");
  IElementType EQ = new TAC_tokenType("EQ");
  IElementType EXTENDS = new TAC_tokenType("EXTENDS");
  IElementType IMPLEMENTS = new TAC_tokenType("IMPLEMENTS");
  IElementType JAVA_TYPE = new TAC_tokenType("JAVA_TYPE");
  IElementType KEY = new TAC_tokenType("KEY");
  IElementType KEYWORDS = new TAC_tokenType("KEYWORDS");
  IElementType LARROW = new TAC_tokenType("LARROW");
  IElementType LBRACKET = new TAC_tokenType("LBRACKET");
  IElementType LEVEL = new TAC_tokenType("LEVEL");
  IElementType MODIFIER = new TAC_tokenType("MODIFIER");
  IElementType NUMBER = new TAC_tokenType("NUMBER");
  IElementType RARROW = new TAC_tokenType("RARROW");
  IElementType RBRACKET = new TAC_tokenType("RBRACKET");
  IElementType SEMICOLON = new TAC_tokenType("SEMICOLON");
  IElementType SEPARATOR = new TAC_tokenType("SEPARATOR");
  IElementType STRING = new TAC_tokenType("STRING");
  IElementType STRINGVAR = new TAC_tokenType("STRINGVAR");
  IElementType THIS = new TAC_tokenType("THIS");
  IElementType THROW = new TAC_tokenType("THROW");
  IElementType THROWS = new TAC_tokenType("THROWS");
  IElementType UEQ = new TAC_tokenType("UEQ");
  IElementType VALUE = new TAC_tokenType("VALUE");
  IElementType VOID = new TAC_tokenType("VOID");
  IElementType WHITE_SPACE = new TAC_tokenType("WHITE_SPACE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == J_TYPE) {
        return new TACJTypeImpl(node);
      }
      else if (type == PROPERTY) {
        return new TACPropertyImpl(node);
      }
      else if (type == TYPE) {
        return new TACTypeImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
