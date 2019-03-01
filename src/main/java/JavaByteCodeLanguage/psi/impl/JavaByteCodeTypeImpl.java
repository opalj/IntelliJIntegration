// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static JavaByteCodeLanguage.psi.JavaByteCodeTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import JavaByteCodeLanguage.psi.*;

public class JavaByteCodeTypeImpl extends ASTWrapperPsiElement implements JavaByteCodeType {

  public JavaByteCodeTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitType(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public JavaByteCodeJType getJType() {
    return findChildByClass(JavaByteCodeJType.class);
  }

  @Override
  @Nullable
  public JavaByteCodeLambdaType getLambdaType() {
    return findChildByClass(JavaByteCodeLambdaType.class);
  }

  @Override
  @Nullable
  public PsiElement getPrimitivetype() {
    return findChildByType(PRIMITIVETYPE);
  }

}
