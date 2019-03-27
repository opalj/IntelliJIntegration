// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi.impl;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import JavaByteCodeLanguage.autoGenerated.psi.*;

public class JavaByteCodeAttributesAreaImpl extends ASTWrapperPsiElement implements JavaByteCodeAttributesArea {

  public JavaByteCodeAttributesAreaImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitAttributesArea(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public JavaByteCodeInnerTable getInnerTable() {
    return findChildByClass(JavaByteCodeInnerTable.class);
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

}
