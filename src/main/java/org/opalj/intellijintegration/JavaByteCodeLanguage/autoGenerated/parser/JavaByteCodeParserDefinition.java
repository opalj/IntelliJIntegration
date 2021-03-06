/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.opalj.intellijintegration.JavaByteCodeFile;
import org.opalj.intellijintegration.JavaByteCodeLanguage.LanguageAndFileType.JavaByteCode;
import org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.Lexer.JavaByteCodeLexerAdapter;
import org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeTypes;

public class JavaByteCodeParserDefinition implements ParserDefinition {

  public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
  public static final TokenSet COMMENTS = TokenSet.create(JavaByteCodeTypes.COMMENT);

  public static final IFileElementType FILE = new IFileElementType(JavaByteCode.INSTANCE);

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return new JavaByteCodeLexerAdapter();
  }

  @NotNull
  public TokenSet getWhitespaceTokens() {
    return WHITE_SPACES;
  }

  @NotNull
  public TokenSet getCommentTokens() {
    return COMMENTS;
  }

  @NotNull
  public TokenSet getStringLiteralElements() {
    return TokenSet.EMPTY;
  }

  @NotNull
  public PsiParser createParser(final Project project) {
    return new JavaByteCodeParser();
  }

  @Override
  public IFileElementType getFileNodeType() {
    return FILE;
  }

  public PsiFile createFile(FileViewProvider viewProvider) {
    return new JavaByteCodeFile(viewProvider);
  }

  public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
    return SpaceRequirements.MAY;
  }

  @NotNull
  public PsiElement createElement(ASTNode node) {
    return JavaByteCodeTypes.Factory.createElement(node);
  }
}
