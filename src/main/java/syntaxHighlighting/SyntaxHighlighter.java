package syntaxHighlighting;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class SyntaxHighlighter extends SyntaxHighlighterBase {
  public static final TextAttributesKey SEPARATOR =
      createTextAttributesKey("TAC_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
  public static final TextAttributesKey CRLF =
      createTextAttributesKey("TAC_CRLF", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey WHITE_SPACE =
      createTextAttributesKey("TAC_WHITE_SPACE", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey COMMENT =
      createTextAttributesKey("TAC_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
  public static final TextAttributesKey NUMBER =
      createTextAttributesKey("TAC_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  public static final TextAttributesKey KEYWORDS =
      createTextAttributesKey("TAC_KEYWORDS", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey STRING =
      createTextAttributesKey("TAC_STRING", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey LEVEL =
          createTextAttributesKey("TAC_LEVEL", DefaultLanguageHighlighterColors.CONSTANT);
  public static final TextAttributesKey BAD_CHARACTER =
      createTextAttributesKey("TAC_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
  public static final TextAttributesKey JAVATYPE =
          createTextAttributesKey("TAC_JAVATYPE", DefaultLanguageHighlighterColors.CLASS_NAME);

  private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[] {BAD_CHARACTER};
  private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[] {COMMENT};
  private static final TextAttributesKey[] CRLF_KEYS = new TextAttributesKey[] {CRLF};
  private static final TextAttributesKey[] WHITE_SPACE_KEYS = new TextAttributesKey[] {WHITE_SPACE};
  private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[] {NUMBER};
  private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[] {STRING};
  private static final TextAttributesKey[] KEYWORDS_KEYS = new TextAttributesKey[] {KEYWORDS};
  private static final TextAttributesKey[] JAVATYPE_KEYS = new TextAttributesKey[] {JAVATYPE};
  private static final TextAttributesKey[] LEVEL_KEYS = new TextAttributesKey[] {LEVEL};
  private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new TAC_adapter();
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    if (tokenType.equals(TAC_elementTypeHolder.COMMENT)) {
      return COMMENT_KEYS;
    } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
      return BAD_CHAR_KEYS;
    } else if (tokenType.equals(TAC_elementTypeHolder.CRLF)) {
      return CRLF_KEYS;
    } else if (tokenType.equals(TAC_elementTypeHolder.WHITE_SPACE)) {
      return WHITE_SPACE_KEYS;
    } else if (tokenType.equals(TAC_elementTypeHolder.NUMBER)) {
      return NUMBER_KEYS;
    } else if (tokenType.equals(TAC_elementTypeHolder.STRING)) {
      return STRING_KEYS;
    } else if (tokenType.equals(TAC_elementTypeHolder.KEYWORDS)) {
      return KEYWORDS_KEYS;
    } else /*if (tokenType.equals(TAC_elementTypeHolder.JAVATYPE)) {
      return JAVATYPE_KEYS;
    }  else*/ if (tokenType.equals(TAC_elementTypeHolder.LEVEL)) {
        return LEVEL_KEYS;
    } else {
      return EMPTY_KEYS;
    }
  }
}
