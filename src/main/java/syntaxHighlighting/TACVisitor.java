// This is a generated file. Not intended for manual editing.
package syntaxHighlighting;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class TACVisitor extends PsiElementVisitor {

  public void visitClassHead(@NotNull TACClassHead o) {
    visitPsiElement(o);
  }

  public void visitDefMethodName(@NotNull TACDefMethodName o) {
    visit_namedElement(o);
  }

  public void visitFieldsDeclaration(@NotNull TACFieldsDeclaration o) {
    visitPsiElement(o);
  }

  public void visitInstr(@NotNull TACInstr o) {
    visitPsiElement(o);
  }

  public void visitJType(@NotNull TACJType o) {
    visit_namedElement(o);
  }

  public void visitJavaOP(@NotNull TACJavaOP o) {
    visitPsiElement(o);
  }

  public void visitLambdaType(@NotNull TACLambdaType o) {
    visitPsiElement(o);
  }

  public void visitMethodDeclaration(@NotNull TACMethodDeclaration o) {
    visit_namedElement(o);
  }

  public void visitMethodHead(@NotNull TACMethodHead o) {
    visit_namedElement(o);
  }

  public void visitMethodName(@NotNull TACMethodName o) {
    visitPsiElement(o);
  }

  public void visitModifierV(@NotNull TACModifierV o) {
    visitPsiElement(o);
  }

  public void visitType(@NotNull TACType o) {
    visitPsiElement(o);
  }

  public void visitLambdaParams(@NotNull TACLambdaParams o) {
    visitPsiElement(o);
  }

  public void visitParams(@NotNull TACParams o) {
    visitPsiElement(o);
  }

  public void visit_namedElement(@NotNull TAC_namedElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
