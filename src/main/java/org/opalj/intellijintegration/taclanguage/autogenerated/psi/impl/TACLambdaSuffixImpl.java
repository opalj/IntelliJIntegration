// This is a generated file. Not intended for manual editing.
package org.opalj.intellijintegration.taclanguage.autogenerated.psi.impl;

import static org.opalj.intellijintegration.taclanguage.autogenerated.psi.TAC_elementTypeHolder.*;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.List;
import org.jetbrains.annotations.*;
import org.opalj.intellijintegration.taclanguage.autogenerated.psi.TACArrayOrBracket;
import org.opalj.intellijintegration.taclanguage.autogenerated.psi.TACJType;
import org.opalj.intellijintegration.taclanguage.autogenerated.psi.TACLambdaSuffix;
import org.opalj.intellijintegration.taclanguage.autogenerated.psi.TACVisitor;

public class TACLambdaSuffixImpl extends ASTWrapperPsiElement implements TACLambdaSuffix {

  public TACLambdaSuffixImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitLambdaSuffix(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TACArrayOrBracket getArrayOrBracket() {
    return findChildByClass(TACArrayOrBracket.class);
  }

  @Override
  @NotNull
  public List<TACJType> getJTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TACJType.class);
  }

  @Override
  @Nullable
  public PsiElement getNumber() {
    return findChildByType(NUMBER);
  }
}