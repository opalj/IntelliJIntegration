// This is a generated file. Not intended for manual editing.
package syntaxHighlighting.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static syntaxHighlighting.TAC_elementTypeHolder.*;
import syntaxHighlighting.*;
import com.intellij.psi.PsiReference;

public class TACJTypeImpl extends TAC_namedElementImpl implements TACJType {

  public TACJTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitJType(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getJavaType() {
    return findNotNullChildByType(JAVA_TYPE);
  }

  @Nullable
  public String getJavaTypeString() {
    return TACPsiImplUtil.getJavaTypeString(this);
  }

  public String getName() {
    return TACPsiImplUtil.getName(this);
  }

  public PsiElement setName(String newName) {
    return TACPsiImplUtil.setName(this, newName);
  }

  @Nullable
  public PsiElement getNameIdentifier() {
    return TACPsiImplUtil.getNameIdentifier(this);
  }

  public PsiReference[] getReferences() {
    return TACPsiImplUtil.getReferences(this);
  }

}
