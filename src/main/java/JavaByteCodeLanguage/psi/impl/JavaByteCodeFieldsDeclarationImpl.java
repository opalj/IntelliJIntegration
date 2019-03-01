// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi.impl;

import static JavaByteCodeLanguage.psi.JavaByteCodeTypes.*;

import JavaByteCodeLanguage.psi.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.List;
import org.jetbrains.annotations.*;

public class JavaByteCodeFieldsDeclarationImpl extends ASTWrapperPsiElement
    implements JavaByteCodeFieldsDeclaration {

  public JavaByteCodeFieldsDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitFieldsDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JavaByteCodeDefMethodName> getDefMethodNameList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeDefMethodName.class);
  }

  @Override
  @NotNull
  public List<JavaByteCodeType> getTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeType.class);
  }
}
