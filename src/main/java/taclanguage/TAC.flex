package taclanguage;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static taclanguage.autogenerated.psi.TAC_elementTypeHolder.*;

%%

%{
  public TACLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class TACLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

WHITE_SPACE=[ \t\n\x0B\f\r]
COMMENT="//".*|"/"\*[^*/]*\*"/"
NUMBER=[-]?[0-9]+(\.[0-9]*)?([Efdl][0-9]*)?
STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
LBRACKET=\(|\{|\[|[«]
RBRACKET=\)|\}|\]|[»]
JAVATYPEHEAD=class|enum|interface
INSTRUCTIONHEAD=PC[ \t\n\x0B\f\r]+Line[ \t\n\x0B\f\r]+Instruction
BOOLS=true|false
PRIMITIVETYPE=void|boolean|byte|char|short|int|long|float|double
KEYWORDS=new|goto|if|else|throw|throws|catch|caught|return
MODIFIER=public|private|protected|default|static|final|abstract|synchronized|native|strictfp|volatile|transient
LEVEL=\{?((lv|param|exception(\[VM\])?@)([A-Za-z0-9])*(,[ \t\n\x0B\f\r])?)*\}?
OPERATORS=\+|\-|\*|\&|\||\^|<<|>>
COMPARATORS=<=|>=|==|\!=|<|>
STRINGVAR=<?[a-zA-Z$_][a-zA-Z0-9$_<>]*>?

%%
<YYINITIAL> {
  {WHITE_SPACE}          { return WHITE_SPACE; }

  ","                    { return COMMA; }
  "."                    { return DOT; }
  ":"                    { return COLON; }
  ";"                    { return SEMICOLON; }
  "/"                    { return SLASH; }
  "@"                    { return AT; }
  "="                    { return EQ; }
  "⤼"                    { return SWITCH; }
  "extends"              { return EXTENDS; }
  "implements"           { return IMPLEMENTS; }
  "throws"               { return THROWS; }
  "Attributes"           { return ATTRIBUTES; }
  "Fields"               { return FIELDS; }
  "Methods"              { return METHODS; }
  "lvIndex="             { return LVLINDEX; }
  "/"                    { return DIVID; }
  "TABLES"               { return TABLES; }
  "TABLENAME"            { return TABLENAME; }
  "THROW"                { return THROW; }
  "VOID"                 { return VOID; }
  "THIS"                 { return THIS; }
  "R_ARROW"              { return R_ARROW; }
  "L_ARROW"              { return L_ARROW; }
  "CONSTMETHODNAMES"     { return CONSTMETHODNAMES; }
  "UEQ"                  { return UEQ; }
  "PLUS"                 { return PLUS; }
  "MINUS"                { return MINUS; }

  {WHITE_SPACE}          { return WHITE_SPACE; }
  {COMMENT}              { return COMMENT; }
  {NUMBER}               { return NUMBER; }
  {STRING}               { return STRING; }
  {LBRACKET}             { return LBRACKET; }
  {RBRACKET}             { return RBRACKET; }
  {JAVATYPEHEAD}         { return JAVATYPEHEAD; }
  {INSTRUCTIONHEAD}      { return INSTRUCTIONHEAD; }
  {BOOLS}                { return BOOLS; }
  {PRIMITIVETYPE}        { return PRIMITIVETYPE; }
  {KEYWORDS}             { return KEYWORDS; }
  {MODIFIER}             { return MODIFIER; }
  {LEVEL}                { return LEVEL; }
  {OPERATORS}            { return OPERATORS; }
  {COMPARATORS}          { return COMPARATORS; }
  {STRINGVAR}            { return STRINGVAR; }

}

[^] { return BAD_CHARACTER; }
