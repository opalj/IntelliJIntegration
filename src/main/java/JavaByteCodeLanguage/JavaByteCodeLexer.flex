package JavaByteCodeLanguage;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static JavaByteCodeLanguage.psi.JavaByteCodeTypes.*;

%%

%{
  public JavaByteCodeLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class JavaByteCodeLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

COMMENT="//".*
NUMBER=(-)?[0-9]+
STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
INST=A(A)?(CONST_NULL|LOAD|STORE)(_[0-9])?|(I)(CONST|LOAD|STORE)(_M?[0-9])?|(I|L|F|D|A)?RETURN|INVOKE(SPECIAL|STATIC|VIRTUAL)|DUP|NOP|IF(EQ|NE|LT|GE|GT|LE|_ICMP(EQ|NE|LT|GE|GT|LE)|_ACMP(EQ|NE))|IINC|ARRAYLENGTH|GOTO|NEW|CHECKCAST
SPACE=[ \t\n\x0B\f\r]+
LBRACKET=\(|\{|\[|«
RBRACKET=\)|\}|\]|»
CONSTMETHODNAMES=<(cl)?init>
PUT_GET_INSTR=(put|get) .* :
LOAD_INSTR=Load([A-Za-z]+)
PRIMITIVETYPE=void|boolean|byte|char|short|int|long|float|double
MODIFIER=static|final|abstract|default
PREMODIFIER=public|private|protected|default
STRINGVAR=[a-zA-Z$_][a-zA-Z0-9$_]*

%%
<YYINITIAL> {
  {WHITE_SPACE}           { return WHITE_SPACE; }

  ","                     { return COMMA; }
  "."                     { return DOT; }
  "class"                 { return CLASS; }
  "extends"               { return EXTENDS; }
  "implements"            { return IMPLEMENTS; }
  "\\n"                   { return EOF; }
  "PC"                    { return PC; }
  "Line"                  { return LINE; }
  "Instruction"           { return INSTRUCTION; }
  "LocalVariableTable"    { return LOCALVARIABLETABLE; }
  "this"                  { return THIS; }

  {COMMENT}               { return COMMENT; }
  {NUMBER}                { return NUMBER; }
  {STRING}                { return STRING; }
  {INST}                  { return INST; }
  {SPACE}                 { return SPACE; }
  {LBRACKET}              { return LBRACKET; }
  {RBRACKET}              { return RBRACKET; }
  {CONSTMETHODNAMES}      { return CONSTMETHODNAMES; }
  {PUT_GET_INSTR}         { return PUT_GET_INSTR; }
  {LOAD_INSTR}            { return LOAD_INSTR; }
  {PRIMITIVETYPE}         { return PRIMITIVETYPE; }
  {MODIFIER}              { return MODIFIER; }
  {PREMODIFIER}           { return PREMODIFIER; }
  {STRINGVAR}             { return STRINGVAR; }

}

[^] { return BAD_CHARACTER; }
