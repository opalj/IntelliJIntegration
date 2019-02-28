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
    if (t == CLASS_HEAD) {
      r = ClassHead(b, 0);
    }
    else if (t == DEF_METHOD_NAME) {
      r = DefMethodName(b, 0);
    }
    else if (t == FIELDS_DECLARATION) {
      r = FieldsDeclaration(b, 0);
    }
    else if (t == INSTR) {
      r = Instr(b, 0);
    }
    else if (t == J_TYPE) {
      r = JType(b, 0);
    }
    else if (t == JAVA_OP) {
      r = JavaOP(b, 0);
    }
    else if (t == LOC_VAR_TABLE_DECLARATION) {
      r = LocVarTableDeclaration(b, 0);
    }
    else if (t == LOC_VAR_TABLE_HEAD) {
      r = LocVarTableHead(b, 0);
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
    else if (t == MODIFIER_V) {
      r = ModifierV(b, 0);
    }
    else if (t == TYPE) {
      r = Type(b, 0);
    }
    else if (t == PARAMS) {
      r = params(b, 0);
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
  // ModifierV? class DefMethodName (extends JType)? (implements (JType(",")?)+)?
  public static boolean ClassHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CLASS_HEAD, "<class head>");
    r = ClassHead_0(b, l + 1);
    r = r && consumeToken(b, CLASS);
    r = r && DefMethodName(b, l + 1);
    r = r && ClassHead_3(b, l + 1);
    r = r && ClassHead_4(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ModifierV?
  private static boolean ClassHead_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_0")) return false;
    ModifierV(b, l + 1);
    return true;
  }

  // (extends JType)?
  private static boolean ClassHead_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_3")) return false;
    ClassHead_3_0(b, l + 1);
    return true;
  }

  // extends JType
  private static boolean ClassHead_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTENDS);
    r = r && JType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (implements (JType(",")?)+)?
  private static boolean ClassHead_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_4")) return false;
    ClassHead_4_0(b, l + 1);
    return true;
  }

  // implements (JType(",")?)+
  private static boolean ClassHead_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IMPLEMENTS);
    r = r && ClassHead_4_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (JType(",")?)+
  private static boolean ClassHead_4_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_4_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ClassHead_4_0_1_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!ClassHead_4_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ClassHead_4_0_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // JType(",")?
  private static boolean ClassHead_4_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_4_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = JType(b, l + 1);
    r = r && ClassHead_4_0_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (",")?
  private static boolean ClassHead_4_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_4_0_1_0_1")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // STRINGVAR
  public static boolean DefMethodName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DefMethodName")) return false;
    if (!nextTokenIs(b, STRINGVAR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STRINGVAR);
    exit_section_(b, m, DEF_METHOD_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // Fields (Type DefMethodName)*
  public static boolean FieldsDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldsDeclaration")) return false;
    if (!nextTokenIs(b, FIELDS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FIELDS);
    r = r && FieldsDeclaration_1(b, l + 1);
    exit_section_(b, m, FIELDS_DECLARATION, r);
    return r;
  }

  // (Type DefMethodName)*
  private static boolean FieldsDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldsDeclaration_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!FieldsDeclaration_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "FieldsDeclaration_1", c)) break;
    }
    return true;
  }

  // Type DefMethodName
  private static boolean FieldsDeclaration_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldsDeclaration_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Type(b, l + 1);
    r = r && DefMethodName(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ((INST|LOAD_INSTR) ("("((JavaOP|NUMBER|STRING)','?)*")"|JavaOP)?) | (PUT_GET_INSTR Type) | // TODO {recoverWhile=recoverTillNextInstr}
  public static boolean Instr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, INSTR, "<instr>");
    r = Instr_0(b, l + 1);
    if (!r) r = Instr_1(b, l + 1);
    if (!r) r = consumeToken(b, INSTR_2_0);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (INST|LOAD_INSTR) ("("((JavaOP|NUMBER|STRING)','?)*")"|JavaOP)?
  private static boolean Instr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Instr_0_0(b, l + 1);
    r = r && Instr_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // INST|LOAD_INSTR
  private static boolean Instr_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_0_0")) return false;
    boolean r;
    r = consumeToken(b, INST);
    if (!r) r = consumeToken(b, LOAD_INSTR);
    return r;
  }

  // ("("((JavaOP|NUMBER|STRING)','?)*")"|JavaOP)?
  private static boolean Instr_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_0_1")) return false;
    Instr_0_1_0(b, l + 1);
    return true;
  }

  // "("((JavaOP|NUMBER|STRING)','?)*")"|JavaOP
  private static boolean Instr_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Instr_0_1_0_0(b, l + 1);
    if (!r) r = JavaOP(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "("((JavaOP|NUMBER|STRING)','?)*")"
  private static boolean Instr_0_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_0_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "(");
    r = r && Instr_0_1_0_0_1(b, l + 1);
    r = r && consumeToken(b, ")");
    exit_section_(b, m, null, r);
    return r;
  }

  // ((JavaOP|NUMBER|STRING)','?)*
  private static boolean Instr_0_1_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_0_1_0_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Instr_0_1_0_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Instr_0_1_0_0_1", c)) break;
    }
    return true;
  }

  // (JavaOP|NUMBER|STRING)','?
  private static boolean Instr_0_1_0_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_0_1_0_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Instr_0_1_0_0_1_0_0(b, l + 1);
    r = r && Instr_0_1_0_0_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // JavaOP|NUMBER|STRING
  private static boolean Instr_0_1_0_0_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_0_1_0_0_1_0_0")) return false;
    boolean r;
    r = JavaOP(b, l + 1);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, STRING);
    return r;
  }

  // ','?
  private static boolean Instr_0_1_0_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_0_1_0_0_1_0_1")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  // PUT_GET_INSTR Type
  private static boolean Instr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PUT_GET_INSTR);
    r = r && Type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
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
  // (STRINGVAR'.')*STRINGVAR
  public static boolean JType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JType")) return false;
    if (!nextTokenIs(b, STRINGVAR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = JType_0(b, l + 1);
    r = r && consumeToken(b, STRINGVAR);
    exit_section_(b, m, J_TYPE, r);
    return r;
  }

  // (STRINGVAR'.')*
  private static boolean JType_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JType_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!JType_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "JType_0", c)) break;
    }
    return true;
  }

  // STRINGVAR'.'
  private static boolean JType_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JType_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, STRINGVAR, DOT);
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
  // "["NUMBER ">" NUMBER ")" "=>" Type (this | STRINGVAR)
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
    r = r && Type(b, l + 1);
    r = r && LocVarTableBody_7(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // this | STRINGVAR
  private static boolean LocVarTableBody_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LocVarTableBody_7")) return false;
    boolean r;
    r = consumeToken(b, THIS);
    if (!r) r = consumeToken(b, STRINGVAR);
    return r;
  }

  /* ********************************************************** */
  // LocVarTableHead LocVarTableBody*
  public static boolean LocVarTableDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LocVarTableDeclaration")) return false;
    if (!nextTokenIs(b, LOCALVARIABLETABLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = LocVarTableHead(b, l + 1);
    r = r && LocVarTableDeclaration_1(b, l + 1);
    exit_section_(b, m, LOC_VAR_TABLE_DECLARATION, r);
    return r;
  }

  // LocVarTableBody*
  private static boolean LocVarTableDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LocVarTableDeclaration_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!LocVarTableBody(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LocVarTableDeclaration_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // LocalVariableTable
  public static boolean LocVarTableHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LocVarTableHead")) return false;
    if (!nextTokenIs(b, LOCALVARIABLETABLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LOCALVARIABLETABLE);
    exit_section_(b, m, LOC_VAR_TABLE_HEAD, r);
    return r;
  }

  /* ********************************************************** */
  // MethodHead InstructionHead InstructionBody* LocVarTableDeclaration?
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

  // LocVarTableDeclaration?
  private static boolean MethodDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodDeclaration_3")) return false;
    LocVarTableDeclaration(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ModifierV? Type MethodName ("�" (STRINGVAR ","?)* "�")?
  public static boolean MethodHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodHead")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, METHOD_HEAD, "<method head>");
    r = MethodHead_0(b, l + 1);
    r = r && Type(b, l + 1);
    r = r && MethodName(b, l + 1);
    r = r && MethodHead_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ModifierV?
  private static boolean MethodHead_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodHead_0")) return false;
    ModifierV(b, l + 1);
    return true;
  }

  // ("�" (STRINGVAR ","?)* "�")?
  private static boolean MethodHead_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodHead_3")) return false;
    MethodHead_3_0(b, l + 1);
    return true;
  }

  // "�" (STRINGVAR ","?)* "�"
  private static boolean MethodHead_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodHead_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "�");
    r = r && MethodHead_3_0_1(b, l + 1);
    r = r && consumeToken(b, "�");
    exit_section_(b, m, null, r);
    return r;
  }

  // (STRINGVAR ","?)*
  private static boolean MethodHead_3_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodHead_3_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!MethodHead_3_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MethodHead_3_0_1", c)) break;
    }
    return true;
  }

  // STRINGVAR ","?
  private static boolean MethodHead_3_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodHead_3_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STRINGVAR);
    r = r && MethodHead_3_0_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ","?
  private static boolean MethodHead_3_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodHead_3_0_1_0_1")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // ('<init>'|'<clinit>'|DefMethodName) params
  public static boolean MethodName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodName")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, METHOD_NAME, "<method name>");
    r = MethodName_0(b, l + 1);
    r = r && params(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '<init>'|'<clinit>'|DefMethodName
  private static boolean MethodName_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodName_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "<init>");
    if (!r) r = consumeToken(b, "<clinit>");
    if (!r) r = DefMethodName(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (PREMODIFIER)?(MODIFIER)*
  public static boolean ModifierV(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModifierV")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MODIFIER_V, "<modifier v>");
    r = ModifierV_0(b, l + 1);
    r = r && ModifierV_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (PREMODIFIER)?
  private static boolean ModifierV_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModifierV_0")) return false;
    consumeToken(b, PREMODIFIER);
    return true;
  }

  // (MODIFIER)*
  private static boolean ModifierV_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModifierV_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, MODIFIER)) break;
      if (!empty_element_parsed_guard_(b, "ModifierV_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (JType | PRIMITIVETYPE)('[]')*
  public static boolean Type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type")) return false;
    if (!nextTokenIs(b, "<type>", PRIMITIVETYPE, STRINGVAR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE, "<type>");
    r = Type_0(b, l + 1);
    r = r && Type_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // JType | PRIMITIVETYPE
  private static boolean Type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type_0")) return false;
    boolean r;
    r = JType(b, l + 1);
    if (!r) r = consumeToken(b, PRIMITIVETYPE);
    return r;
  }

  // ('[]')*
  private static boolean Type_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Type_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Type_1", c)) break;
    }
    return true;
  }

  // ('[]')
  private static boolean Type_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[]");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ClassHead FieldsDeclaration MethodDeclaration*
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ClassHead(b, l + 1);
    r = r && FieldsDeclaration(b, l + 1);
    r = r && item__2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // MethodDeclaration*
  private static boolean item__2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item__2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!MethodDeclaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "item__2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // item_
  static boolean jbcFile(PsiBuilder b, int l) {
    return item_(b, l + 1);
  }

  /* ********************************************************** */
  // ('('(Type ','?)*')')?
  public static boolean params(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params")) return false;
    Marker m = enter_section_(b, l, _NONE_, PARAMS, "<params>");
    params_0(b, l + 1);
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  // '('(Type ','?)*')'
  private static boolean params_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "(");
    r = r && params_0_1(b, l + 1);
    r = r && consumeToken(b, ")");
    exit_section_(b, m, null, r);
    return r;
  }

  // (Type ','?)*
  private static boolean params_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!params_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "params_0_1", c)) break;
    }
    return true;
  }

  // Type ','?
  private static boolean params_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Type(b, l + 1);
    r = r && params_0_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ','?
  private static boolean params_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params_0_1_0_1")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // !NUMBER
  static boolean recoverTillNextInstr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "recoverTillNextInstr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, NUMBER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}
