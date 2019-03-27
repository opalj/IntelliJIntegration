// This is a generated file. Not intended for manual editing.
package taclanguage.autogenerated.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import taclanguage.psi.TACNamedElement;
import com.intellij.navigation.ItemPresentation;

public interface TACFieldsDeclaration extends TACNamedElement {

  @NotNull
  TACDefMethodName getDefMethodName();

  @Nullable
  TACModifierV getModifierV();

  @NotNull
  TACType getType();

  String getName();

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

  void navigate(boolean requestFocus);

}
