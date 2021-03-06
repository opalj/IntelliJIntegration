// This is a generated file. Not intended for manual editing.
package org.opalj.intellijintegration.taclanguage.autogenerated.psi;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.*;
import org.opalj.intellijintegration.taclanguage.psi.TACNamedElement;

public interface TACMethodDeclaration extends TACNamedElement {

  @NotNull
  List<TACAnnotation> getAnnotationList();

  @NotNull
  List<TACInstructionBody> getInstructionBodyList();

  @NotNull
  TACMethodHead getMethodHead();

  @NotNull
  PsiElement getLbracket();

  @NotNull
  PsiElement getRbracket();

  String getName();

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

  void navigate(boolean requestFocus);
}
