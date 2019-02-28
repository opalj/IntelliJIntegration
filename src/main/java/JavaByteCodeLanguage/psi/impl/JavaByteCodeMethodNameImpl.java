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

public class JavaByteCodeMethodNameImpl extends ASTWrapperPsiElement implements JavaByteCodeMethodName {

  public JavaByteCodeMethodNameImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitMethodName(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public JavaByteCodeDefMethodName getDefMethodName() {
    return findChildByClass(JavaByteCodeDefMethodName.class);
  }

  @Override
  @NotNull
  public JavaByteCodeParams getParams() {
    return findNotNullChildByClass(JavaByteCodeParams.class);
  }

}
