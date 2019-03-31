package JavaByteCodeLanguage.syntaxhighlighter;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

import JavaByteCodeLanguage.autoGenerated.Lexer.JavaByteCodeLexerAdapter;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeTypes;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class JbcSyntaxHighlighter extends SyntaxHighlighterBase {

  private static final Map<IElementType, TextAttributesKey> ourMap;
  // Java keywords
  private static final TextAttributesKey MODIFIER =
      createTextAttributesKey("JBC_MODIFIER", DefaultLanguageHighlighterColors.KEYWORD);
  static final TextAttributesKey TYPE =
      createTextAttributesKey("JBC_TYPE", DefaultLanguageHighlighterColors.KEYWORD);
  private static final TextAttributesKey JAVATYPEHEAD =
      createTextAttributesKey("JBC_JAVATYPEHEAD", DefaultLanguageHighlighterColors.KEYWORD);
  private static final TextAttributesKey EXTENDS =
      createTextAttributesKey("JBC_EXTENDS", DefaultLanguageHighlighterColors.KEYWORD);
  private static final TextAttributesKey IMPLEMENTS =
      createTextAttributesKey("JBC_IMPLEMENTS", DefaultLanguageHighlighterColors.KEYWORD);
  private static final TextAttributesKey THROWS =
      createTextAttributesKey("JBC_THROWS", DefaultLanguageHighlighterColors.KEYWORD);
  private static final TextAttributesKey CASE =
      createTextAttributesKey("JBC_CASE", DefaultLanguageHighlighterColors.KEYWORD);
  private static final TextAttributesKey BOOLS =
      createTextAttributesKey("JBC_BOOLS", DefaultLanguageHighlighterColors.KEYWORD);

  // comments (includes block comments)
  public static final TextAttributesKey COMMENT =
      createTextAttributesKey("JBC_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);

  // misc
  public static final TextAttributesKey NUMBER =
      createTextAttributesKey("JBC_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  private static final TextAttributesKey BAD_CHARACTER =
      createTextAttributesKey("JBC_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
  public static final TextAttributesKey STRING =
      createTextAttributesKey("JBC_STRING", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey MNEMONIC =
      createTextAttributesKey("JBC_MNEMONIC", DefaultLanguageHighlighterColors.CONSTANT);

  static final TextAttributesKey ANNOTATION =
      createTextAttributesKey("JBC_ANNOTATION", DefaultLanguageHighlighterColors.METADATA);

  static {
    ourMap = new HashMap<>();
    // TODO: doesn't work for annotations, because they're not TokenTypes...
    TokenSet annotationSet = TokenSet.create(JavaByteCodeTypes.ANNOTATION);
    fillMap(ourMap, annotationSet, DefaultLanguageHighlighterColors.METADATA);

    TokenSet keywords =
        TokenSet.create(
            JavaByteCodeTypes.PRIMITIVETYPE,
            JavaByteCodeTypes.MODIFIER,
            JavaByteCodeTypes.JAVATYPEHEAD,
            JavaByteCodeTypes.EXTENDS,
            JavaByteCodeTypes.IMPLEMENTS,
            JavaByteCodeTypes.THROWS,
            JavaByteCodeTypes.CASE,
            JavaByteCodeTypes.BOOLS);
    fillMap(ourMap, keywords, DefaultLanguageHighlighterColors.KEYWORD);

    ourMap.put(JavaByteCodeTypes.COMMENT, DefaultLanguageHighlighterColors.LINE_COMMENT);
    ourMap.put(JavaByteCodeTypes.MNEMONIC, DefaultLanguageHighlighterColors.CONSTANT);
    ourMap.put(JavaByteCodeTypes.NUMBER, DefaultLanguageHighlighterColors.NUMBER);
    ourMap.put(JavaByteCodeTypes.STRING, DefaultLanguageHighlighterColors.STRING);
    ourMap.put(TokenType.BAD_CHARACTER, HighlighterColors.BAD_CHARACTER);
  }

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new JavaByteCodeLexerAdapter();
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    return pack(ourMap.get(tokenType));
  }
}
