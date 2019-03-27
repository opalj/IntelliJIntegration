package JavaByteCodeLanguage.psi;

import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeClassHead;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeDefMethodName;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeJType;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeJavaOP;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

/** for elaborate JavaDoc, see {@link PsiReferenceContributor} */
public class JbcReferenceContributor extends PsiReferenceContributor {

  @Override
  public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
    PsiReferenceProvider psiReferenceProvider =
        new PsiReferenceProvider() {
          @Override
          public PsiReference[] getReferencesByElement(
              @NotNull PsiElement element, @NotNull ProcessingContext context) {
            JbcMethodAndFieldReference methodReference;

            // the element is guaranteed to be of type JavaByteCodeDefMethodName (see below)
            TextRange range = new TextRange(0, element.getTextLength());

            PsiElement grandparent = element.getParent().getParent();

            // a method can either be part of an instruction
            if (grandparent instanceof JavaByteCodeJavaOP) {
              // example: type = java.io.PrintStream (<- the FQN)
              PsiElement type = grandparent.getFirstChild();
              methodReference = new JbcMethodAndFieldReference(element, range, type.getText());
            }
            // or be part of "this" file (for which the bytecode has been generated)
            else {
              PsiElement file = element.getContainingFile();
              // example: classHead = public class io.ChainedReader extends java.lang.Object
              PsiElement classHead;
              classHead =
                  Arrays.stream(file.getChildren())
                      .filter(psiElement -> psiElement instanceof JavaByteCodeClassHead)
                      .findFirst()
                      .orElse(null); // get()orElse(null)
              // example: typeOfClass = io.ChainedReader

              if (classHead != null) {
                PsiElement typeOfClass =
                    Arrays.stream(classHead.getChildren())
                        .filter(psiElement -> psiElement instanceof JavaByteCodeJType)
                        .findFirst()
                        .orElse(null);
                if (typeOfClass != null) {
                  methodReference =
                      new JbcMethodAndFieldReference(element, range, typeOfClass.getText());
                } else return null;
              } else return null;
            }

            return new PsiReference[] {methodReference};
          }
        };

    /**
     * (note: this is a dangling JavaDoc; purpose: provide easy link to class mentioned below)
     *
     * <p>the Pattern ensures that psiReferenceProvider#getReferencesByElement(...) is only executed
     * for JavaByteCodeDefMethodName elements
     *
     * @see com.intellij.psi.PsiReferenceRegistrar
     */
    registrar.registerReferenceProvider(
        PlatformPatterns.psiElement(JavaByteCodeDefMethodName.class), psiReferenceProvider);
  }
}
