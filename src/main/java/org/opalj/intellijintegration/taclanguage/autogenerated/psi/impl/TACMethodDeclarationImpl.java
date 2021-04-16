// This is a generated file. Not intended for manual editing.
package org.opalj.intellijintegration.taclanguage.autogenerated.psi.impl;

import static org.opalj.intellijintegration.taclanguage.autogenerated.psi.TAC_elementTypeHolder.*;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.List;
import org.jetbrains.annotations.*;
import org.opalj.intellijintegration.taclanguage.autogenerated.psi.*;
import org.opalj.intellijintegration.taclanguage.psi.impl.TACNamedElementImpl;
import org.opalj.intellijintegration.taclanguage.psi.impl.TAC_parserParserUtil;

public class TACMethodDeclarationImpl extends TACNamedElementImpl implements TACMethodDeclaration {

  public TACMethodDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitMethodDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<TACAnnotation> getAnnotationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TACAnnotation.class);
  }

  @Override
  @NotNull
  public List<TACInstructionBody> getInstructionBodyList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TACInstructionBody.class);
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

  @Override
  public String getName() {
    return TAC_parserParserUtil.getName(this);
  }

  @Override
  public PsiElement getNameIdentifier() {
    return TAC_parserParserUtil.getNameIdentifier(this);
  }

  @Override
  public ItemPresentation getPresentation() {
    return TAC_parserParserUtil.getPresentation(this);
  }

  @Override
  public void navigate(boolean requestFocus) {
    TAC_parserParserUtil.navigate(this, requestFocus);
  }
}