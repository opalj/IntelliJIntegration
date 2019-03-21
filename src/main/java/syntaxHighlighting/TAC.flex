package syntaxHighlighting;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import syntaxHighlighting.TAC_elementTypeHolder;

%%
%{
  public TACLexer() {
    this((java.io.Reader)null);
  }
%}

%class TACLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF=\R
WHITE_SPACE=\s+
STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
COMMENT="//".*
BLOCK_COMMENT="/"\*[^\*/]*\*"/"
NUMBER =(-)?[0-9]+
KEYWORDS=new|goto|if|else|throw|throws|catch|caught|return
MODIFIER=public|private|protected|default|static|final|abstract|synchronized|native|strictfp|volatile|transient
LEVEL=lv+([A-Za-z0-9])*
COMMA=,
DOT=.
COLON=:
SEMICOLON=;
PLUS=\+
MINUS=\-
AT=@
R_ARROW==>
L_ARROW=<=
L_LACE_BRACE=<
R_LACE_BRACE=>
L_DOUBLE_LACE_BRACE=\«
R_DOUBLE_LACE_BRACE=\»
R_BRACKET=\)
L_BRACKET=\(
L_SQUARE_BRACKET=\[
R_SQUARE_BRACKET=\]
L_CURVED_BRACKET=\{
R_CURVED_BRACKET=\}
CONSTMETHODNAMES = <(cl)?init>
CLASS=class
EXTENDS=extends
IMPLEMENTS=implements
NEW_LINE=\n
EQ==
UEQ=\!=
THIS=this
THROW=throw
THROWS=throws
VOID=void
//STRINGVAR = [A-Za-z0-9]+
JAVA_TYPE=((([A-Za-z])([A-Za-z0-9])*)\.)+(([A-Za-z])([A-Za-z0-9])*)
STRINGVAR = [a-zA-Z$_][a-zA-Z0-9$_]*

%state WAITING_VALUE

%%
<YYINITIAL> {
","                     { return TAC_elementTypeHolder.COMMA; }
";"                     { return TAC_elementTypeHolder.SEMICOLON; }
"."                     { return TAC_elementTypeHolder.DOT; }
":"                     { return TAC_elementTypeHolder.COLON; }
"@"                     { return TAC_elementTypeHolder.AT; }
"class"                 { return TAC_elementTypeHolder.CLASS; }
"extends"               { return TAC_elementTypeHolder.EXTENDS; }
"implements"            { return TAC_elementTypeHolder.IMPLEMENTS; }
"\n"                   { return TAC_elementTypeHolder.NEW_LINE; }
"this"                  { return TAC_elementTypeHolder.THIS; }
"throw"                  { return TAC_elementTypeHolder.THROW; }
"throws"                  { return TAC_elementTypeHolder.THROWS; }
"+"                  { return TAC_elementTypeHolder.PLUS; }
"-"                  { return TAC_elementTypeHolder.MINUS; }
{L_DOUBLE_LACE_BRACE}            { return TAC_elementTypeHolder.L_DOUBLE_LACE_BRACE; }
{R_DOUBLE_LACE_BRACE}            { return TAC_elementTypeHolder.R_DOUBLE_LACE_BRACE; }
{L_BRACKET}            { return TAC_elementTypeHolder.L_BRACKET; }
{R_BRACKET}            { return TAC_elementTypeHolder.R_BRACKET; }
{L_SQUARE_BRACKET}     { return TAC_elementTypeHolder.L_SQUARE_BRACKET; }
{R_SQUARE_BRACKET}    { return TAC_elementTypeHolder.R_SQUARE_BRACKET; }
{L_CURVED_BRACKET}   { return TAC_elementTypeHolder.L_CURVED_BRACKET; }
{R_CURVED_BRACKET}    { return TAC_elementTypeHolder.R_CURVED_BRACKET; }
{L_LACE_BRACE}   { return TAC_elementTypeHolder.L_LACE_BRACE; }
{R_LACE_BRACE}    { return TAC_elementTypeHolder.R_LACE_BRACE; }
{KEYWORDS}            { return TAC_elementTypeHolder.KEYWORDS; }
{COMMENT}             { return TAC_elementTypeHolder.COMMENT; }
{STRING}              { return TAC_elementTypeHolder.STRING; }
{LEVEL}               { return TAC_elementTypeHolder.LEVEL; }
{BLOCK_COMMENT}       { return TAC_elementTypeHolder.BLOCK_COMMENT; }
{MODIFIER}           { return TAC_elementTypeHolder.MODIFIER; }
{EQ}                 { return TAC_elementTypeHolder.EQ; }
{UEQ}                 { return TAC_elementTypeHolder.UEQ; }
{NUMBER}              { return TAC_elementTypeHolder.NUMBER; }
{JAVA_TYPE}            { return TAC_elementTypeHolder.JAVA_TYPE; }
{STRINGVAR}           { return TAC_elementTypeHolder.STRINGVAR; }
{R_ARROW}            { return TAC_elementTypeHolder.R_ARROW; }
{L_ARROW}            { return TAC_elementTypeHolder.L_ARROW; }
}

//<YYINITIAL> {COMMENT}                                       { yybegin(YYINITIAL); return TAC_elementTypeHolder.COMMENT; }

//<YYINITIAL> {KEYWORDS}                                     { yybegin(YYINITIAL); return TAC_elementTypeHolder.KEYWORDS; }

//<YYINITIAL> {SEPARATOR}                                     { yybegin(WAITING_VALUE); return TAC_elementTypeHolder.SEPARATOR; }

//<YYINITIAL> {STRING}                                        { yybegin(YYINITIAL); return TAC_elementTypeHolder.STRING; }

//<YYINITIAL> {NUMBER}                                        { yybegin(YYINITIAL); return TAC_elementTypeHolder.NUMBER; }

<WAITING_VALUE> {CRLF}({CRLF}|{WHITE_SPACE})+               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }

({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }



