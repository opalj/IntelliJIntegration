// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static JavaByteCodeLanguage.psi.JavaByteCodeTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import JavaByteCodeLanguage.psi.*;

public class JavaByteCodeInstrImpl extends ASTWrapperPsiElement implements JavaByteCodeInstr {

  public JavaByteCodeInstrImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitInstr(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JavaByteCodeJavaOP> getJavaOPList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JavaByteCodeJavaOP.class);
  }

  @Override
  @Nullable
  public JavaByteCodeType getType() {
    return findChildByClass(JavaByteCodeType.class);
  }

  @Override
  @Nullable
  public PsiElement getInst() {
    return findChildByType(INST);
  }

  @Override
  @Nullable
  public PsiElement getLoadInstr() {
    return findChildByType(LOAD_INSTR);
  }

  @Override
  @Nullable
  public PsiElement getPutGetInstr() {
    return findChildByType(PUT_GET_INSTR);
  }

}
