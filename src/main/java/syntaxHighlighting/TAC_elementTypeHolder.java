// This is a generated file. Not intended for manual editing.
package syntaxHighlighting;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import syntaxHighlighting.psi.TAC_elementType;
import syntaxHighlighting.psi.TAC_tokenType;
import syntaxHighlighting.psi.impl.*;

public interface TAC_elementTypeHolder {

  IElementType J_METHOD_HEAD = new TAC_elementType("J_METHOD_HEAD");
  IElementType J_TYPE = new TAC_elementType("J_TYPE");
  IElementType TYPE = new TAC_elementType("TYPE");

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
  IElementType KEYWORDS = new TAC_tokenType("KEYWORDS");
  IElementType LEVEL = new TAC_tokenType("LEVEL");
  IElementType L_ARROW = new TAC_tokenType("L_ARROW");
  IElementType L_BRACKET = new TAC_tokenType("L_BRACKET");
  IElementType L_CURVED_BRACKET = new TAC_tokenType("L_CURVED_BRACKET");
  IElementType L_DOUBLE_LACE_BRACE = new TAC_tokenType("L_DOUBLE_LACE_BRACE");
  IElementType L_LACE_BRACE = new TAC_tokenType("L_LACE_BRACE");
  IElementType L_SQUARE_BRACKET = new TAC_tokenType("L_SQUARE_BRACKET");
  IElementType MINUS = new TAC_tokenType("MINUS");
  IElementType MODIFIER = new TAC_tokenType("MODIFIER");
  IElementType NEW_LINE = new TAC_tokenType("NEW_LINE");
  IElementType NUMBER = new TAC_tokenType("NUMBER");
  IElementType PLUS = new TAC_tokenType("PLUS");
  IElementType R_ARROW = new TAC_tokenType("R_ARROW");
  IElementType R_BRACKET = new TAC_tokenType("R_BRACKET");
  IElementType R_CURVED_BRACKET = new TAC_tokenType("R_CURVED_BRACKET");
  IElementType R_DOUBLE_LACE_BRACE = new TAC_tokenType("R_DOUBLE_LACE_BRACE");
  IElementType R_LACE_BRACE = new TAC_tokenType("R_LACE_BRACE");
  IElementType R_SQUARE_BRACKET = new TAC_tokenType("R_SQUARE_BRACKET");
  IElementType SEMICOLON = new TAC_tokenType("SEMICOLON");
  IElementType STRING = new TAC_tokenType("STRING");
  IElementType STRINGVAR = new TAC_tokenType("STRINGVAR");
  IElementType THIS = new TAC_tokenType("THIS");
  IElementType THROW = new TAC_tokenType("THROW");
  IElementType THROWS = new TAC_tokenType("THROWS");
  IElementType UEQ = new TAC_tokenType("UEQ");
  IElementType VOID = new TAC_tokenType("VOID");
  IElementType WHITE_SPACE = new TAC_tokenType("WHITE_SPACE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == J_METHOD_HEAD) {
        return new TACJMethodHeadImpl(node);
      }
      else if (type == J_TYPE) {
        return new TACJTypeImpl(node);
      }
      else if (type == TYPE) {
        return new TACTypeImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
