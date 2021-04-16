// This is a generated file. Not intended for manual editing.
package org.opalj.intellijintegration.taclanguage.autogenerated.psi.impl;

import static org.opalj.intellijintegration.taclanguage.autogenerated.psi.TAC_elementTypeHolder.*;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.*;
import org.opalj.intellijintegration.taclanguage.autogenerated.psi.TACAttributesArea;
import org.opalj.intellijintegration.taclanguage.autogenerated.psi.TACInnerTable;
import org.opalj.intellijintegration.taclanguage.autogenerated.psi.TACVisitor;

public class TACAttributesAreaImpl extends ASTWrapperPsiElement implements TACAttributesArea {

  public TACAttributesAreaImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitAttributesArea(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TACInnerTable getInnerTable() {
    return findChildByClass(TACInnerTable.class);
  }

  @Override
  @NotNull
  public PsiElement getLbracket() {
    return findNotNullChildByType(LBRACKET);
  }

  @Override
  @NotNull
  public PsiElement getRbracket() {
    return findNotNullChildByType(RBRACKET);
  }
}