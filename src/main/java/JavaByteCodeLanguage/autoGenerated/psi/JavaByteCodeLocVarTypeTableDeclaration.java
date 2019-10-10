// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi;

import JavaByteCodeLanguage.psi.JavaByteCodeNamedElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.*;

public interface JavaByteCodeLocVarTypeTableDeclaration extends JavaByteCodeNamedElement {

  @NotNull
  List<JavaByteCodeLocVarTypeTableBody> getLocVarTypeTableBodyList();

  @NotNull
  PsiElement getLbracket();

  @NotNull
  PsiElement getRbracket();

  @NotNull
  PsiElement getTablename();

  String getName();

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

  void navigate(boolean requestFocus);
}