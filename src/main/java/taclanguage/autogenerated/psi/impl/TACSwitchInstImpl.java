// This is a generated file. Not intended for manual editing.
package taclanguage.autogenerated.psi.impl;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static taclanguage.autogenerated.psi.TAC_elementTypeHolder.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import taclanguage.autogenerated.psi.*;

public class TACSwitchInstImpl extends ASTWrapperPsiElement implements TACSwitchInst {

  public TACSwitchInstImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitSwitchInst(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getLevel() {
    return findNotNullChildByType(LEVEL);
  }

}
