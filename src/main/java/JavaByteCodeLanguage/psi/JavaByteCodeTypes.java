// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import JavaByteCodeLanguage.psi.impl.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

public interface JavaByteCodeTypes {

  IElementType INSTR = new JavaByteCodeElementType("INSTR");
  IElementType JAVA_OP = new JavaByteCodeElementType("JAVA_OP");
  IElementType LOC_VAR_TABLE_DELERATION = new JavaByteCodeElementType("LOC_VAR_TABLE_DELERATION");
  IElementType METHOD_DECLARATION = new JavaByteCodeElementType("METHOD_DECLARATION");
  IElementType METHOD_HEAD = new JavaByteCodeElementType("METHOD_HEAD");
  IElementType METHOD_NAME = new JavaByteCodeElementType("METHOD_NAME");
  IElementType MODIFIER = new JavaByteCodeElementType("MODIFIER");
  IElementType TYPE = new JavaByteCodeElementType("TYPE");

  IElementType COMMENT = new JavaByteCodeTokenType("COMMENT");
  IElementType EOF = new JavaByteCodeTokenType("\\n");
  IElementType INST = new JavaByteCodeTokenType("INST");
  IElementType INSTRUCTION = new JavaByteCodeTokenType("Instruction");
  IElementType JAVATYPE = new JavaByteCodeTokenType("JAVATYPE");
  IElementType LINE = new JavaByteCodeTokenType("Line");
  IElementType LOCALVARIABLETABLE = new JavaByteCodeTokenType("LocalVariableTable");
  IElementType NUMBER = new JavaByteCodeTokenType("NUMBER");
  IElementType PC = new JavaByteCodeTokenType("PC");
  IElementType PREMODIFIER = new JavaByteCodeTokenType("PREMODIFIER");
  IElementType PRIMITIVETYPE = new JavaByteCodeTokenType("PRIMITIVETYPE");
  IElementType STRING = new JavaByteCodeTokenType("STRING");
  IElementType STRINGVAR = new JavaByteCodeTokenType("STRINGVAR");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == INSTR) {
        return new JavaByteCodeInstrImpl(node);
      } else if (type == JAVA_OP) {
        return new JavaByteCodeJavaOPImpl(node);
      } else if (type == LOC_VAR_TABLE_DELERATION) {
        return new JavaByteCodeLocVarTableDelerationImpl(node);
      } else if (type == METHOD_DECLARATION) {
        return new JavaByteCodeMethodDeclarationImpl(node);
      } else if (type == METHOD_HEAD) {
        return new JavaByteCodeMethodHeadImpl(node);
      } else if (type == METHOD_NAME) {
        return new JavaByteCodeMethodNameImpl(node);
      } else if (type == MODIFIER) {
        return new JavaByteCodeModifierImpl(node);
      } else if (type == TYPE) {
        return new JavaByteCodeTypeImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
