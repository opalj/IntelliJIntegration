// This is a generated file. Not intended for manual editing.
package syntaxHighlighting;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static syntaxHighlighting.TAC_elementTypeHolder.*;
import static syntaxHighlighting.TAC_parserParserUtil.*;
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
    if (t == J_METHOD_HEAD) {
      r = JMethodHead(b, 0);
    }
    else if (t == J_METHOD_NAME) {
      r = JMethodName(b, 0);
    }
    else if (t == J_MODIFIER) {
      r = JModifier(b, 0);
    }
    else if (t == J_RETURN_VALUE) {
      r = JReturnValue(b, 0);
    }
    else if (t == J_TYPE) {
      r = JType(b, 0);
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
    return tacFile(b, l + 1);
  }

  /* ********************************************************** */
  // JModifier BLOCK_COMMENT* JReturnValue BLOCK_COMMENT* JMethodName BLOCK_COMMENT* L_BRACKET (STRINGVAR | BLOCK_COMMENT | COMMA | JAVA_TYPE)* R_BRACKET BLOCK_COMMENT* (L_DOUBLE_LACE_BRACE (STRINGVAR | COMMA | BLOCK_COMMENT | JAVA_TYPE)* R_DOUBLE_LACE_BRACE)? BLOCK_COMMENT* COMMENT?
  public static boolean JMethodHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, J_METHOD_HEAD, "<j method head>");
    r = JModifier(b, l + 1);
    r = r && JMethodHead_1(b, l + 1);
    r = r && JReturnValue(b, l + 1);
    r = r && JMethodHead_3(b, l + 1);
    r = r && JMethodName(b, l + 1);
    r = r && JMethodHead_5(b, l + 1);
    r = r && consumeToken(b, L_BRACKET);
    r = r && JMethodHead_7(b, l + 1);
    r = r && consumeToken(b, R_BRACKET);
    r = r && JMethodHead_9(b, l + 1);
    r = r && JMethodHead_10(b, l + 1);
    r = r && JMethodHead_11(b, l + 1);
    r = r && JMethodHead_12(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // BLOCK_COMMENT*
  private static boolean JMethodHead_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, BLOCK_COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "JMethodHead_1", c)) break;
    }
    return true;
  }

  // BLOCK_COMMENT*
  private static boolean JMethodHead_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, BLOCK_COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "JMethodHead_3", c)) break;
    }
    return true;
  }

  // BLOCK_COMMENT*
  private static boolean JMethodHead_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, BLOCK_COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "JMethodHead_5", c)) break;
    }
    return true;
  }

  // (STRINGVAR | BLOCK_COMMENT | COMMA | JAVA_TYPE)*
  private static boolean JMethodHead_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead_7")) return false;
    while (true) {
      int c = current_position_(b);
      if (!JMethodHead_7_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "JMethodHead_7", c)) break;
    }
    return true;
  }

  // STRINGVAR | BLOCK_COMMENT | COMMA | JAVA_TYPE
  private static boolean JMethodHead_7_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead_7_0")) return false;
    boolean r;
    r = consumeToken(b, STRINGVAR);
    if (!r) r = consumeToken(b, BLOCK_COMMENT);
    if (!r) r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, JAVA_TYPE);
    return r;
  }

  // BLOCK_COMMENT*
  private static boolean JMethodHead_9(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead_9")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, BLOCK_COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "JMethodHead_9", c)) break;
    }
    return true;
  }

  // (L_DOUBLE_LACE_BRACE (STRINGVAR | COMMA | BLOCK_COMMENT | JAVA_TYPE)* R_DOUBLE_LACE_BRACE)?
  private static boolean JMethodHead_10(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead_10")) return false;
    JMethodHead_10_0(b, l + 1);
    return true;
  }

  // L_DOUBLE_LACE_BRACE (STRINGVAR | COMMA | BLOCK_COMMENT | JAVA_TYPE)* R_DOUBLE_LACE_BRACE
  private static boolean JMethodHead_10_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead_10_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, L_DOUBLE_LACE_BRACE);
    r = r && JMethodHead_10_0_1(b, l + 1);
    r = r && consumeToken(b, R_DOUBLE_LACE_BRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // (STRINGVAR | COMMA | BLOCK_COMMENT | JAVA_TYPE)*
  private static boolean JMethodHead_10_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead_10_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!JMethodHead_10_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "JMethodHead_10_0_1", c)) break;
    }
    return true;
  }

  // STRINGVAR | COMMA | BLOCK_COMMENT | JAVA_TYPE
  private static boolean JMethodHead_10_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead_10_0_1_0")) return false;
    boolean r;
    r = consumeToken(b, STRINGVAR);
    if (!r) r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, BLOCK_COMMENT);
    if (!r) r = consumeToken(b, JAVA_TYPE);
    return r;
  }

  // BLOCK_COMMENT*
  private static boolean JMethodHead_11(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead_11")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, BLOCK_COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "JMethodHead_11", c)) break;
    }
    return true;
  }

  // COMMENT?
  private static boolean JMethodHead_12(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodHead_12")) return false;
    consumeToken(b, COMMENT);
    return true;
  }

  /* ********************************************************** */
  // (L_LACE_BRACE STRINGVAR R_LACE_BRACE) | Type
  public static boolean JMethodName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodName")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, J_METHOD_NAME, "<j method name>");
    r = JMethodName_0(b, l + 1);
    if (!r) r = Type(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // L_LACE_BRACE STRINGVAR R_LACE_BRACE
  private static boolean JMethodName_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JMethodName_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, L_LACE_BRACE, STRINGVAR, R_LACE_BRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MODIFIER*
  public static boolean JModifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JModifier")) return false;
    Marker m = enter_section_(b, l, _NONE_, J_MODIFIER, "<j modifier>");
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, MODIFIER)) break;
      if (!empty_element_parsed_guard_(b, "JModifier", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  /* ********************************************************** */
  // VOID | JType | KEYWORDS | STRINGVAR
  public static boolean JReturnValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JReturnValue")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, J_RETURN_VALUE, "<j return value>");
    r = consumeToken(b, VOID);
    if (!r) r = JType(b, l + 1);
    if (!r) r = consumeToken(b, KEYWORDS);
    if (!r) r = consumeToken(b, STRINGVAR);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // JAVA_TYPE
  public static boolean JType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JType")) return false;
    if (!nextTokenIs(b, JAVA_TYPE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, JAVA_TYPE);
    exit_section_(b, m, J_TYPE, r);
    return r;
  }

  /* ********************************************************** */
  // (JType | STRINGVAR | NUMBER)('[]')*
  public static boolean Type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE, "<type>");
    r = Type_0(b, l + 1);
    r = r && Type_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // JType | STRINGVAR | NUMBER
  private static boolean Type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type_0")) return false;
    boolean r;
    r = JType(b, l + 1);
    if (!r) r = consumeToken(b, STRINGVAR);
    if (!r) r = consumeToken(b, NUMBER);
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
  // JMethodHead|Type|COMMENT|CRLF|WHITE_SPACE|STRING|NUMBER|KEYWORDS|MODIFIER | BLOCK_COMMENT|EXTENDS|IMPLEMENTS|CLASS|THROW|THROWS|VOID|THIS|LEVEL|COMMA|DOT|SEMICOLON|COLON|AT|R_ARROW|L_ARROW|L_LACE_BRACE|R_LACE_BRACE|R_BRACKET|L_BRACKET|L_SQUARE_BRACKET|R_SQUARE_BRACKET|L_CURVED_BRACKET|R_CURVED_BRACKET|CONSTMETHODNAMES|NEW_LINE|EQ|UEQ|STRINGVAR|L_DOUBLE_LACE_BRACE|R_DOUBLE_LACE_BRACE|PLUS|MINUS
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = JMethodHead(b, l + 1);
    if (!r) r = Type(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CRLF);
    if (!r) r = consumeToken(b, WHITE_SPACE);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, KEYWORDS);
    if (!r) r = consumeToken(b, MODIFIER);
    if (!r) r = consumeToken(b, BLOCK_COMMENT);
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
    if (!r) r = consumeToken(b, L_LACE_BRACE);
    if (!r) r = consumeToken(b, R_LACE_BRACE);
    if (!r) r = consumeToken(b, R_BRACKET);
    if (!r) r = consumeToken(b, L_BRACKET);
    if (!r) r = consumeToken(b, L_SQUARE_BRACKET);
    if (!r) r = consumeToken(b, R_SQUARE_BRACKET);
    if (!r) r = consumeToken(b, L_CURVED_BRACKET);
    if (!r) r = consumeToken(b, R_CURVED_BRACKET);
    if (!r) r = consumeToken(b, CONSTMETHODNAMES);
    if (!r) r = consumeToken(b, NEW_LINE);
    if (!r) r = consumeToken(b, EQ);
    if (!r) r = consumeToken(b, UEQ);
    if (!r) r = consumeToken(b, STRINGVAR);
    if (!r) r = consumeToken(b, L_DOUBLE_LACE_BRACE);
    if (!r) r = consumeToken(b, R_DOUBLE_LACE_BRACE);
    if (!r) r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean tacFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tacFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tacFile", c)) break;
    }
    return true;
  }

}
