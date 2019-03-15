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
import com.intellij.navigation.ItemPresentation;

public class TACPropertyImpl extends TAC_namedElementImpl implements TACProperty {

  public TACPropertyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitProperty(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor)visitor);
    else super.accept(visitor);
  }

  public String getKey() {
    return TACPsiImplUtil.getKey(this);
  }

  public String getValue() {
    return TACPsiImplUtil.getValue(this);
  }

  public String getName() {
    return TACPsiImplUtil.getName(this);
  }

  public PsiElement setName(String newName) {
    return TACPsiImplUtil.setName(this, newName);
  }

  public PsiElement getNameIdentifier() {
    return TACPsiImplUtil.getNameIdentifier(this);
  }

  public ItemPresentation getPresentation() {
    return TACPsiImplUtil.getPresentation(this);
  }

}
