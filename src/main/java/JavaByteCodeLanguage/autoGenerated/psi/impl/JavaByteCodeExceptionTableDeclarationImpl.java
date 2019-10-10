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

public class JavaByteCodeExceptionTableDeclarationImpl extends JavaByteCodeNamedElementImpl
    implements JavaByteCodeExceptionTableDeclaration {

  public JavaByteCodeExceptionTableDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitExceptionTableDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JavaByteCodeExceptionTableBody> getExceptionTableBodyList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeExceptionTableBody.class);
  }

  @Override
  @NotNull
  public PsiElement getLbracket() {
    return findNotNullChildByType(LBRACKET);
  }

  @Override
  @NotNull
  public PsiElement getRbracket() {
    return findNotNullChildByType(RBRACKET);
  }

  @Override
  @NotNull
  public PsiElement getTablename() {
    return findNotNullChildByType(TABLENAME);
  }

  @Override
  public String getName() {
    return JavaByteCodePsiImplUtil.getName(this);
  }

  @Override
  public PsiElement getNameIdentifier() {
    return JavaByteCodePsiImplUtil.getNameIdentifier(this);
  }

  @Override
  public ItemPresentation getPresentation() {
    return JavaByteCodePsiImplUtil.getPresentation(this);
  }

  @Override
  public void navigate(boolean requestFocus) {
    JavaByteCodePsiImplUtil.navigate(this, requestFocus);
  }
}
