package JavaByteCodeLanguage.psi;

import JavaByteCodeLanguage.LanguageAndFileType.JavaByteCode;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class JavaByteCodeElementType extends IElementType {
  public JavaByteCodeElementType(@NotNull String debugName) {
    super(debugName, JavaByteCode.INSTANCE);
  }
}
