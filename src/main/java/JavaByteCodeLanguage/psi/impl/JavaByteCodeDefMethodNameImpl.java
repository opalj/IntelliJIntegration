// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi.impl;

import static JavaByteCodeLanguage.psi.JavaByteCodeTypes.*;

import JavaByteCodeLanguage.psi.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.*;

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

  public String getStringVar() {
    return JavaByteCodePsiImplUtil.getStringVar(this);
  }

  public String getName() {
    return JavaByteCodePsiImplUtil.getName(this);
  }

  public PsiElement setName(String newName) {
    return JavaByteCodePsiImplUtil.setName(this, newName);
  }

  public PsiElement getNameIdentifier() {
    return JavaByteCodePsiImplUtil.getNameIdentifier(this);
  }

  public PsiReference[] getReferences() {
    return JavaByteCodePsiImplUtil.getReferences(this);
  }
}
