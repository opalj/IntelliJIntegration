// This is a generated file. Not intended for manual editing.
package taclanguage.autogenerated.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static taclanguage.autogenerated.psi.TAC_elementTypeHolder.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import taclanguage.autogenerated.psi.*;

public class TACMethodAreaImpl extends ASTWrapperPsiElement implements TACMethodArea {

  public TACMethodAreaImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitMethodArea(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<TACMethodDeclaration> getMethodDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TACMethodDeclaration.class);
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
