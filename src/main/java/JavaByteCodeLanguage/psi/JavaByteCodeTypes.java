// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import JavaByteCodeLanguage.psi.impl.*;

public interface JavaByteCodeTypes {

  IElementType CLASS_HEAD = new JavaByteCodeElementType("CLASS_HEAD");
  IElementType DEF_METHOD_NAME = new JavaByteCodeElementType("DEF_METHOD_NAME");
  IElementType FIELDS_DECLARATION = new JavaByteCodeElementType("FIELDS_DECLARATION");
  IElementType INSTR = new JavaByteCodeElementType("INSTR");
  IElementType JAVA_OP = new JavaByteCodeElementType("JAVA_OP");
  IElementType J_TYPE = new JavaByteCodeElementType("J_TYPE");
  IElementType LOC_VAR_TABLE_DECLARATION = new JavaByteCodeElementType("LOC_VAR_TABLE_DECLARATION");
  IElementType LOC_VAR_TABLE_HEAD = new JavaByteCodeElementType("LOC_VAR_TABLE_HEAD");
  IElementType METHOD_DECLARATION = new JavaByteCodeElementType("METHOD_DECLARATION");
  IElementType METHOD_HEAD = new JavaByteCodeElementType("METHOD_HEAD");
  IElementType METHOD_NAME = new JavaByteCodeElementType("METHOD_NAME");
  IElementType MODIFIER_V = new JavaByteCodeElementType("MODIFIER_V");
  IElementType PARAMS = new JavaByteCodeElementType("PARAMS");
  IElementType TYPE = new JavaByteCodeElementType("TYPE");

  IElementType CLASS = new JavaByteCodeTokenType("class");
  IElementType COMMA = new JavaByteCodeTokenType(",");
  IElementType COMMENT = new JavaByteCodeTokenType("COMMENT");
  IElementType CONSTMETHODNAMES = new JavaByteCodeTokenType("CONSTMETHODNAMES");
  IElementType DOT = new JavaByteCodeTokenType(".");
  IElementType EOF = new JavaByteCodeTokenType("\\n");
  IElementType EXTENDS = new JavaByteCodeTokenType("extends");
  IElementType FIELDS = new JavaByteCodeTokenType("Fields");
  IElementType IMPLEMENTS = new JavaByteCodeTokenType("implements");
  IElementType INST = new JavaByteCodeTokenType("INST");
  IElementType INSTRUCTION = new JavaByteCodeTokenType("Instruction");
  IElementType INSTR_2_0 = new JavaByteCodeTokenType("Instr_2_0");
  IElementType LBRACKET = new JavaByteCodeTokenType("LBRACKET");
  IElementType LINE = new JavaByteCodeTokenType("Line");
  IElementType LOAD_INSTR = new JavaByteCodeTokenType("LOAD_INSTR");
  IElementType LOCALVARIABLETABLE = new JavaByteCodeTokenType("LocalVariableTable");
  IElementType MODIFIER = new JavaByteCodeTokenType("MODIFIER");
  IElementType NUMBER = new JavaByteCodeTokenType("NUMBER");
  IElementType PC = new JavaByteCodeTokenType("PC");
  IElementType PREMODIFIER = new JavaByteCodeTokenType("PREMODIFIER");
  IElementType PRIMITIVETYPE = new JavaByteCodeTokenType("PRIMITIVETYPE");
  IElementType PUT_GET_INSTR = new JavaByteCodeTokenType("PUT_GET_INSTR");
  IElementType RBRACKET = new JavaByteCodeTokenType("RBRACKET");
  IElementType STRING = new JavaByteCodeTokenType("STRING");
  IElementType STRINGVAR = new JavaByteCodeTokenType("STRINGVAR");
  IElementType THIS = new JavaByteCodeTokenType("this");
  IElementType TO = new JavaByteCodeTokenType("TO");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == CLASS_HEAD) {
        return new JavaByteCodeClassHeadImpl(node);
      }
      else if (type == DEF_METHOD_NAME) {
        return new JavaByteCodeDefMethodNameImpl(node);
      }
      else if (type == FIELDS_DECLARATION) {
        return new JavaByteCodeFieldsDeclarationImpl(node);
      }
      else if (type == INSTR) {
        return new JavaByteCodeInstrImpl(node);
      }
      else if (type == JAVA_OP) {
        return new JavaByteCodeJavaOPImpl(node);
      }
      else if (type == J_TYPE) {
        return new JavaByteCodeJTypeImpl(node);
      }
      else if (type == LOC_VAR_TABLE_DECLARATION) {
        return new JavaByteCodeLocVarTableDeclarationImpl(node);
      }
      else if (type == LOC_VAR_TABLE_HEAD) {
        return new JavaByteCodeLocVarTableHeadImpl(node);
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
      else if (type == TYPE) {
        return new JavaByteCodeTypeImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
