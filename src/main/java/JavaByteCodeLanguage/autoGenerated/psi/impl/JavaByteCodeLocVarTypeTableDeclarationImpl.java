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

public class JavaByteCodeLocVarTypeTableDeclarationImpl extends ASTWrapperPsiElement implements JavaByteCodeLocVarTypeTableDeclaration {

  public JavaByteCodeLocVarTypeTableDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitLocVarTypeTableDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JavaByteCodeLocVarTypeTableBody> getLocVarTypeTableBodyList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeLocVarTypeTableBody.class);
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

}
