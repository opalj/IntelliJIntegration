package syntaxHighlighting.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.*;
import syntaxHighlighting.TAC;

public class TAC_elementType extends IElementType {

  public TAC_elementType(@NotNull @NonNls String debugName) {
    super(debugName, TAC.INSTANCE);
  }
}
