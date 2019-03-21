package syntaxHighlighting;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static syntaxHighlighting.TAC_elementTypeHolder.*;

%%

%{
  public _TAC_parserLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _TAC_parserLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

CRLF=\R
WHITE_SPACE=[ \t\n\x0B\f\r]+
STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
COMMENT="//".*
BLOCK_COMMENT="/"\*[^\*/]*\*"/"
NUMBER=(-)?[0-9]+
KEYWORDS=new|goto|if|else|throw|throws|catch|caught|return
MODIFIER=public|private|protected|default|static|final|abstract|synchronized|native|strictfp|volatile|transient
LEVEL=lv+([A-Za-z0-9])*
CONSTMETHODNAMES=<(cl)?init>
NEW_LINE=\n
JAVA_TYPE=((([A-Za-z])([A-Za-z0-9])*)\.)+(([A-Za-z])([A-Za-z0-9])*)
STRINGVAR=[a-zA-Z$_][a-zA-Z0-9$_]*

%%
<YYINITIAL> {
  {WHITE_SPACE}           { return WHITE_SPACE; }

  "class"                 { return CLASS; }
  "extends"               { return EXTENDS; }
  "implemenmts"           { return IMPLEMENTS; }
  "throw"                 { return THROW; }
  "throws"                { return THROWS; }
  "void"                  { return VOID; }
  "this"                  { return THIS; }
  ","                     { return COMMA; }
  "."                     { return DOT; }
  ":"                     { return COLON; }
  ";"                     { return SEMICOLON; }
  "@"                     { return AT; }
  "=>"                    { return R_ARROW; }
  "<="                    { return L_ARROW; }
  "«"                     { return L_DOUBLE_LACE_BRACE; }
  "»"                     { return R_DOUBLE_LACE_BRACE; }
  "<"                     { return L_LACE_BRACE; }
  ">"                     { return R_LACE_BRACE; }
  ")"                     { return R_BRACKET; }
  "("                     { return L_BRACKET; }
  "["                     { return L_SQUARE_BRACKET; }
  "]"                     { return R_SQUARE_BRACKET; }
  "{"                     { return L_CURVED_BRACKET; }
  "}"                     { return R_CURVED_BRACKET; }
  "+"                     { return PLUS; }
  "-"                     { return MINUS; }
  "="                     { return EQ; }
  "!="                    { return UEQ; }

  {CRLF}                  { return CRLF; }
  {WHITE_SPACE}           { return WHITE_SPACE; }
  {STRING}                { return STRING; }
  {COMMENT}               { return COMMENT; }
  {BLOCK_COMMENT}         { return BLOCK_COMMENT; }
  {NUMBER}                { return NUMBER; }
  {KEYWORDS}              { return KEYWORDS; }
  {MODIFIER}              { return MODIFIER; }
  {LEVEL}                 { return LEVEL; }
  {CONSTMETHODNAMES}      { return CONSTMETHODNAMES; }
  {NEW_LINE}              { return NEW_LINE; }
  {JAVA_TYPE}             { return JAVA_TYPE; }
  {STRINGVAR}             { return STRINGVAR; }

}

[^] { return BAD_CHARACTER; }
