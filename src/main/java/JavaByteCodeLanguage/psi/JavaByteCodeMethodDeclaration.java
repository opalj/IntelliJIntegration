// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.*;

public interface JavaByteCodeMethodDeclaration extends PsiElement {

  @NotNull
  List<JavaByteCodeInstr> getInstrList();

  @Nullable
  JavaByteCodeLocVarTableDeleration getLocVarTableDeleration();

  @NotNull
  JavaByteCodeMethodHead getMethodHead();
}
