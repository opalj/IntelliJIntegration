package JavaByteCodeLanguage.syntaxhighlighter;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

import JavaByteCodeLanguage.Lexer.JavaByteCodeLexerAdapter;
import JavaByteCodeLanguage.psi.JavaByteCodeTypes;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class JavaByteCodeSyntaxHighlighter extends SyntaxHighlighterBase {
  public static final TextAttributesKey TYPE =
      createTextAttributesKey("JBC_TYPE", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey NUMBER =
      createTextAttributesKey("JBC_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  public static final TextAttributesKey COMMENT =
      createTextAttributesKey("JBC_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
  public static final TextAttributesKey BAD_CHARACTER =
      createTextAttributesKey("JBC_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
  public static final TextAttributesKey MNEMONIC =
      createTextAttributesKey("JBC_INSTRUCTION", DefaultLanguageHighlighterColors.IDENTIFIER);
  public static final TextAttributesKey STRING =
      createTextAttributesKey("JBC_STRING", DefaultLanguageHighlighterColors.STRING);

  private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[] {BAD_CHARACTER};
  private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[] {NUMBER};
  private static final TextAttributesKey[] TYPE_KEYS = new TextAttributesKey[] {TYPE};
  private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[] {COMMENT};
  private static final TextAttributesKey[] MNEMONIC_KEYS = new TextAttributesKey[] {MNEMONIC};
  private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[] {STRING};
  private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new JavaByteCodeLexerAdapter();
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    if (tokenType.equals(JavaByteCodeTypes.COMMENT)) {
      return COMMENT_KEYS;
    } else if (tokenType.equals(JavaByteCodeTypes.J_TYPE)
        || tokenType.equals(
            JavaByteCodeTypes.PRIMITIVETYPE)) { // tokenType.equals(JavaByteCodeTypes.JAVATYPE)
      return TYPE_KEYS;
    } else if (tokenType.equals(JavaByteCodeTypes.NUMBER)) {
      return NUMBER_KEYS;
    } else if (tokenType.equals(JavaByteCodeTypes.MNEMONIC)) {
      return MNEMONIC_KEYS;
    } else if (tokenType.equals(JavaByteCodeTypes.STRING)) {
      return STRING_KEYS;
    } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
      return BAD_CHAR_KEYS;
    } else {
      return EMPTY_KEYS;
    }
  }
}
