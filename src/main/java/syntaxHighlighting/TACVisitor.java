// This is a generated file. Not intended for manual editing.
package syntaxHighlighting;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class TACVisitor extends PsiElementVisitor {

  public void visitJType(@NotNull TACJType o) {
    visit_namedElement(o);
  }

  public void visitType(@NotNull TACType o) {
    visitPsiElement(o);
  }

  public void visitProperty(@NotNull TACProperty o) {
    visit_namedElement(o);
  }

  public void visit_namedElement(@NotNull TAC_namedElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
