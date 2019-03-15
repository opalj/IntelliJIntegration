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
    if (t == J_TYPE) {
      r = JType(b, 0);
    }
    else if (t == TYPE) {
      r = Type(b, 0);
    }
    else if (t == PROPERTY) {
      r = property(b, 0);
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
  // ((STRINGVAR | THIS) DOT)*(STRINGVAR | THIS)
  public static boolean JType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JType")) return false;
    if (!nextTokenIs(b, "<j type>", STRINGVAR, THIS)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, J_TYPE, "<j type>");
    r = JType_0(b, l + 1);
    r = r && JType_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((STRINGVAR | THIS) DOT)*
  private static boolean JType_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JType_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!JType_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "JType_0", c)) break;
    }
    return true;
  }

  // (STRINGVAR | THIS) DOT
  private static boolean JType_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JType_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = JType_0_0_0(b, l + 1);
    r = r && consumeToken(b, DOT);
    exit_section_(b, m, null, r);
    return r;
  }

  // STRINGVAR | THIS
  private static boolean JType_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JType_0_0_0")) return false;
    boolean r;
    r = consumeToken(b, STRINGVAR);
    if (!r) r = consumeToken(b, THIS);
    return r;
  }

  // STRINGVAR | THIS
  private static boolean JType_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JType_1")) return false;
    boolean r;
    r = consumeToken(b, STRINGVAR);
    if (!r) r = consumeToken(b, THIS);
    return r;
  }

  /* ********************************************************** */
  // (JType | NUMBER)('[]')*
  public static boolean Type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE, "<type>");
    r = Type_0(b, l + 1);
    r = r && Type_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // JType | NUMBER
  private static boolean Type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type_0")) return false;
    boolean r;
    r = JType(b, l + 1);
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
  // Type|COMMENT|CRLF|WHITE_SPACE|STRING|NUMBER|KEYWORDS
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = Type(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CRLF);
    if (!r) r = consumeToken(b, WHITE_SPACE);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, KEYWORDS);
    return r;
  }

  /* ********************************************************** */
  // (KEY? SEPARATOR VALUE?) | KEY
  public static boolean property(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property")) return false;
    if (!nextTokenIs(b, "<property>", KEY, SEPARATOR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PROPERTY, "<property>");
    r = property_0(b, l + 1);
    if (!r) r = consumeToken(b, KEY);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // KEY? SEPARATOR VALUE?
  private static boolean property_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = property_0_0(b, l + 1);
    r = r && consumeToken(b, SEPARATOR);
    r = r && property_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // KEY?
  private static boolean property_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_0_0")) return false;
    consumeToken(b, KEY);
    return true;
  }

  // VALUE?
  private static boolean property_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_0_2")) return false;
    consumeToken(b, VALUE);
    return true;
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
