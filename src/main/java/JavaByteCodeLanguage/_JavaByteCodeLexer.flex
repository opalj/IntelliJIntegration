package JavaByteCodeLanguage;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static JavaByteCodeLanguage.psi.JavaByteCodeTypes.*;

%%

%{
  public _JavaByteCodeLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _JavaByteCodeLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

SPACE=[ \t\n\x0B\f\r]+
COMMENT="//".*
NUMBER=(-)?[0-9]+
STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
INST=A(A)?(CONST_NULL|LOAD|STORE)(_[0-9])?|(I)(CONST|LOAD|STORE)(_M?[0-9])?|(I|L|F|D|A)?RETURN|INVOKE(SPECIAL|STATIC|VIRTUAL)|DUP|NOP|IF(EQ|NE|LT|GE|GT|LE|_ICMP(EQ|NE|LT|GE|GT|LE)|_ACMP(EQ|NE))|IINC|ARRAYLENGTH|GOTO|LoadString|NEW|CHECKCAST
JAVATYPE=(([A-Za-z])*\.)+[A-Za-z]*
PRIMITIVETYPE=void|boolean|byte|char|short|int|long|float|double
MODIFIER=static|protected|final|abstract|default
PREMODIFIER=public|private|default
STRINGVAR=[A-Za-z0-9]+

%%
<YYINITIAL> {
  {WHITE_SPACE}           { return WHITE_SPACE; }

  "\\n"                   { return EOF; }
  "PC"                    { return PC; }
  "Line"                  { return LINE; }
  "Instruction"           { return INSTRUCTION; }
  "LocalVariableTable"    { return LOCALVARIABLETABLE; }

  {SPACE}                 { return SPACE; }
  {COMMENT}               { return COMMENT; }
  {NUMBER}                { return NUMBER; }
  {STRING}                { return STRING; }
  {INST}                  { return INST; }
  {JAVATYPE}              { return JAVATYPE; }
  {PRIMITIVETYPE}         { return PRIMITIVETYPE; }
  {MODIFIER}              { return MODIFIER; }
  {PREMODIFIER}           { return PREMODIFIER; }
  {STRINGVAR}             { return STRINGVAR; }

}

[^] { return BAD_CHARACTER; }
