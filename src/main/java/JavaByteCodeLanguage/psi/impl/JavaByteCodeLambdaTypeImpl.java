// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi.impl;

import static JavaByteCodeLanguage.psi.JavaByteCodeTypes.*;

import JavaByteCodeLanguage.psi.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.*;

public class JavaByteCodeLambdaTypeImpl extends ASTWrapperPsiElement
    implements JavaByteCodeLambdaType {

  public JavaByteCodeLambdaTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitLambdaType(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public JavaByteCodeLambdaParams getLambdaParams() {
    return findNotNullChildByClass(JavaByteCodeLambdaParams.class);
  }

  @Override
  @NotNull
  public PsiElement getNumber() {
    return findNotNullChildByType(NUMBER);
  }
}
