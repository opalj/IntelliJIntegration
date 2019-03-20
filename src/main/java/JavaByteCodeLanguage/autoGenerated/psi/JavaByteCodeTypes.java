// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import JavaByteCodeLanguage.psi.JavaByteCodeElementType;
import JavaByteCodeLanguage.psi.JavaByteCodeTokenType;
import JavaByteCodeLanguage.autoGenerated.psi.impl.*;

public interface JavaByteCodeTypes {

  IElementType ANNOTATION = new JavaByteCodeElementType("ANNOTATION");
  IElementType CLASS_HEAD = new JavaByteCodeElementType("CLASS_HEAD");
  IElementType DEF_METHOD_NAME = new JavaByteCodeElementType("DEF_METHOD_NAME");
  IElementType EXCEPTION_TABLE_DECLARATION = new JavaByteCodeElementType("EXCEPTION_TABLE_DECLARATION");
  IElementType FIELDS_DECLARATION = new JavaByteCodeElementType("FIELDS_DECLARATION");
  IElementType INSTR = new JavaByteCodeElementType("INSTR");
  IElementType INSTRUCTION_BODY = new JavaByteCodeElementType("INSTRUCTION_BODY");
  IElementType JAVA_OP = new JavaByteCodeElementType("JAVA_OP");
  IElementType J_TYPE = new JavaByteCodeElementType("J_TYPE");
  IElementType LAMBDA_PARAMS = new JavaByteCodeElementType("LAMBDA_PARAMS");
  IElementType LAMBDA_TYPE = new JavaByteCodeElementType("LAMBDA_TYPE");
  IElementType LINE_NUMBER = new JavaByteCodeElementType("LINE_NUMBER");
  IElementType LOC_VAR_TABLE_DECLARATION = new JavaByteCodeElementType("LOC_VAR_TABLE_DECLARATION");
  IElementType METHOD_AREA = new JavaByteCodeElementType("METHOD_AREA");
  IElementType METHOD_DECLARATION = new JavaByteCodeElementType("METHOD_DECLARATION");
  IElementType METHOD_HEAD = new JavaByteCodeElementType("METHOD_HEAD");
  IElementType METHOD_NAME = new JavaByteCodeElementType("METHOD_NAME");
  IElementType MODIFIER_V = new JavaByteCodeElementType("MODIFIER_V");
  IElementType PARAMS = new JavaByteCodeElementType("PARAMS");
  IElementType PC_NUMBER = new JavaByteCodeElementType("PC_NUMBER");
  IElementType STACK_MAP_TABLE_DECLARATION = new JavaByteCodeElementType("STACK_MAP_TABLE_DECLARATION");
  IElementType TYPE = new JavaByteCodeElementType("TYPE");

  IElementType AT = new JavaByteCodeTokenType("@");
  IElementType COLON = new JavaByteCodeTokenType(":");
  IElementType COMMA = new JavaByteCodeTokenType(",");
  IElementType COMMENT = new JavaByteCodeTokenType("COMMENT");
  IElementType DOT = new JavaByteCodeTokenType(".");
  IElementType EXTENDS = new JavaByteCodeTokenType("extends");
  IElementType FIELDS = new JavaByteCodeTokenType("Fields");
  IElementType FUCKINGTOKEN = new JavaByteCodeTokenType("lvIndex=");
  IElementType IMPLEMENTS = new JavaByteCodeTokenType("implements");
  IElementType INSTRUCTIONHEAD = new JavaByteCodeTokenType("INSTRUCTIONHEAD");
  IElementType JAVATYPEHEAD = new JavaByteCodeTokenType("JAVATYPEHEAD");
  IElementType LBRACKET = new JavaByteCodeTokenType("LBRACKET");
  IElementType METHODS = new JavaByteCodeTokenType("Methods");
  IElementType MNEMONIC = new JavaByteCodeTokenType("MNEMONIC");
  IElementType MODIFIER = new JavaByteCodeTokenType("MODIFIER");
  IElementType NUMBER = new JavaByteCodeTokenType("NUMBER");
  IElementType PRIMITIVETYPE = new JavaByteCodeTokenType("PRIMITIVETYPE");
  IElementType RBRACKET = new JavaByteCodeTokenType("RBRACKET");
  IElementType SEMICOLON = new JavaByteCodeTokenType(";");
  IElementType STRING = new JavaByteCodeTokenType("STRING");
  IElementType STRINGVAR = new JavaByteCodeTokenType("STRINGVAR");
  IElementType SWITCH = new JavaByteCodeTokenType("?");
  IElementType TABLENAME = new JavaByteCodeTokenType("TABLENAME");
  IElementType THIS = new JavaByteCodeTokenType("this");
  IElementType THROWS = new JavaByteCodeTokenType("throws");
  IElementType TO = new JavaByteCodeTokenType("TO");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ANNOTATION) {
        return new JavaByteCodeAnnotationImpl(node);
      }
      else if (type == CLASS_HEAD) {
        return new JavaByteCodeClassHeadImpl(node);
      }
      else if (type == DEF_METHOD_NAME) {
        return new JavaByteCodeDefMethodNameImpl(node);
      }
      else if (type == EXCEPTION_TABLE_DECLARATION) {
        return new JavaByteCodeExceptionTableDeclarationImpl(node);
      }
      else if (type == FIELDS_DECLARATION) {
        return new JavaByteCodeFieldsDeclarationImpl(node);
      }
      else if (type == INSTR) {
        return new JavaByteCodeInstrImpl(node);
      }
      else if (type == INSTRUCTION_BODY) {
        return new JavaByteCodeInstructionBodyImpl(node);
      }
      else if (type == JAVA_OP) {
        return new JavaByteCodeJavaOPImpl(node);
      }
      else if (type == J_TYPE) {
        return new JavaByteCodeJTypeImpl(node);
      }
      else if (type == LAMBDA_PARAMS) {
        return new JavaByteCodeLambdaParamsImpl(node);
      }
      else if (type == LAMBDA_TYPE) {
        return new JavaByteCodeLambdaTypeImpl(node);
      }
      else if (type == LINE_NUMBER) {
        return new JavaByteCodeLineNumberImpl(node);
      }
      else if (type == LOC_VAR_TABLE_DECLARATION) {
        return new JavaByteCodeLocVarTableDeclarationImpl(node);
      }
      else if (type == METHOD_AREA) {
        return new JavaByteCodeMethodAreaImpl(node);
      }
      else if (type == METHOD_DECLARATION) {
        return new JavaByteCodeMethodDeclarationImpl(node);
      }
      else if (type == METHOD_HEAD) {
        return new JavaByteCodeMethodHeadImpl(node);
      }
      else if (type == METHOD_NAME) {
        return new JavaByteCodeMethodNameImpl(node);
      }
      else if (type == MODIFIER_V) {
        return new JavaByteCodeModifierVImpl(node);
      }
      else if (type == PARAMS) {
        return new JavaByteCodeParamsImpl(node);
      }
      else if (type == PC_NUMBER) {
        return new JavaByteCodePcNumberImpl(node);
      }
      else if (type == STACK_MAP_TABLE_DECLARATION) {
        return new JavaByteCodeStackMapTableDeclarationImpl(node);
      }
      else if (type == TYPE) {
        return new JavaByteCodeTypeImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
