package syntaxHighlighting.psi;

import com.intellij.psi.tree.IElementType;
import syntaxHighlighting.TAC;
import org.jetbrains.annotations.*;

public class TAC_tokenType extends IElementType {

  public TAC_tokenType(@NotNull @NonNls String debugName) {
    super(debugName, TAC.INSTANCE);
  }

  @Override
  public String toString() {
    return "TAC_TokenType." + super.toString();
  }
}