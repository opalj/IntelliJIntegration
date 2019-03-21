// This is a generated file. Not intended for manual editing.
package syntaxHighlighting.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static syntaxHighlighting.TAC_elementTypeHolder.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import syntaxHighlighting.*;

public class TACTypeImpl extends ASTWrapperPsiElement implements TACType {

  public TACTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitType(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TACJType getJType() {
    return findChildByClass(TACJType.class);
  }

  @Override
  @Nullable
  public PsiElement getNumber() {
    return findChildByType(NUMBER);
  }

  @Override
  @Nullable
  public PsiElement getStringvar() {
    return findChildByType(STRINGVAR);
  }

}
