// This is a generated file. Not intended for manual editing.
package org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.psi.impl;

import static org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeTypes.*;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.*;
import org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeDefMethodName;
import org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeVisitor;
import org.opalj.intellijintegration.JavaByteCodeLanguage.psi.impl.JavaByteCodeNamedElementImpl;
import org.opalj.intellijintegration.JavaByteCodeLanguage.psi.impl.JavaByteCodePsiImplUtil;

public class JavaByteCodeDefMethodNameImpl extends JavaByteCodeNamedElementImpl
    implements JavaByteCodeDefMethodName {

  public JavaByteCodeDefMethodNameImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitDefMethodName(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getStringvar() {
    return findNotNullChildByType(STRINGVAR);
  }

  @Override
  @Nullable
  public String getStringVar() {
    return JavaByteCodePsiImplUtil.getStringVar(this);
  }

  @Override
  public String getName() {
    return JavaByteCodePsiImplUtil.getName(this);
  }

  @Override
  @Nullable
  public PsiElement getNameIdentifier() {
    return JavaByteCodePsiImplUtil.getNameIdentifier(this);
  }

  @Override
  @NotNull
  public PsiReference[] getReferences() {
    return JavaByteCodePsiImplUtil.getReferences(this);
  }
}