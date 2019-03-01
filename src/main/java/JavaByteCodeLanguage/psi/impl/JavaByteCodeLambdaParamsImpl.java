// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi.impl;

import static JavaByteCodeLanguage.psi.JavaByteCodeTypes.*;

import JavaByteCodeLanguage.psi.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.*;

public class JavaByteCodeLambdaParamsImpl extends ASTWrapperPsiElement
    implements JavaByteCodeLambdaParams {

  public JavaByteCodeLambdaParamsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitLambdaParams(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor) visitor);
    else super.accept(visitor);
  }
}
