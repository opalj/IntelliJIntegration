/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi.impl;

import static JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeTypes.*;

import JavaByteCodeLanguage.autoGenerated.psi.*;
import JavaByteCodeLanguage.psi.impl.JavaByteCodeNamedElementImpl;
import JavaByteCodeLanguage.psi.impl.JavaByteCodePsiImplUtil;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.List;
import org.jetbrains.annotations.*;

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
