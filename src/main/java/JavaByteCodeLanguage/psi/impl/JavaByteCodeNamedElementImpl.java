package JavaByteCodeLanguage.psi.impl;

import JavaByteCodeLanguage.psi.JavaByteCodeNamedElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class JavaByteCodeNamedElementImpl extends ASTWrapperPsiElement
    implements JavaByteCodeNamedElement {
  public JavaByteCodeNamedElementImpl(@NotNull ASTNode node) {
    super(node);
  }
}
