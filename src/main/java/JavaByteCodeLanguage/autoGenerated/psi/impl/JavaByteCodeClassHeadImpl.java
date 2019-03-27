// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import JavaByteCodeLanguage.autoGenerated.psi.*;
import JavaByteCodeLanguage.psi.impl.JavaByteCodePsiImplUtil;

public class JavaByteCodeClassHeadImpl extends ASTWrapperPsiElement implements JavaByteCodeClassHead {

  public JavaByteCodeClassHeadImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitClassHead(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JavaByteCodeJType> getJTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeJType.class);
  }

  @Override
  @Nullable
  public JavaByteCodeModifierV getModifierV() {
    return findChildByClass(JavaByteCodeModifierV.class);
  }

  @Override
  @NotNull
  public PsiElement getJavatypehead() {
    return findNotNullChildByType(JAVATYPEHEAD);
  }

}
