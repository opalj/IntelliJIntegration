// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static JavaByteCodeLanguage.psi.JavaByteCodeTypes.*;
import static JavaByteCodeLanguage.parser.JavaByteCodeParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class JavaByteCodeParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == INSTR) {
      r = Instr(b, 0);
    }
    else if (t == JAVA_OP) {
      r = JavaOP(b, 0);
    }
    else if (t == LOC_VAR_TABLE_DELERATION) {
      r = LocVarTableDeleration(b, 0);
    }
    else if (t == METHOD_DECLARATION) {
      r = MethodDeclaration(b, 0);
    }
    else if (t == METHOD_HEAD) {
      r = MethodHead(b, 0);
    }
    else if (t == METHOD_NAME) {
      r = MethodName(b, 0);
    }
    else if (t == MODIFIER) {
      r = Modifier(b, 0);
    }
    else if (t == TYPE) {
      r = Type(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return jbcFile(b, l + 1);
  }

  /* ********************************************************** */
  // INST ("("((JavaOP|NUMBER|STRING)','?)*")"|JavaOP)?
  public static boolean Instr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr")) return false;
    if (!nextTokenIs(b, INST)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INST);
    r = r && Instr_1(b, l + 1);
    exit_section_(b, m, INSTR, r);
    return r;
  }

  // ("("((JavaOP|NUMBER|STRING)','?)*")"|JavaOP)?
  private static boolean Instr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_1")) return false;
    Instr_1_0(b, l + 1);
    return true;
  }

  // "("((JavaOP|NUMBER|STRING)','?)*")"|JavaOP
  private static boolean Instr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Instr_1_0_0(b, l + 1);
    if (!r) r = JavaOP(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "("((JavaOP|NUMBER|STRING)','?)*")"
  private static boolean Instr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "(");
    r = r && Instr_1_0_0_1(b, l + 1);
    r = r && consumeToken(b, ")");
    exit_section_(b, m, null, r);
    return r;
  }

  // ((JavaOP|NUMBER|STRING)','?)*
  private static boolean Instr_1_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_1_0_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Instr_1_0_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Instr_1_0_0_1", c)) break;
    }
    return true;
  }

  // (JavaOP|NUMBER|STRING)','?
  private static boolean Instr_1_0_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_1_0_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Instr_1_0_0_1_0_0(b, l + 1);
    r = r && Instr_1_0_0_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // JavaOP|NUMBER|STRING
  private static boolean Instr_1_0_0_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_1_0_0_1_0_0")) return false;
    boolean r;
    r = JavaOP(b, l + 1);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, STRING);
    return r;
  }

  // ','?
  private static boolean Instr_1_0_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_1_0_0_1_0_1")) return false;
    consumeToken(b, ",");
    return true;
  }

  /* ********************************************************** */
  // NUMBER NUMBER Instr
  static boolean InstructionBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InstructionBody")) return false;
    if (!nextTokenIs(b, NUMBER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NUMBER, NUMBER);
    r = r && Instr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PC Line Instruction
  static boolean InstructionHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InstructionHead")) return false;
    if (!nextTokenIs(b, PC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PC, LINE, INSTRUCTION);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ((Type|STRINGVAR)('{'Type MethodName'}')?)+| 'lvIndex=' NUMBER
  public static boolean JavaOP(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JavaOP")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, JAVA_OP, "<java op>");
    r = JavaOP_0(b, l + 1);
    if (!r) r = JavaOP_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((Type|STRINGVAR)('{'Type MethodName'}')?)+
  private static boolean JavaOP_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JavaOP_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = JavaOP_0_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!JavaOP_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "JavaOP_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // (Type|STRINGVAR)('{'Type MethodName'}')?
  private static boolean JavaOP_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JavaOP_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = JavaOP_0_0_0(b, l + 1);
    r = r && JavaOP_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Type|STRINGVAR
  private static boolean JavaOP_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JavaOP_0_0_0")) return false;
    boolean r;
    r = Type(b, l + 1);
    if (!r) r = consumeToken(b, STRINGVAR);
    return r;
  }

  // ('{'Type MethodName'}')?
  private static boolean JavaOP_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JavaOP_0_0_1")) return false;
    JavaOP_0_0_1_0(b, l + 1);
    return true;
  }

  // '{'Type MethodName'}'
  private static boolean JavaOP_0_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JavaOP_0_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "{");
    r = r && Type(b, l + 1);
    r = r && MethodName(b, l + 1);
    r = r && consumeToken(b, "}");
    exit_section_(b, m, null, r);
    return r;
  }

  // 'lvIndex=' NUMBER
  private static boolean JavaOP_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JavaOP_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "lvIndex=");
    r = r && consumeToken(b, NUMBER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "["NUMBER ">" NUMBER ")" "=>"
  static boolean LocVarTableBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LocVarTableBody")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[");
    r = r && consumeToken(b, NUMBER);
    r = r && consumeToken(b, ">");
    r = r && consumeToken(b, NUMBER);
    r = r && consumeToken(b, ")");
    r = r && consumeToken(b, "=>");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LocVarTableHead LocVarTableBody*
  public static boolean LocVarTableDeleration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LocVarTableDeleration")) return false;
    if (!nextTokenIs(b, LOCALVARIABLETABLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = LocVarTableHead(b, l + 1);
    r = r && LocVarTableDeleration_1(b, l + 1);
    exit_section_(b, m, LOC_VAR_TABLE_DELERATION, r);
    return r;
  }

  // LocVarTableBody*
  private static boolean LocVarTableDeleration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LocVarTableDeleration_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!LocVarTableBody(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LocVarTableDeleration_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // LocalVariableTable
  static boolean LocVarTableHead(PsiBuilder b, int l) {
    return consumeToken(b, LOCALVARIABLETABLE);
  }

  /* ********************************************************** */
  // MethodHead InstructionHead InstructionBody* LocVarTableDeleration?
  public static boolean MethodDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, METHOD_DECLARATION, "<method declaration>");
    r = MethodHead(b, l + 1);
    r = r && InstructionHead(b, l + 1);
    r = r && MethodDeclaration_2(b, l + 1);
    r = r && MethodDeclaration_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // InstructionBody*
  private static boolean MethodDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodDeclaration_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!InstructionBody(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MethodDeclaration_2", c)) break;
    }
    return true;
  }

  // LocVarTableDeleration?
  private static boolean MethodDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodDeclaration_3")) return false;
    LocVarTableDeleration(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Modifier? Type MethodName
  public static boolean MethodHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodHead")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, METHOD_HEAD, "<method head>");
    r = MethodHead_0(b, l + 1);
    r = r && Type(b, l + 1);
    r = r && MethodName(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // Modifier?
  private static boolean MethodHead_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodHead_0")) return false;
    Modifier(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ('<init>'|'<clinit>'|STRINGVAR)('('(Type ','?)*')')?
  //     | STRINGVAR
  public static boolean MethodName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodName")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, METHOD_NAME, "<method name>");
    r = MethodName_0(b, l + 1);
    if (!r) r = consumeToken(b, STRINGVAR);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ('<init>'|'<clinit>'|STRINGVAR)('('(Type ','?)*')')?
  private static boolean MethodName_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodName_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MethodName_0_0(b, l + 1);
    r = r && MethodName_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '<init>'|'<clinit>'|STRINGVAR
  private static boolean MethodName_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodName_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "<init>");
    if (!r) r = consumeToken(b, "<clinit>");
    if (!r) r = consumeToken(b, STRINGVAR);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('('(Type ','?)*')')?
  private static boolean MethodName_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodName_0_1")) return false;
    MethodName_0_1_0(b, l + 1);
    return true;
  }

  // '('(Type ','?)*')'
  private static boolean MethodName_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodName_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "(");
    r = r && MethodName_0_1_0_1(b, l + 1);
    r = r && consumeToken(b, ")");
    exit_section_(b, m, null, r);
    return r;
  }

  // (Type ','?)*
  private static boolean MethodName_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodName_0_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!MethodName_0_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MethodName_0_1_0_1", c)) break;
    }
    return true;
  }

  // Type ','?
  private static boolean MethodName_0_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodName_0_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Type(b, l + 1);
    r = r && MethodName_0_1_0_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ','?
  private static boolean MethodName_0_1_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodName_0_1_0_1_0_1")) return false;
    consumeToken(b, ",");
    return true;
  }

  /* ********************************************************** */
  // PREMODIFIER? MODIFIER?
  public static boolean Modifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Modifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MODIFIER, "<modifier>");
    r = Modifier_0(b, l + 1);
    r = r && Modifier_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // PREMODIFIER?
  private static boolean Modifier_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Modifier_0")) return false;
    consumeToken(b, PREMODIFIER);
    return true;
  }

  // MODIFIER?
  private static boolean Modifier_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Modifier_1")) return false;
    consumeToken(b, MODIFIER);
    return true;
  }

  /* ********************************************************** */
  // (JAVATYPE | PRIMITIVETYPE)('[]')? | JAVATYPE
  public static boolean Type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type")) return false;
    if (!nextTokenIs(b, "<type>", JAVATYPE, PRIMITIVETYPE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE, "<type>");
    r = Type_0(b, l + 1);
    if (!r) r = consumeToken(b, JAVATYPE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (JAVATYPE | PRIMITIVETYPE)('[]')?
  private static boolean Type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Type_0_0(b, l + 1);
    r = r && Type_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // JAVATYPE | PRIMITIVETYPE
  private static boolean Type_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type_0_0")) return false;
    boolean r;
    r = consumeToken(b, JAVATYPE);
    if (!r) r = consumeToken(b, PRIMITIVETYPE);
    return r;
  }

  // ('[]')?
  private static boolean Type_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type_0_1")) return false;
    Type_0_1_0(b, l + 1);
    return true;
  }

  // ('[]')
  private static boolean Type_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[]");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (MethodDeclaration)
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MethodDeclaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean jbcFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jbcFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "jbcFile", c)) break;
    }
    return true;
  }

}
