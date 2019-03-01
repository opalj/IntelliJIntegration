package JavaByteCodeLanguage.psi;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that resolves references for methods in the JavaByteCode-Editor (e.g. for
 * println(java.lang.String) it finds the println(String) method in PrintStream.java)
 */
public class JbcMethodReference extends PsiReferenceBase<PsiElement> {

  // the FQN of the class the method belongs to
  private String fqn;

  public JbcMethodReference(@NotNull PsiElement element, TextRange textRange, String fqn) {
    super(element, textRange);
    this.fqn = fqn;
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    if (myElement instanceof JavaByteCodeDefMethodName) {
      final Project project = myElement.getProject();
      PsiClass psiClass =
          JavaPsiFacade.getInstance(project).findClass(fqn, GlobalSearchScope.allScope(project));
      PsiMethod psiMethod = (psiClass != null) ? findAppropriateOverload(psiClass) : null;
      return psiMethod;
    }

    return null;
  }

  @NotNull
  @Override
  public Object[] getVariants() {
    return new Object[0];
  }

  /**
   * finds the appropriate overload of the method for a given class, e.g. when pressing on
   * println(java.lang.String) it will find java.io.PrintStream.println(String), and not println()
   * or println(char)
   *
   * @param psiClass the class to which the method belongs to (e.g. java.io.PrintStream in the case
   *     of println)
   * @return the correct overload of the method that fits the signature (method name and parameter
   *     list)
   */
  @Nullable
  private PsiMethod findAppropriateOverload(@NotNull PsiClass psiClass) {
    // relies on underlying grammar
    String methodName = myElement.getText();
    String parameterList = myElement.getParent().getLastChild().getText();

    // this guarantees that the name matches, if the method exists in the class
    PsiMethod[] psiMethods = psiClass.findMethodsByName(methodName, true);

    // get rid of the parentheses and split at comma,
    // e.g. "(java.lang.String, void)" -> [java.lang.String, void]
    String[] params = parameterList.substring(1, parameterList.length() - 1).split(",");

    for (PsiMethod psiMethod : psiMethods) {
      PsiParameter[] psiParameters = psiMethod.getParameterList().getParameters();

      if (isSameParameterList(params, psiParameters)) {
        return psiMethod;
      }
    }

    // may come here when (e.g.) parameter list is empty
    return psiMethods.length > 0 ? psiMethods[0] : null;
  }

  /**
   * TODO: what about varargs and generics ?
   *
   * @param ourParams the parameter list that is found in our JavaByteCode-Editor
   * @param theirParams the parameter list of an overload of the reference we're looking for
   * @return true, if the parameter lists match
   */
  private boolean isSameParameterList(
      @NotNull String[] ourParams, @NotNull PsiParameter[] theirParams) {
    if (ourParams.length != theirParams.length) {
      return false;
    }

    for (int i = 0; i < theirParams.length; ++i) {
      if (!ourParams[i].equals(theirParams[i].getType().getCanonicalText())) {
        return false;
      }
    }

    return true;
  }
}
