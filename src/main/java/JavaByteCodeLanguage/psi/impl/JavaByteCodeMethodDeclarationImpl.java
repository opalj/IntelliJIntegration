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

public class JavaByteCodeMethodDeclarationImpl extends ASTWrapperPsiElement
    implements JavaByteCodeMethodDeclaration {

  public JavaByteCodeMethodDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitMethodDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JavaByteCodeInstr> getInstrList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeInstr.class);
  }

  @Override
  @Nullable
  public JavaByteCodeLocVarTableDeleration getLocVarTableDeleration() {
    return findChildByClass(JavaByteCodeLocVarTableDeleration.class);
  }

  @Override
  @NotNull
  public JavaByteCodeMethodHead getMethodHead() {
    return findNotNullChildByClass(JavaByteCodeMethodHead.class);
  }
}
