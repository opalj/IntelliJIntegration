// This is a generated file. Not intended for manual editing.
package org.opalj.intellijintegration.taclanguage.autogenerated.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.*;
import org.opalj.intellijintegration.taclanguage.autogenerated.psi.*;

public class TACMethodHeadImpl extends ASTWrapperPsiElement implements TACMethodHead {

  public TACMethodHeadImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitMethodHead(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public TACMethodName getMethodName() {
    return findNotNullChildByClass(TACMethodName.class);
  }

  @Override
  @Nullable
  public TACModifierV getModifierV() {
    return findChildByClass(TACModifierV.class);
  }

  @Override
  @NotNull
  public TACType getType() {
    return findNotNullChildByClass(TACType.class);
  }
}