package syntaxHighlighting.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import syntaxHighlighting.TAC_namedElement;

public abstract class TAC_namedElementImpl extends ASTWrapperPsiElement implements TAC_namedElement {
    public TAC_namedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
