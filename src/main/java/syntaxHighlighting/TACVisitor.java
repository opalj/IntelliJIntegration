// This is a generated file. Not intended for manual editing.
package syntaxHighlighting;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class TACVisitor extends PsiElementVisitor {

  public void visitAnnotation(@NotNull TACAnnotation o) {
    visitPsiElement(o);
  }

  public void visitAttributesArea(@NotNull TACAttributesArea o) {
    visitPsiElement(o);
  }

  public void visitClassHead(@NotNull TACClassHead o) {
    visitPsiElement(o);
  }

  public void visitDefMethodName(@NotNull TACDefMethodName o) {
    visitNamedElement(o);
  }

  public void visitFieldArea(@NotNull TACFieldArea o) {
    visitPsiElement(o);
  }

  public void visitFieldsDeclaration(@NotNull TACFieldsDeclaration o) {
    visitNamedElement(o);
  }

  public void visitInnerTable(@NotNull TACInnerTable o) {
    visitPsiElement(o);
  }

  public void visitInstr(@NotNull TACInstr o) {
    visitPsiElement(o);
  }

  public void visitJType(@NotNull TACJType o) {
    visitNamedElement(o);
  }

  public void visitLambdaType(@NotNull TACLambdaType o) {
    visitPsiElement(o);
  }

  public void visitMethodArea(@NotNull TACMethodArea o) {
    visitPsiElement(o);
  }

  public void visitMethodDeclaration(@NotNull TACMethodDeclaration o) {
    visitNamedElement(o);
  }

  public void visitMethodHead(@NotNull TACMethodHead o) {
    visitPsiElement(o);
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

  public void visitSwitchInst(@NotNull TACSwitchInst o) {
    visitPsiElement(o);
  }

  public void visitNamedElement(@NotNull TACNamedElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
