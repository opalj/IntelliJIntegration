package JavaByteCodeLanguage.Lexer;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class JavaByteCodeLexerAdapter extends FlexAdapter {
    public JavaByteCodeLexerAdapter() {
        super(new JavaByteCodeLexer((Reader) null));
    }
}
