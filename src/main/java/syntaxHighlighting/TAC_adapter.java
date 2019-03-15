package syntaxHighlighting;

import com.intellij.lexer.FlexAdapter;

public class TAC_adapter extends FlexAdapter {

  public TAC_adapter() {
    super(new TACLexer(null));
  }
}
