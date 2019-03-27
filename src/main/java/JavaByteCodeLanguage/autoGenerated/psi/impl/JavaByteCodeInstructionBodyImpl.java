// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi.impl;

import static JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeTypes.*;

import JavaByteCodeLanguage.autoGenerated.psi.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.*;

public class JavaByteCodeInstructionBodyImpl extends ASTWrapperPsiElement
    implements JavaByteCodeInstructionBody {

  public JavaByteCodeInstructionBodyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JavaByteCodeVisitor visitor) {
    visitor.visitInstructionBody(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public JavaByteCodeInstr getInstr() {
    return findNotNullChildByClass(JavaByteCodeInstr.class);
  }

  @Override
  @NotNull
  public JavaByteCodeLineNumber getLineNumber() {
    return findNotNullChildByClass(JavaByteCodeLineNumber.class);
  }

  @Override
  @NotNull
  public JavaByteCodePcNumber getPcNumber() {
    return findNotNullChildByClass(JavaByteCodePcNumber.class);
  }
}
