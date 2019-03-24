// This is a generated file. Not intended for manual editing.
package syntaxHighlighting;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import syntaxHighlighting.psi.TAC_elementType;
import syntaxHighlighting.psi.TAC_tokenType;
import syntaxHighlighting.psi.impl.*;

public interface TAC_elementTypeHolder {

  IElementType CLASS_HEAD = new TAC_elementType("CLASS_HEAD");
  IElementType DEF_METHOD_NAME = new TAC_elementType("DEF_METHOD_NAME");
  IElementType FIELDS_DECLARATION = new TAC_elementType("FIELDS_DECLARATION");
  IElementType INSTR = new TAC_elementType("INSTR");
  IElementType JAVA_OP = new TAC_elementType("JAVA_OP");
  IElementType J_TYPE = new TAC_elementType("J_TYPE");
  IElementType LAMBDA_PARAMS = new TAC_elementType("LAMBDA_PARAMS");
  IElementType LAMBDA_TYPE = new TAC_elementType("LAMBDA_TYPE");
  IElementType METHOD_DECLARATION = new TAC_elementType("METHOD_DECLARATION");
  IElementType METHOD_HEAD = new TAC_elementType("METHOD_HEAD");
  IElementType METHOD_NAME = new TAC_elementType("METHOD_NAME");
  IElementType MODIFIER_V = new TAC_elementType("MODIFIER_V");
  IElementType PARAMS = new TAC_elementType("PARAMS");
  IElementType TYPE = new TAC_elementType("TYPE");

  IElementType AT = new TAC_tokenType("@");
  IElementType BLOCK_COMMENT = new TAC_tokenType("BLOCK_COMMENT");
  IElementType CLASS = new TAC_tokenType("class");
  IElementType COLON = new TAC_tokenType(":");
  IElementType COMMA = new TAC_tokenType(",");
  IElementType COMMENT = new TAC_tokenType("COMMENT");
  IElementType CONSTMETHODNAMES = new TAC_tokenType("CONSTMETHODNAMES");
  IElementType DOT = new TAC_tokenType(".");
  IElementType ENUM = new TAC_tokenType("enum");
  IElementType EQ = new TAC_tokenType("=");
  IElementType EXTENDS = new TAC_tokenType("extends");
  IElementType FIELDS = new TAC_tokenType("Fields");
  IElementType IMPLEMENTS = new TAC_tokenType("implements");
  IElementType INSTRUCTION = new TAC_tokenType("Instruction");
  IElementType INTERFACE = new TAC_tokenType("interface");
  IElementType JAVA_TYPE = new TAC_tokenType("JAVA_TYPE");
  IElementType KEYWORDS = new TAC_tokenType("KEYWORDS");
  IElementType LBRACKET = new TAC_tokenType("LBRACKET");
  IElementType LEVEL = new TAC_tokenType("LEVEL");
  IElementType LINE = new TAC_tokenType("Line");
  IElementType LOAD_INSTR = new TAC_tokenType("LOAD_INSTR");
  IElementType L_ARROW = new TAC_tokenType("<=");
  IElementType L_BRACKET = new TAC_tokenType("(");
  IElementType L_CURVED_BRACKET = new TAC_tokenType("{");
  IElementType L_DOUBLE_LACE_BRACE = new TAC_tokenType("«");
  IElementType L_LACE_BRACE = new TAC_tokenType("<");
  IElementType L_SQUARE_BRACKET = new TAC_tokenType("[");
  IElementType METHODS = new TAC_tokenType("Methods");
  IElementType MINUS = new TAC_tokenType("-");
  IElementType MNEMONIC = new TAC_tokenType("MNEMONIC");
  IElementType MODIFIER = new TAC_tokenType("MODIFIER");
  IElementType NUMBER = new TAC_tokenType("NUMBER");
  IElementType PC = new TAC_tokenType("PC");
  IElementType PLUS = new TAC_tokenType("+");
  IElementType PRIMITIVETYPE = new TAC_tokenType("PRIMITIVETYPE");
  IElementType RBRACKET = new TAC_tokenType("RBRACKET");
  IElementType R_ARROW = new TAC_tokenType("=>");
  IElementType R_BRACKET = new TAC_tokenType(")");
  IElementType R_CURVED_BRACKET = new TAC_tokenType("}");
  IElementType R_DOUBLE_LACE_BRACE = new TAC_tokenType("»");
  IElementType R_LACE_BRACE = new TAC_tokenType(">");
  IElementType R_SQUARE_BRACKET = new TAC_tokenType("]");
  IElementType SEMICOLON = new TAC_tokenType(";");
  IElementType STRING = new TAC_tokenType("STRING");
  IElementType STRINGVAR = new TAC_tokenType("STRINGVAR");
  IElementType THIS = new TAC_tokenType("this");
  IElementType THROW = new TAC_tokenType("throw");
  IElementType THROWS = new TAC_tokenType("throws");
  IElementType UEQ = new TAC_tokenType("!=");
  IElementType VOID = new TAC_tokenType("void");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == CLASS_HEAD) {
        return new TACClassHeadImpl(node);
      }
      else if (type == DEF_METHOD_NAME) {
        return new TACDefMethodNameImpl(node);
      }
      else if (type == FIELDS_DECLARATION) {
        return new TACFieldsDeclarationImpl(node);
      }
      else if (type == INSTR) {
        return new TACInstrImpl(node);
      }
      else if (type == JAVA_OP) {
        return new TACJavaOPImpl(node);
      }
      else if (type == J_TYPE) {
        return new TACJTypeImpl(node);
      }
      else if (type == LAMBDA_PARAMS) {
        return new TACLambdaParamsImpl(node);
      }
      else if (type == LAMBDA_TYPE) {
        return new TACLambdaTypeImpl(node);
      }
      else if (type == METHOD_DECLARATION) {
        return new TACMethodDeclarationImpl(node);
      }
      else if (type == METHOD_HEAD) {
        return new TACMethodHeadImpl(node);
      }
      else if (type == METHOD_NAME) {
        return new TACMethodNameImpl(node);
      }
      else if (type == MODIFIER_V) {
        return new TACModifierVImpl(node);
      }
      else if (type == PARAMS) {
        return new TACParamsImpl(node);
      }
      else if (type == TYPE) {
        return new TACTypeImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
