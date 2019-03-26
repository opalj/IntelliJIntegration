package taclanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import taclanguage.psi.TACNamedElement;

public abstract class TACNamedElementImpl extends ASTWrapperPsiElement implements TACNamedElement {
    public TACNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        //not needed because no refactoring/renaming
        return null;
    }

}
