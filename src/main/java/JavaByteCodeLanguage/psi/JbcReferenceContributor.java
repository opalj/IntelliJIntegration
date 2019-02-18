package JavaByteCodeLanguage.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class JbcReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        PsiReferenceProvider psiReferenceProvider = new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                if(!(element instanceof JavaByteCodeDefMethodName)) {
                    return new PsiReference[0];
                }

                TextRange range = new TextRange(0, element.getTextLength());
                PsiElement javaOP = element.getParent().getParent();
                PsiElement type = javaOP.getFirstChild();
                JbcMethodReference methodRef = new JbcMethodReference(element, range, type.getText());

                return new PsiReference[] { methodRef };
            }
        };

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(JavaByteCodeDefMethodName.class), psiReferenceProvider);
    }
}
