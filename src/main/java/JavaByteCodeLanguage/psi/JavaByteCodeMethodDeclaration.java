// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.*;

public interface JavaByteCodeMethodDeclaration extends JavaByteCodeNamedElement {

  @NotNull
  List<JavaByteCodeInstr> getInstrList();

  @Nullable
  JavaByteCodeLocVarTableDeclaration getLocVarTableDeclaration();

  @NotNull
  JavaByteCodeMethodHead getMethodHead();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

  void navigate(boolean requestFocus);
}
