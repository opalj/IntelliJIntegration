// This is a generated file. Not intended for manual editing.
package taclanguage.autogenerated.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static taclanguage.autogenerated.psi.TAC_elementTypeHolder.*;
import taclanguage.psi.impl.TACNamedElementImpl;
import taclanguage.autogenerated.psi.*;
import taclanguage.psi.impl.TAC_parserParserUtil;
import com.intellij.navigation.ItemPresentation;

public class TACMethodDeclarationImpl extends TACNamedElementImpl implements TACMethodDeclaration {

  public TACMethodDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitMethodDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<TACAnnotation> getAnnotationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TACAnnotation.class);
  }

  @Override
  @NotNull
  public List<TACInstr> getInstrList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TACInstr.class);
  }

  @Override
  @NotNull
  public TACMethodHead getMethodHead() {
    return findNotNullChildByClass(TACMethodHead.class);
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

  public String getName() {
    return TAC_parserParserUtil.getName(this);
  }

  public PsiElement getNameIdentifier() {
    return TAC_parserParserUtil.getNameIdentifier(this);
  }

  public ItemPresentation getPresentation() {
    return TAC_parserParserUtil.getPresentation(this);
  }

  public void navigate(boolean requestFocus) {
    TAC_parserParserUtil.navigate(this, requestFocus);
  }

}
