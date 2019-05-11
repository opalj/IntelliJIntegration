// This is a generated file. Not intended for manual editing.
package taclanguage.autogenerated.psi.impl;

import static taclanguage.autogenerated.psi.TAC_elementTypeHolder.*;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.List;
import org.jetbrains.annotations.*;
import taclanguage.autogenerated.psi.*;

public class TACFieldAreaImpl extends ASTWrapperPsiElement implements TACFieldArea {

  public TACFieldAreaImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitFieldArea(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<TACFieldsDeclaration> getFieldsDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TACFieldsDeclaration.class);
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
