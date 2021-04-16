// This is a generated file. Not intended for manual editing.
package org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.psi.impl;

import static org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeTypes.*;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.List;
import org.jetbrains.annotations.*;
import org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeStackMapTableDeclaration;
import org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeType;
import org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeVisitor;
import org.opalj.intellijintegration.JavaByteCodeLanguage.psi.impl.JavaByteCodeNamedElementImpl;
import org.opalj.intellijintegration.JavaByteCodeLanguage.psi.impl.JavaByteCodePsiImplUtil;

public class JavaByteCodeStackMapTableDeclarationImpl extends JavaByteCodeNamedElementImpl
    implements JavaByteCodeStackMapTableDeclaration {

  public JavaByteCodeStackMapTableDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitStackMapTableDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JavaByteCodeType> getTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeType.class);
  }

  @Override
  @NotNull
  public PsiElement getTablename() {
    return findNotNullChildByType(TABLENAME);
  }

  @Override
  public String getName() {
    return JavaByteCodePsiImplUtil.getName(this);
  }

  @Override
  public PsiElement getNameIdentifier() {
    return JavaByteCodePsiImplUtil.getNameIdentifier(this);
  }

  @Override
  public ItemPresentation getPresentation() {
    return JavaByteCodePsiImplUtil.getPresentation(this);
  }

  @Override
  public void navigate(boolean requestFocus) {
    JavaByteCodePsiImplUtil.navigate(this, requestFocus);
  }
}