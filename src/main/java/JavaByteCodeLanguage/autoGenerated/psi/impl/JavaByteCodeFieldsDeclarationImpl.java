// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeTypes.*;
import JavaByteCodeLanguage.psi.impl.JavaByteCodeNamedElementImpl;
import JavaByteCodeLanguage.autoGenerated.psi.*;
import JavaByteCodeLanguage.psi.impl.JavaByteCodePsiImplUtil;
import com.intellij.navigation.ItemPresentation;

public class JavaByteCodeFieldsDeclarationImpl extends JavaByteCodeNamedElementImpl implements JavaByteCodeFieldsDeclaration {

  public JavaByteCodeFieldsDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitFieldsDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public JavaByteCodeDefMethodName getDefMethodName() {
    return findNotNullChildByClass(JavaByteCodeDefMethodName.class);
  }

  @Override
  @Nullable
  public JavaByteCodeModifierV getModifierV() {
    return findChildByClass(JavaByteCodeModifierV.class);
  }

  @Override
  @NotNull
  public JavaByteCodeType getType() {
    return findNotNullChildByClass(JavaByteCodeType.class);
  }

  public String getName() {
    return JavaByteCodePsiImplUtil.getName(this);
  }

  public PsiElement getNameIdentifier() {
    return JavaByteCodePsiImplUtil.getNameIdentifier(this);
  }

  public ItemPresentation getPresentation() {
    return JavaByteCodePsiImplUtil.getPresentation(this);
  }

  public void navigate(boolean requestFocus) {
    JavaByteCodePsiImplUtil.navigate(this, requestFocus);
  }

}
