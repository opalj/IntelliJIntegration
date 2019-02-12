// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi.impl;

import java.util.List;

import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static JavaByteCodeLanguage.psi.JavaByteCodeTypes.*;
import JavaByteCodeLanguage.psi.*;

public class JavaByteCodeMethodNameImpl extends JavaByteCodeNamedElementImpl implements JavaByteCodeMethodName {

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
  @NotNull
  public List<JavaByteCodeType> getTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeType.class);
  }

  @Override
  @Nullable
  public PsiElement getStringvar() {
    return findChildByType(STRINGVAR);
  }

  public String getKey() {
    return JavaByteCodePsiImplUtil.getKey(this);
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

  @NotNull
  @Override
  public PsiReference[] getReferences() {
    JavaClassReferenceProvider provider = new JavaClassReferenceProvider();
    try {
      System.out.println(this.getParent().getFirstChild().getText() + " --- " + this.getText());
      return provider.getReferencesByString(getName(), this.getParent().getFirstChild(), 0);
    } catch(Exception e) {
      return super.getReferences();
    }
  }

}