// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi.impl;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import JavaByteCodeLanguage.autoGenerated.psi.*;

public class JavaByteCodeAnnotationImpl extends ASTWrapperPsiElement implements JavaByteCodeAnnotation {

  public JavaByteCodeAnnotationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitAnnotation(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public JavaByteCodeJType getJType() {
    return findNotNullChildByClass(JavaByteCodeJType.class);
  }

}
