package JavaByteCodeLanguage;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeTypes.*;

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

COMMENT="//".*
BLOCK_COMMENT="/"\*[^\*/]*\*"/"
NUMBER=(-)?[0-9]+(\.[0-9]*)?
STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
MNEMONIC=AALOAD|AASTORE|ACONST_NULL|ALOAD_0|ALOAD_1|ALOAD_2|ALOAD_3|ALOAD|\
                       |ANEWARRAY|ARETURN|ARRAYLENGTH|ASTORE_0|ASTORE_1|ASTORE_2|ASTORE_3|ASTORE|\
                       |ATHROW|BALOAD|BASTORE|BIPUSH|BREAKPOINT|CALOAD|CASTORE|CHECKCAST|\
                       |D2F|D2I|DADD|DALOAD|DASTORE|DCMPG|DCMPL|DCONST_0|DCONST_1|DDIV|\
                       |DLOAD_0|DLOAD_1|DLOAD_2|DLOAD_3|DLOAD|DMUL|DNEG|DREM|DRETURN|\
                       |DSTORE_0|DSTORE_1|DSTORE_2|DSTORE_3|DSTORE|DSUB|DUP|DUP_X1|DUP_X2|\
                       |DUP2|DUP2_X1|DUP2_X2|F2D|F2I|F2L|FADD|FALOAD|FASTORE|FCMPG|FCMPL|\
                       |FCONST_0|FCONST_1|FCONST_2|FDIV|FLOAD_0|FLOAD_1|FLOAD_2|FLOAD_3|FLOAD|\
                       |FMUL|FNEG|FREM|FRETURN|FSTORE_0|FSTORE_1|FSTORE_2|FSTORE_3|FSTORE|FSUB|\
                       |GOTO|GOTO_W|I2B|I2C|I2D|I2F|I2L|I2S|IADD|IALOAD|IAND|IASTORE|ICONST_M1|\
                       |ICONST_0|ICONST_1|ICONST_2|ICONST_3|ICONST_4|ICONST_5|IDIV|IF_ACMPEQ|\
                       |IF_ACMPNE|IF_ICMPEQ|IF_ICMPGE|IF_ICMPGT|IF_ICMPLE|IF_ICMPLT|IF_ICMPNE|\
                       |IFEQ|IFGE|IFGT|IFLE|IFLT|IFNE|IFNONNULL|IFNULL|IINC|\
                       |ILOAD_0|ILOAD_1|ILOAD_2|ILOAD_3|ILOAD|IMPDEP1|IMPDEP2|IMUL|INEG|INSTANCEOF|\
                       |INVOKEDYNAMIC|INVOKEINTERFACE|INVOKESPECIAL|INVOKESTATIC|INVOKEVIRTUAL|\
                       |IOR|IREM|IRETURN|ISHL|ISHR|ISTORE_0|ISTORE_1|ISTORE_2|ISTORE_3|ISTORE|\
                       |ISUB|IUSHR|IXOR|JSR|JSR_W|L2D|L2F|L2I|LADD|LALOAD|LAND|LASTORE|LCMP|\
                       |LCONST_0|LCONST_1|LDC|LDC_W|LDC2_W|LDIV|\
                       |LLOAD_0|LLOAD_1|LLOAD_2|LLOAD_3|LLOAD|LMUL|LNEG|LOOKUPSWITCH|LOR|LREM|\
                       |LRETURN|LSHL|LSTORE_0|LSTORE_1|LSTORE_2|LSTORE_3|LSTORE|LSUB|LUSHR|LXOR|\
                       |MONITORENTER|MONITOREXIT|MULTIANEWARRAY|NEW|NEWARRAY|NOP|POP|POP2|\
                       |RETURN|RET|SALOAD|SASTORE|SIPUSH|SWAP|TABLESWITCH|WIDE|\
                       |GETFIELD|GETSTATIC|GET|PUTFIELD|PUTSTATIC|PUT
SPACE=[ \t\n\x0B\f\r]+
TO=>|=>
LBRACKET=\(|\{|\[|«
RBRACKET=\)|\}|\]|»
CONSTMETHODNAMES=<(cl)?init>
LOAD_INSTR=Load([A-Za-z]+)
PRIMITIVETYPE=void|boolean|byte|char|short|int|long|float|double
MODIFIER=public|private|protected|default|static|final|abstract|synchronized|native|strictfp|volatile|transient
STRINGVAR=[a-zA-Z$_][a-zA-Z0-9$_]*

%%
<YYINITIAL> {
  {WHITE_SPACE}           { return WHITE_SPACE; }

  ","                     { return COMMA; }
  "."                     { return DOT; }
  ":"                     { return COLON; }
  "@"                     { return AT; }
  "class"                 { return CLASS; }
  "extends"               { return EXTENDS; }
  "implements"            { return IMPLEMENTS; }
  "Fields"                { return FIELDS; }
  "lvIndex="              { return FUCKINGTOKEN; }
  "enum"                  { return ENUM; }
  "interface"             { return INTERFACE; }
  "Methods"               { return METHODS; }
  "PC"                    { return PC; }
  "Line"                  { return LINE; }
  "Instruction"           { return INSTRUCTION; }
  "LocalVariableTable"    { return LOCALVARIABLETABLE; }
  "this"                  { return THIS; }
  "StackMapTable"         { return STACKMAPTABLE; }

  {COMMENT}               { return COMMENT; }
  {BLOCK_COMMENT}         { return BLOCK_COMMENT; }
  {NUMBER}                { return NUMBER; }
  {STRING}                { return STRING; }
  {MNEMONIC}              { return MNEMONIC; }
  {SPACE}                 { return SPACE; }
  {TO}                    { return TO; }
  {LBRACKET}              { return LBRACKET; }
  {RBRACKET}              { return RBRACKET; }
  {CONSTMETHODNAMES}      { return CONSTMETHODNAMES; }
  {LOAD_INSTR}            { return LOAD_INSTR; }
  {PRIMITIVETYPE}         { return PRIMITIVETYPE; }
  {MODIFIER}              { return MODIFIER; }
  {STRINGVAR}             { return STRINGVAR; }

}

[^] { return BAD_CHARACTER; }
