// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class JavaByteCodeVisitor extends PsiElementVisitor {

  public void visitInstr(@NotNull JavaByteCodeInstr o) {
    visitPsiElement(o);
  }

  public void visitJavaOP(@NotNull JavaByteCodeJavaOP o) {
    visitPsiElement(o);
  }

  public void visitLocVarTableDeleration(@NotNull JavaByteCodeLocVarTableDeleration o) {
    visitPsiElement(o);
  }

  public void visitMethodDeclaration(@NotNull JavaByteCodeMethodDeclaration o) {
    visitPsiElement(o);
  }

  public void visitMethodHead(@NotNull JavaByteCodeMethodHead o) {
    visitPsiElement(o);
  }

  public void visitMethodName(@NotNull JavaByteCodeMethodName o) {
    visitNamedElement(o);
  }

  public void visitModifier(@NotNull JavaByteCodeModifier o) {
    visitPsiElement(o);
  }

  public void visitType(@NotNull JavaByteCodeType o) {
    visitNamedElement(o);
  }

  public void visitNamedElement(@NotNull JavaByteCodeNamedElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
