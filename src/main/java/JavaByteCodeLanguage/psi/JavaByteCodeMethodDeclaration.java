// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface JavaByteCodeMethodDeclaration extends PsiElement {

  @NotNull
  List<JavaByteCodeInstr> getInstrList();

  @Nullable
  JavaByteCodeLocVarTableDeleration getLocVarTableDeleration();

  @NotNull
  JavaByteCodeMethodHead getMethodHead();

}
