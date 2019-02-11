package syntaxHighlighting;

import com.intellij.lang.Language;

public class TAC extends Language {

  public static final TAC INSTANCE = new TAC();

  private TAC() {
    super("TAC");
  }
}
