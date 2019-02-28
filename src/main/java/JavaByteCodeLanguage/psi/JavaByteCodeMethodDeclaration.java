// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.navigation.ItemPresentation;

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
