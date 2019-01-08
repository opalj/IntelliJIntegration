package syntaxHighlighting;

import com.intellij.lexer.FlexAdapter;
import java.io.Reader;

public class TAC_adapter extends FlexAdapter {

  public TAC_adapter() {
    super(new TACLexer((Reader) null));
  }
}
