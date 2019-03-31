// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import JavaByteCodeLanguage.psi.JavaByteCodeNamedElement;
import com.intellij.navigation.ItemPresentation;

public interface JavaByteCodeExceptionTableDeclaration extends JavaByteCodeNamedElement {

  @NotNull
  List<JavaByteCodeExceptionTableBody> getExceptionTableBodyList();

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
