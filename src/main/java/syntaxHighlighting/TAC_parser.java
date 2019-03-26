// This is a generated file. Not intended for manual editing.
package syntaxHighlighting;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static syntaxHighlighting.TAC_elementTypeHolder.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class TAC_parser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == ANNOTATION) {
      r = Annotation(b, 0);
    }
    else if (t == ATTRIBUTES_AREA) {
      r = AttributesArea(b, 0);
    }
    else if (t == CLASS_HEAD) {
      r = ClassHead(b, 0);
    }
    else if (t == DEF_METHOD_NAME) {
      r = DefMethodName(b, 0);
    }
    else if (t == FIELD_AREA) {
      r = FieldArea(b, 0);
    }
    else if (t == FIELDS_DECLARATION) {
      r = FieldsDeclaration(b, 0);
    }
    else if (t == INNER_TABLE) {
      r = InnerTable(b, 0);
    }
    else if (t == INSTR) {
      r = Instr(b, 0);
    }
    else if (t == J_TYPE) {
      r = JType(b, 0);
    }
    else if (t == LAMBDA_TYPE) {
      r = LambdaType(b, 0);
    }
    else if (t == METHOD_AREA) {
      r = MethodArea(b, 0);
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
    else if (t == LAMBDA_PARAMS) {
      r = lambdaParams(b, 0);
    }
    else if (t == PARAMS) {
      r = params(b, 0);
    }
    else if (t == SWITCH_INST) {
      r = switchInst(b, 0);
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
  // AT JType
  public static boolean Annotation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Annotation")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, AT);
    r = r && JType(b, l + 1);
    exit_section_(b, m, ANNOTATION, r);
    return r;
  }

  /* ********************************************************** */
  // ATTRIBUTES LBRACKET InnerTable? RBRACKET
  public static boolean AttributesArea(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AttributesArea")) return false;
    if (!nextTokenIs(b, ATTRIBUTES)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ATTRIBUTES, LBRACKET);
    r = r && AttributesArea_2(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, ATTRIBUTES_AREA, r);
    return r;
  }

  // InnerTable?
  private static boolean AttributesArea_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AttributesArea_2")) return false;
    InnerTable(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ModifierV? (AT?) JAVATYPEHEAD JType (EXTENDS JType)? (IMPLEMENTS (JType(COMMA)?)+)?
  public static boolean ClassHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CLASS_HEAD, "<class head>");
    r = ClassHead_0(b, l + 1);
    r = r && ClassHead_1(b, l + 1);
    r = r && consumeToken(b, JAVATYPEHEAD);
    r = r && JType(b, l + 1);
    r = r && ClassHead_4(b, l + 1);
    r = r && ClassHead_5(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ModifierV?
  private static boolean ClassHead_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_0")) return false;
    ModifierV(b, l + 1);
    return true;
  }

  // AT?
  private static boolean ClassHead_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_1")) return false;
    consumeToken(b, AT);
    return true;
  }

  // (EXTENDS JType)?
  private static boolean ClassHead_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_4")) return false;
    ClassHead_4_0(b, l + 1);
    return true;
  }

  // EXTENDS JType
  private static boolean ClassHead_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTENDS);
    r = r && JType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (IMPLEMENTS (JType(COMMA)?)+)?
  private static boolean ClassHead_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_5")) return false;
    ClassHead_5_0(b, l + 1);
    return true;
  }

  // IMPLEMENTS (JType(COMMA)?)+
  private static boolean ClassHead_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IMPLEMENTS);
    r = r && ClassHead_5_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (JType(COMMA)?)+
  private static boolean ClassHead_5_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_5_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ClassHead_5_0_1_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!ClassHead_5_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ClassHead_5_0_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // JType(COMMA)?
  private static boolean ClassHead_5_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_5_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = JType(b, l + 1);
    r = r && ClassHead_5_0_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA)?
  private static boolean ClassHead_5_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassHead_5_0_1_0_1")) return false;
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
  // FIELDS LBRACKET FieldsDeclaration* RBRACKET
  public static boolean FieldArea(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldArea")) return false;
    if (!nextTokenIs(b, FIELDS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, FIELDS, LBRACKET);
    r = r && FieldArea_2(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, FIELD_AREA, r);
    return r;
  }

  // FieldsDeclaration*
  private static boolean FieldArea_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldArea_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!FieldsDeclaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "FieldArea_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // ModifierV? Type DefMethodName
  public static boolean FieldsDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldsDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FIELDS_DECLARATION, "<fields declaration>");
    r = FieldsDeclaration_0(b, l + 1);
    r = r && Type(b, l + 1);
    r = r && DefMethodName(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ModifierV?
  private static boolean FieldsDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldsDeclaration_0")) return false;
    ModifierV(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // STRINGVAR LBRACKET
  //                     (Type LBRACKET ModifierV JAVATYPEHEAD? STRINGVAR RBRACKET)*
  //                RBRACKET
  public static boolean InnerTable(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InnerTable")) return false;
    if (!nextTokenIs(b, STRINGVAR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, STRINGVAR, LBRACKET);
    r = r && InnerTable_2(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, INNER_TABLE, r);
    return r;
  }

  // (Type LBRACKET ModifierV JAVATYPEHEAD? STRINGVAR RBRACKET)*
  private static boolean InnerTable_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InnerTable_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!InnerTable_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "InnerTable_2", c)) break;
    }
    return true;
  }

  // Type LBRACKET ModifierV JAVATYPEHEAD? STRINGVAR RBRACKET
  private static boolean InnerTable_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InnerTable_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Type(b, l + 1);
    r = r && consumeToken(b, LBRACKET);
    r = r && ModifierV(b, l + 1);
    r = r && InnerTable_2_0_3(b, l + 1);
    r = r && consumeTokens(b, 0, STRINGVAR, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // JAVATYPEHEAD?
  private static boolean InnerTable_2_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InnerTable_2_0_3")) return false;
    consumeToken(b, JAVATYPEHEAD);
    return true;
  }

  /* ********************************************************** */
  // (STRING|NUMBER|switchInst|KEYWORDS|PRIMITIVETYPE|MODIFIER|EXTENDS|IMPLEMENTS|CLASS|THROW|THROWS|VOID|THIS|LEVEL|COMMA|DOT|SEMICOLON|COLON|AT|R_ARROW|L_ARROW|"("|")"|"["|"]"|COMPARATORS|"/"|COMPARATORS|OPERATORS|CONSTMETHODNAMES|EQ|UEQ|STRINGVAR|PLUS|MINUS)*
  public static boolean Instr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr")) return false;
    Marker m = enter_section_(b, l, _NONE_, INSTR, "<instr>");
    while (true) {
      int c = current_position_(b);
      if (!Instr_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Instr", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  // STRING|NUMBER|switchInst|KEYWORDS|PRIMITIVETYPE|MODIFIER|EXTENDS|IMPLEMENTS|CLASS|THROW|THROWS|VOID|THIS|LEVEL|COMMA|DOT|SEMICOLON|COLON|AT|R_ARROW|L_ARROW|"("|")"|"["|"]"|COMPARATORS|"/"|COMPARATORS|OPERATORS|CONSTMETHODNAMES|EQ|UEQ|STRINGVAR|PLUS|MINUS
  private static boolean Instr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Instr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = switchInst(b, l + 1);
    if (!r) r = consumeToken(b, KEYWORDS);
    if (!r) r = consumeToken(b, PRIMITIVETYPE);
    if (!r) r = consumeToken(b, MODIFIER);
    if (!r) r = consumeToken(b, EXTENDS);
    if (!r) r = consumeToken(b, IMPLEMENTS);
    if (!r) r = consumeToken(b, CLASS);
    if (!r) r = consumeToken(b, THROW);
    if (!r) r = consumeToken(b, THROWS);
    if (!r) r = consumeToken(b, VOID);
    if (!r) r = consumeToken(b, THIS);
    if (!r) r = consumeToken(b, LEVEL);
    if (!r) r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, DOT);
    if (!r) r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, COLON);
    if (!r) r = consumeToken(b, AT);
    if (!r) r = consumeToken(b, R_ARROW);
    if (!r) r = consumeToken(b, L_ARROW);
    if (!r) r = consumeToken(b, "(");
    if (!r) r = consumeToken(b, ")");
    if (!r) r = consumeToken(b, "[");
    if (!r) r = consumeToken(b, "]");
    if (!r) r = consumeToken(b, COMPARATORS);
    if (!r) r = consumeToken(b, SLASH);
    if (!r) r = consumeToken(b, COMPARATORS);
    if (!r) r = consumeToken(b, OPERATORS);
    if (!r) r = consumeToken(b, CONSTMETHODNAMES);
    if (!r) r = consumeToken(b, EQ);
    if (!r) r = consumeToken(b, UEQ);
    if (!r) r = consumeToken(b, STRINGVAR);
    if (!r) r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // NUMBER COLON Instr
  static boolean InstructionBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InstructionBody")) return false;
    if (!nextTokenIs(b, NUMBER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NUMBER, COLON);
    r = r && Instr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (STRINGVAR DOT)*STRINGVAR
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

  // (STRINGVAR DOT)*
  private static boolean JType_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JType_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!JType_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "JType_0", c)) break;
    }
    return true;
  }

  // STRINGVAR DOT
  private static boolean JType_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JType_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, STRINGVAR, DOT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (STRINGVAR DOT)*STRINGVAR lambdaParams STRINGVAR COLON* NUMBER STRINGVAR
  public static boolean LambdaType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LambdaType")) return false;
    if (!nextTokenIs(b, STRINGVAR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = LambdaType_0(b, l + 1);
    r = r && consumeToken(b, STRINGVAR);
    r = r && lambdaParams(b, l + 1);
    r = r && consumeToken(b, STRINGVAR);
    r = r && LambdaType_4(b, l + 1);
    r = r && consumeTokens(b, 0, NUMBER, STRINGVAR);
    exit_section_(b, m, LAMBDA_TYPE, r);
    return r;
  }

  // (STRINGVAR DOT)*
  private static boolean LambdaType_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LambdaType_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!LambdaType_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LambdaType_0", c)) break;
    }
    return true;
  }

  // STRINGVAR DOT
  private static boolean LambdaType_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LambdaType_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, STRINGVAR, DOT);
    exit_section_(b, m, null, r);
    return r;
  }

  // COLON*
  private static boolean LambdaType_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LambdaType_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COLON)) break;
      if (!empty_element_parsed_guard_(b, "LambdaType_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // METHODS LBRACKET MethodDeclaration* RBRACKET
  public static boolean MethodArea(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodArea")) return false;
    if (!nextTokenIs(b, METHODS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, METHODS, LBRACKET);
    r = r && MethodArea_2(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, METHOD_AREA, r);
    return r;
  }

  // MethodDeclaration*
  private static boolean MethodArea_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodArea_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!MethodDeclaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MethodArea_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // Annotation* MethodHead LBRACKET
  //          InstructionBody*
  //         RBRACKET
  public static boolean MethodDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, METHOD_DECLARATION, "<method declaration>");
    r = MethodDeclaration_0(b, l + 1);
    r = r && MethodHead(b, l + 1);
    r = r && consumeToken(b, LBRACKET);
    r = r && MethodDeclaration_3(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // Annotation*
  private static boolean MethodDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodDeclaration_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MethodDeclaration_0", c)) break;
    }
    return true;
  }

  // InstructionBody*
  private static boolean MethodDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodDeclaration_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!InstructionBody(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MethodDeclaration_3", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // ModifierV? Type MethodName
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

  // ModifierV?
  private static boolean MethodHead_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodHead_0")) return false;
    ModifierV(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // DefMethodName params exceptionList*
  public static boolean MethodName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodName")) return false;
    if (!nextTokenIs(b, STRINGVAR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = DefMethodName(b, l + 1);
    r = r && params(b, l + 1);
    r = r && MethodName_2(b, l + 1);
    exit_section_(b, m, METHOD_NAME, r);
    return r;
  }

  // exceptionList*
  private static boolean MethodName_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MethodName_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!exceptionList(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MethodName_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // MODIFIER*
  public static boolean ModifierV(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModifierV")) return false;
    Marker m = enter_section_(b, l, _NONE_, MODIFIER_V, "<modifier v>");
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, MODIFIER)) break;
      if (!empty_element_parsed_guard_(b, "ModifierV", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  /* ********************************************************** */
  // (LambdaType | JType | PRIMITIVETYPE)('[]')*
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

  // LambdaType | JType | PRIMITIVETYPE
  private static boolean Type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type_0")) return false;
    boolean r;
    r = LambdaType(b, l + 1);
    if (!r) r = JType(b, l + 1);
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
  // THROWS (JType COMMA?)*
  static boolean exceptionList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exceptionList")) return false;
    if (!nextTokenIs(b, THROWS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, THROWS);
    r = r && exceptionList_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (JType COMMA?)*
  private static boolean exceptionList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exceptionList_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!exceptionList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "exceptionList_1", c)) break;
    }
    return true;
  }

  // JType COMMA?
  private static boolean exceptionList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exceptionList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = JType(b, l + 1);
    r = r && exceptionList_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // COMMA?
  private static boolean exceptionList_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exceptionList_1_0_1")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // Annotation* ClassHead AttributesArea? FieldArea? MethodArea?
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = item__0(b, l + 1);
    r = r && ClassHead(b, l + 1);
    r = r && item__2(b, l + 1);
    r = r && item__3(b, l + 1);
    r = r && item__4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Annotation*
  private static boolean item__0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item__0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "item__0", c)) break;
    }
    return true;
  }

  // AttributesArea?
  private static boolean item__2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item__2")) return false;
    AttributesArea(b, l + 1);
    return true;
  }

  // FieldArea?
  private static boolean item__3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item__3")) return false;
    FieldArea(b, l + 1);
    return true;
  }

  // MethodArea?
  private static boolean item__4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item__4")) return false;
    MethodArea(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // item_
  static boolean jbcFile(PsiBuilder b, int l) {
    return item_(b, l + 1);
  }

  /* ********************************************************** */
  // LBRACKET( ("]")* STRINGVAR COLON*)*RBRACKET
  public static boolean lambdaParams(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaParams")) return false;
    if (!nextTokenIs(b, LBRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACKET);
    r = r && lambdaParams_1(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, LAMBDA_PARAMS, r);
    return r;
  }

  // ( ("]")* STRINGVAR COLON*)*
  private static boolean lambdaParams_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaParams_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!lambdaParams_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "lambdaParams_1", c)) break;
    }
    return true;
  }

  // ("]")* STRINGVAR COLON*
  private static boolean lambdaParams_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaParams_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = lambdaParams_1_0_0(b, l + 1);
    r = r && consumeToken(b, STRINGVAR);
    r = r && lambdaParams_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ("]")*
  private static boolean lambdaParams_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaParams_1_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!lambdaParams_1_0_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "lambdaParams_1_0_0", c)) break;
    }
    return true;
  }

  // ("]")
  private static boolean lambdaParams_1_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaParams_1_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "]");
    exit_section_(b, m, null, r);
    return r;
  }

  // COLON*
  private static boolean lambdaParams_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambdaParams_1_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COLON)) break;
      if (!empty_element_parsed_guard_(b, "lambdaParams_1_0_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (LBRACKET((Annotation? Type | LEVEL ) COMMA?)*RBRACKET)?
  public static boolean params(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params")) return false;
    Marker m = enter_section_(b, l, _NONE_, PARAMS, "<params>");
    params_0(b, l + 1);
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  // LBRACKET((Annotation? Type | LEVEL ) COMMA?)*RBRACKET
  private static boolean params_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACKET);
    r = r && params_0_1(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // ((Annotation? Type | LEVEL ) COMMA?)*
  private static boolean params_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!params_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "params_0_1", c)) break;
    }
    return true;
  }

  // (Annotation? Type | LEVEL ) COMMA?
  private static boolean params_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = params_0_1_0_0(b, l + 1);
    r = r && params_0_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Annotation? Type | LEVEL
  private static boolean params_0_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params_0_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = params_0_1_0_0_0(b, l + 1);
    if (!r) r = consumeToken(b, LEVEL);
    exit_section_(b, m, null, r);
    return r;
  }

  // Annotation? Type
  private static boolean params_0_1_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params_0_1_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = params_0_1_0_0_0_0(b, l + 1);
    r = r && Type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Annotation?
  private static boolean params_0_1_0_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params_0_1_0_0_0_0")) return false;
    Annotation(b, l + 1);
    return true;
  }

  // COMMA?
  private static boolean params_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params_0_1_0_1")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // "switch" LBRACKET LEVEL RBRACKET LBRACKET((NUMBER|"default") COLON "goto" NUMBER SEMICOLON?)* RBRACKET
  public static boolean switchInst(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switchInst")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SWITCH_INST, "<switch inst>");
    r = consumeToken(b, "switch");
    r = r && consumeTokens(b, 0, LBRACKET, LEVEL, RBRACKET, LBRACKET);
    r = r && switchInst_5(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((NUMBER|"default") COLON "goto" NUMBER SEMICOLON?)*
  private static boolean switchInst_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switchInst_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!switchInst_5_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "switchInst_5", c)) break;
    }
    return true;
  }

  // (NUMBER|"default") COLON "goto" NUMBER SEMICOLON?
  private static boolean switchInst_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switchInst_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = switchInst_5_0_0(b, l + 1);
    r = r && consumeToken(b, COLON);
    r = r && consumeToken(b, "goto");
    r = r && consumeToken(b, NUMBER);
    r = r && switchInst_5_0_4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NUMBER|"default"
  private static boolean switchInst_5_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switchInst_5_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, "default");
    exit_section_(b, m, null, r);
    return r;
  }

  // SEMICOLON?
  private static boolean switchInst_5_0_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switchInst_5_0_4")) return false;
    consumeToken(b, SEMICOLON);
    return true;
  }

}
