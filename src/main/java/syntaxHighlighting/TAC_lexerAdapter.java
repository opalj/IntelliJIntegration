package syntaxHighlighting;

import com.intellij.lexer.FlexAdapter;

public class TAC_lexerAdapter extends FlexAdapter {
    public TAC_lexerAdapter() {
        super(new TACLexer( null));
    }
}