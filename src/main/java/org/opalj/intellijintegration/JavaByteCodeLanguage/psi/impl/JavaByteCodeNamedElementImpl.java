/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.JavaByteCodeLanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.opalj.intellijintegration.JavaByteCodeNamedElement;

public abstract class JavaByteCodeNamedElementImpl extends ASTWrapperPsiElement
    implements JavaByteCodeNamedElement {
  public JavaByteCodeNamedElementImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
    // not needed because no refactoring/renaming
    return null;
  }
}
