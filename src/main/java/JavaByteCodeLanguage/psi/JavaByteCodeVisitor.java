// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.*;

public class JavaByteCodeVisitor extends PsiElementVisitor {

  public void visitClassHead(@NotNull JavaByteCodeClassHead o) {
    visitPsiElement(o);
  }

  public void visitDefMethodName(@NotNull JavaByteCodeDefMethodName o) {
    visitNamedElement(o);
  }

  public void visitInstr(@NotNull JavaByteCodeInstr o) {
    visitPsiElement(o);
  }

  public void visitJType(@NotNull JavaByteCodeJType o) {
    visitNamedElement(o);
  }

  public void visitJavaOP(@NotNull JavaByteCodeJavaOP o) {
    visitPsiElement(o);
  }

  public void visitLocVarTableDeclaration(@NotNull JavaByteCodeLocVarTableDeclaration o) {
    visitNamedElement(o);
  }

  public void visitLocVarTableHead(@NotNull JavaByteCodeLocVarTableHead o) {
    visitPsiElement(o);
  }

  public void visitMethodDeclaration(@NotNull JavaByteCodeMethodDeclaration o) {
    visitNamedElement(o);
  }

  public void visitMethodHead(@NotNull JavaByteCodeMethodHead o) {
    visitPsiElement(o);
  }

  public void visitMethodName(@NotNull JavaByteCodeMethodName o) {
    visitPsiElement(o);
  }

  public void visitModifierV(@NotNull JavaByteCodeModifierV o) {
    visitPsiElement(o);
  }

  public void visitType(@NotNull JavaByteCodeType o) {
    visitPsiElement(o);
  }

  public void visitParams(@NotNull JavaByteCodeParams o) {
    visitPsiElement(o);
  }

  public void visitNamedElement(@NotNull JavaByteCodeNamedElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }
}
