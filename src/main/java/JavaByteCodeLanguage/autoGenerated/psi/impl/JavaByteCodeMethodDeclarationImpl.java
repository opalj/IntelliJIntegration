// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeTypes.*;
import JavaByteCodeLanguage.autoGenerated.psi.*;
import JavaByteCodeLanguage.psi.impl.JavaByteCodePsiImplUtil;
import com.intellij.navigation.ItemPresentation;

public class JavaByteCodeMethodDeclarationImpl extends JavaByteCodeNamedElementImpl implements JavaByteCodeMethodDeclaration {

  public JavaByteCodeMethodDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitMethodDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JavaByteCodeAnnotation> getAnnotationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeAnnotation.class);
  }

  @Override
  @NotNull
  public List<JavaByteCodeExceptionTableDeclaration> getExceptionTableDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeExceptionTableDeclaration.class);
  }

  @Override
  @NotNull
  public List<JavaByteCodeInstructionBody> getInstructionBodyList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeInstructionBody.class);
  }

  @Override
  @NotNull
  public List<JavaByteCodeLocVarTableDeclaration> getLocVarTableDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeLocVarTableDeclaration.class);
  }

  @Override
  @NotNull
  public JavaByteCodeMethodHead getMethodHead() {
    return findNotNullChildByClass(JavaByteCodeMethodHead.class);
  }

  @Override
  @NotNull
  public List<JavaByteCodeStackMapTableDeclaration> getStackMapTableDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeStackMapTableDeclaration.class);
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

  public String getName() {
    return JavaByteCodePsiImplUtil.getName(this);
  }

  public PsiElement setName(String newName) {
    return JavaByteCodePsiImplUtil.setName(this, newName);
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
