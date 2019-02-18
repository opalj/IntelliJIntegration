package JavaByteCodeLanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;

/*
 * gathers the possible declarations for the reference and stops the resolve process when it has successfully completed
 */
public class JbcMethodProcessor implements PsiScopeProcessor {

    // fully qualified name
    private String fqn;

    public JbcMethodProcessor(final String fqn) {
        this.fqn = fqn;
    }

    @Override
    public boolean execute(@NotNull PsiElement element, @NotNull ResolveState state) {
        if(!(element instanceof JavaByteCodeDefMethodName)) {   // TODO: or JavaByteCodeJType?
            return true;
        }

        JavaByteCodeDefMethodName jType = (JavaByteCodeDefMethodName)element;

        if(!jType.getName().contains(fqn)) {    // TODO: or equals() ?
            return true;
        }

        // false means declaration has been found
        return false;
    }
}
