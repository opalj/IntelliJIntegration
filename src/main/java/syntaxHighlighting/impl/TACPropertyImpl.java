// This is a generated file. Not intended for manual editing.
package syntaxHighlighting.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import syntaxHighlighting.TACProperty;
import syntaxHighlighting.TACVisitor;

public class TACPropertyImpl extends ASTWrapperPsiElement implements TACProperty {

  public TACPropertyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitProperty(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  public String getKey() {
    return null;
  }

  @Override
  public String getValue() {
    return null;
  }

  @Override
  public PsiElement setName(String newName) {
    return null;
  }

  @Override
  public PsiElement getNameIdentifier() {
    return null;
  }
}
