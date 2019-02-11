package syntaxHighlighting;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class TAC_tokenType extends IElementType {

  public TAC_tokenType(@NotNull @NonNls String debugName) {
    super(debugName, TAC.INSTANCE);
  }

  @Override
  public String toString() {
    return "TAC_TokenType." + super.toString();
  }
}
