package syntaxHighlighting;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class TAC_syntaxHighlighter extends SyntaxHighlighterBase {
  // Java keywords
  public static final TextAttributesKey MODIFIER =
          createTextAttributesKey("TAC_MODIFIER", DefaultLanguageHighlighterColors.KEYWORD);
  static final TextAttributesKey TYPE =
          createTextAttributesKey("TAC_TYPE", DefaultLanguageHighlighterColors.KEYWORD);
  private static final TextAttributesKey JAVATYPEHEAD =
          createTextAttributesKey("TAC_JAVATYPEHEAD", DefaultLanguageHighlighterColors.KEYWORD);
  private static final TextAttributesKey EXTENDS =
          createTextAttributesKey("TAC_EXTENDS", DefaultLanguageHighlighterColors.KEYWORD);
  private static final TextAttributesKey IMPLEMENTS =
          createTextAttributesKey("TAC_IMPLEMENTS", DefaultLanguageHighlighterColors.KEYWORD);
  private static final TextAttributesKey THROWS =
          createTextAttributesKey("TAC_THROWS", DefaultLanguageHighlighterColors.KEYWORD);

  // comments (includes block comments)
  public static final TextAttributesKey COMMENT =
          createTextAttributesKey("TAC_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);

  // misc
  public static final TextAttributesKey NUMBER =
          createTextAttributesKey("TAC_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  private static final TextAttributesKey BAD_CHARACTER =
          createTextAttributesKey("TAC_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
  public static final TextAttributesKey STRING =
          createTextAttributesKey("TAC_STRING", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey LEVEL =
          createTextAttributesKey("TAC_LEVELC", DefaultLanguageHighlighterColors.CONSTANT);

  static final TextAttributesKey ANNOTATION =
          createTextAttributesKey("TAC_ANNOTATION", DefaultLanguageHighlighterColors.METADATA);

  private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[] {BAD_CHARACTER};
  private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[] {NUMBER};
  private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[] {COMMENT};
  private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[] {STRING};
  private static final TextAttributesKey[] LEVEL_KEYS = new TextAttributesKey[] {LEVEL};
  private static final TextAttributesKey[] JAVA_KEYWORD_KEYS =
          new TextAttributesKey[] {TYPE, MODIFIER, JAVATYPEHEAD, EXTENDS, IMPLEMENTS, THROWS};
  private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
  private static final TextAttributesKey[] ANNOTATION_KEYS = new TextAttributesKey[] { ANNOTATION };

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new TAC_lexerAdapter();
  }
  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    if (tokenType.equals(syntaxHighlighting.TAC_elementTypeHolder.COMMENT)) {
      return COMMENT_KEYS;
    } else if(tokenType.equals(TAC_elementTypeHolder.ANNOTATION)) {
      // TODO: only works for tokens
      return ANNOTATION_KEYS;
    } else if (tokenType.equals(TAC_elementTypeHolder.LEVEL)) {
      return LEVEL_KEYS;
    } else if (tokenType.equals(TAC_elementTypeHolder.NUMBER)) {
      return NUMBER_KEYS;
    } else if (tokenType.equals(TAC_elementTypeHolder.STRING)) {
      return STRING_KEYS;
    } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
      return BAD_CHAR_KEYS;
    } else if (tokenType.equals(TAC_elementTypeHolder.PRIMITIVETYPE)
            || tokenType.equals(TAC_elementTypeHolder.MODIFIER)
            || tokenType.equals(TAC_elementTypeHolder.JAVATYPEHEAD)
            || tokenType.equals(TAC_elementTypeHolder.EXTENDS)
            || tokenType.equals(TAC_elementTypeHolder.IMPLEMENTS)
            || tokenType.equals(TAC_elementTypeHolder.THROWS)) {
      return JAVA_KEYWORD_KEYS;
    } else {
      return EMPTY_KEYS;
    }
  }
}
