// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.*;

public interface JavaByteCodeInstr extends PsiElement {

  @NotNull
  List<JavaByteCodeJavaOP> getJavaOPList();

  @NotNull
  PsiElement getInst();
}
