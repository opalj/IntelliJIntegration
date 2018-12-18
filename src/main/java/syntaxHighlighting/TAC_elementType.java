package syntaxHighlighting;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class TAC_elementType extends IElementType {

    public TAC_elementType(@NotNull @NonNls String debugName) {
        super(debugName, TAC.INSTANCE);
    }

}
