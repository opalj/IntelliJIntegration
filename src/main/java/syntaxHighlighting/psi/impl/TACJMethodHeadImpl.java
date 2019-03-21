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

public class TACJMethodHeadImpl extends ASTWrapperPsiElement implements TACJMethodHead {

  public TACJMethodHeadImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitJMethodHead(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TACType getType() {
    return findChildByClass(TACType.class);
  }

  @Override
  @Nullable
  public PsiElement getComment() {
    return findChildByType(COMMENT);
  }

  @Override
  @Nullable
  public PsiElement getKeywords() {
    return findChildByType(KEYWORDS);
  }

  @Override
  @NotNull
  public PsiElement getLBracket() {
    return findNotNullChildByType(L_BRACKET);
  }

  @Override
  @Nullable
  public PsiElement getLDoubleLaceBrace() {
    return findChildByType(L_DOUBLE_LACE_BRACE);
  }

  @Override
  @Nullable
  public PsiElement getLLaceBrace() {
    return findChildByType(L_LACE_BRACE);
  }

  @Override
  @NotNull
  public PsiElement getRBracket() {
    return findNotNullChildByType(R_BRACKET);
  }

  @Override
  @Nullable
  public PsiElement getRDoubleLaceBrace() {
    return findChildByType(R_DOUBLE_LACE_BRACE);
  }

  @Override
  @Nullable
  public PsiElement getRLaceBrace() {
    return findChildByType(R_LACE_BRACE);
  }

  @Override
  @Nullable
  public PsiElement getVoid() {
    return findChildByType(VOID);
  }

}
