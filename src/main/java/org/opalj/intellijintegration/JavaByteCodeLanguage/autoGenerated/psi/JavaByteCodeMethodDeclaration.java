// This is a generated file. Not intended for manual editing.
package org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.psi;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.*;
import org.opalj.intellijintegration.JavaByteCodeNamedElement;

public interface JavaByteCodeMethodDeclaration extends JavaByteCodeNamedElement {

  @NotNull
  List<JavaByteCodeAnnotation> getAnnotationList();

  @NotNull
  List<JavaByteCodeInstructionBody> getInstructionBodyList();

  @NotNull
  JavaByteCodeMethodHead getMethodHead();

  @NotNull
  List<JavaByteCodeTableArea> getTableAreaList();

  @NotNull
  PsiElement getLbracket();

  @NotNull
  PsiElement getRbracket();

  String getName();

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

  void navigate(boolean requestFocus);
}