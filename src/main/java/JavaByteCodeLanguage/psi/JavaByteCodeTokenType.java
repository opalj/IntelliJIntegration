package JavaByteCodeLanguage.psi;

import JavaByteCodeLanguage.LanguageAndFileType.JavaByteCode;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class JavaByteCodeTokenType extends IElementType {

  protected JavaByteCodeTokenType(@NotNull String debugName) {
    super(debugName, JavaByteCode.INSTANCE);
  }

  @Override
  public String toString() {
    return "JBCTokenType." + super.toString();
  }
}
