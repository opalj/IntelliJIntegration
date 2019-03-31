// This is a generated file. Not intended for manual editing.
package taclanguage.autogenerated.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static taclanguage.autogenerated.psi.TAC_elementTypeHolder.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import taclanguage.autogenerated.psi.*;
import taclanguage.psi.impl.TAC_parserParserUtil;

public class TACModifierVImpl extends ASTWrapperPsiElement implements TACModifierV {

  public TACModifierVImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TACVisitor visitor) {
    visitor.visitModifierV(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TACVisitor) accept((TACVisitor)visitor);
    else super.accept(visitor);
  }

}
