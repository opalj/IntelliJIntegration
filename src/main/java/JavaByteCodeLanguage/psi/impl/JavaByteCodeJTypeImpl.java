// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static JavaByteCodeLanguage.psi.JavaByteCodeTypes.*;
import JavaByteCodeLanguage.psi.*;
import com.intellij.psi.PsiReference;

public class JavaByteCodeJTypeImpl extends JavaByteCodeNamedElementImpl implements JavaByteCodeJType {

  public JavaByteCodeJTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitJType(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Nullable
  public String getJavaType() {
    return JavaByteCodePsiImplUtil.getJavaType(this);
  }

  public String getName() {
    return JavaByteCodePsiImplUtil.getName(this);
  }

  public PsiElement setName(String newName) {
    return JavaByteCodePsiImplUtil.setName(this, newName);
  }

  @Nullable
  public PsiElement getNameIdentifier() {
    return JavaByteCodePsiImplUtil.getNameIdentifier(this);
  }

  public PsiReference[] getReferences() {
    return JavaByteCodePsiImplUtil.getReferences(this);
  }

}
