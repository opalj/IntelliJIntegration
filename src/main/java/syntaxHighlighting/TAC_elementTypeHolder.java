// This is a generated file. Not intended for manual editing.
package syntaxHighlighting;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import syntaxHighlighting.impl.*;

public interface TAC_elementTypeHolder {

  IElementType ANNOTATION = new TAC_elementType("ANNOTATION");
  IElementType ATTRIBUTES_AREA = new TAC_elementType("ATTRIBUTES_AREA");
  IElementType CLASS_HEAD = new TAC_elementType("CLASS_HEAD");
  IElementType DEF_METHOD_NAME = new TAC_elementType("DEF_METHOD_NAME");
  IElementType FIELDS_DECLARATION = new TAC_elementType("FIELDS_DECLARATION");
  IElementType FIELD_AREA = new TAC_elementType("FIELD_AREA");
  IElementType INNER_TABLE = new TAC_elementType("INNER_TABLE");
  IElementType INSTR = new TAC_elementType("INSTR");
  IElementType J_TYPE = new TAC_elementType("J_TYPE");
  IElementType LAMBDA_PARAMS = new TAC_elementType("LAMBDA_PARAMS");
  IElementType LAMBDA_TYPE = new TAC_elementType("LAMBDA_TYPE");
  IElementType METHOD_AREA = new TAC_elementType("METHOD_AREA");
  IElementType METHOD_DECLARATION = new TAC_elementType("METHOD_DECLARATION");
  IElementType METHOD_HEAD = new TAC_elementType("METHOD_HEAD");
  IElementType METHOD_NAME = new TAC_elementType("METHOD_NAME");
  IElementType MODIFIER_V = new TAC_elementType("MODIFIER_V");
  IElementType PARAMS = new TAC_elementType("PARAMS");
  IElementType SWITCH_INST = new TAC_elementType("SWITCH_INST");
  IElementType TYPE = new TAC_elementType("TYPE");

  IElementType AT = new TAC_tokenType("@");
  IElementType ATTRIBUTES = new TAC_tokenType("Attributes");
  IElementType CLASS = new TAC_tokenType("CLASS");
  IElementType COLON = new TAC_tokenType(":");
  IElementType COMMA = new TAC_tokenType(",");
  IElementType COMMENT = new TAC_tokenType("COMMENT");
  IElementType COMPARATORS = new TAC_tokenType("COMPARATORS");
  IElementType CONSTMETHODNAMES = new TAC_tokenType("CONSTMETHODNAMES");
  IElementType DOT = new TAC_tokenType(".");
  IElementType EQ = new TAC_tokenType("=");
  IElementType EXTENDS = new TAC_tokenType("extends");
  IElementType FIELDS = new TAC_tokenType("Fields");
  IElementType IMPLEMENTS = new TAC_tokenType("implements");
  IElementType INSTRUCTIONHEAD = new TAC_tokenType("INSTRUCTIONHEAD");
  IElementType JAVATYPEHEAD = new TAC_tokenType("JAVATYPEHEAD");
  IElementType KEYWORDS = new TAC_tokenType("KEYWORDS");
  IElementType LBRACKET = new TAC_tokenType("LBRACKET");
  IElementType LEVEL = new TAC_tokenType("LEVEL");
  IElementType LVLINDEXTOKEN = new TAC_tokenType("lvIndex=");
  IElementType L_ARROW = new TAC_tokenType("L_ARROW");
  IElementType METHODS = new TAC_tokenType("Methods");
  IElementType MINUS = new TAC_tokenType("MINUS");
  IElementType MODIFIER = new TAC_tokenType("MODIFIER");
  IElementType NUMBER = new TAC_tokenType("NUMBER");
  IElementType OPERATORS = new TAC_tokenType("OPERATORS");
  IElementType PLUS = new TAC_tokenType("PLUS");
  IElementType PRIMITIVETYPE = new TAC_tokenType("PRIMITIVETYPE");
  IElementType RBRACKET = new TAC_tokenType("RBRACKET");
  IElementType R_ARROW = new TAC_tokenType("R_ARROW");
  IElementType SEMICOLON = new TAC_tokenType(";");
  IElementType SLASH = new TAC_tokenType("/");
  IElementType STRING = new TAC_tokenType("STRING");
  IElementType STRINGVAR = new TAC_tokenType("STRINGVAR");
  IElementType SWITCH = new TAC_tokenType("?");
  IElementType THIS = new TAC_tokenType("THIS");
  IElementType THROW = new TAC_tokenType("THROW");
  IElementType THROWS = new TAC_tokenType("throws");
  IElementType TO = new TAC_tokenType("TO");
  IElementType UEQ = new TAC_tokenType("UEQ");
  IElementType VOID = new TAC_tokenType("VOID");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ANNOTATION) {
        return new TACAnnotationImpl(node);
      }
      else if (type == ATTRIBUTES_AREA) {
        return new TACAttributesAreaImpl(node);
      }
      else if (type == CLASS_HEAD) {
        return new TACClassHeadImpl(node);
      }
      else if (type == DEF_METHOD_NAME) {
        return new TACDefMethodNameImpl(node);
      }
      else if (type == FIELDS_DECLARATION) {
        return new TACFieldsDeclarationImpl(node);
      }
      else if (type == FIELD_AREA) {
        return new TACFieldAreaImpl(node);
      }
      else if (type == INNER_TABLE) {
        return new TACInnerTableImpl(node);
      }
      else if (type == INSTR) {
        return new TACInstrImpl(node);
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
      else if (type == METHOD_AREA) {
        return new TACMethodAreaImpl(node);
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
      else if (type == SWITCH_INST) {
        return new TACSwitchInstImpl(node);
      }
      else if (type == TYPE) {
        return new TACTypeImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
