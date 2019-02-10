package JavaByteCodeLanguage;

import JavaByteCodeLanguage.psi.JavaByteCodeTypes;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;

%%

%class JavaByteCodeLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}


CRLF=\R
WHITE_SPACE=[\ \n\t\f]
FIRST_VALUE_CHARACTER=[^ \n\f\\] | "\\"{CRLF} | "\\".
VALUE_CHARACTER=[^\n\f\\] | "\\"{CRLF} | "\\".

    LineTerminator = \r|\n|\r\n
    InputCharacter = [^\r\n]
    WhiteSpace     = {LineTerminator} | [ \t\f]
 /* comments */
    Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

    TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
    // Comment can be the last line of the file, without line terminator.
    EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
    DocumentationComment = "/**" {CommentContent} "*"+ "/"
    CommentContent       = ( [^*] | \*+ [^/*] )*
    /*DecimalInteger*/
    DecIntegerLiteral = 0 | [1-9][0-9]*
//SEPARATOR=[:]
//KEY_CHARACTER=[^:=\ \n\t\f\\] | "\\ "

%state WAITING_VALUE

%%

<YYINITIAL> {Comment}                                              { yybegin(YYINITIAL); return JavaByteCodeTypes.COMMENT; }

//<YYINITIAL> {KEY_CHARACTER}+                                { yybegin(YYINITIAL); return JavaByteCodeTypes.KEY; }

//<YYINITIAL> {SEPARATOR}                                     { yybegin(WAITING_VALUE); return JavaByteCodeTypes.SEPARATOR; }

<WAITING_VALUE> {CRLF}({CRLF}|{WHITE_SPACE})+               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }

<WAITING_VALUE> {FIRST_VALUE_CHARACTER}{VALUE_CHARACTER}*   { yybegin(YYINITIAL); return JavaByteCodeTypes.VALUE; }

({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }