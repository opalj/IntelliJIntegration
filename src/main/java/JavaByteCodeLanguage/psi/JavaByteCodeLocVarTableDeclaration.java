// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.navigation.ItemPresentation;

public interface JavaByteCodeLocVarTableDeclaration extends JavaByteCodeNamedElement {

  @NotNull
  JavaByteCodeLocVarTableHead getLocVarTableHead();

  @NotNull
  List<JavaByteCodeType> getTypeList();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

  void navigate(boolean requestFocus);

}
