package JavaByteCodeLanguage.Lexer;

import com.intellij.lexer.FlexAdapter;


public class JavaByteCodeLexerAdapter extends FlexAdapter {
    public JavaByteCodeLexerAdapter() {
        super(new JavaByteCodeLexer( null));
    }
}
