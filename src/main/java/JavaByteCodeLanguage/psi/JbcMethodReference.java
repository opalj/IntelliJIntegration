package JavaByteCodeLanguage.psi;

import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeDefMethodName;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeParams;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * TODO: improve name (?), as it covers both member/non-member methods and fields
 *
 * <p>a class that resolves references for methods and fields in the JavaByteCode-Editor (e.g. for
 * println(java.lang.String) it finds the println(String) method in PrintStream.java)
 *
 * <p>the methods/fields may or may not be members of the current class
 */
public class JbcMethodReference extends PsiReferenceBase<PsiElement> {

  // the FQN of the class the method belongs to
  private String fqn;
  // the name of method or field we're looking for
  private String memberName;
  private boolean isConstructor;

  private static final Logger LOGGER = Logger.getLogger(JbcMethodReference.class.getName());

  JbcMethodReference(@NotNull PsiElement element, TextRange textRange, String fqn) {
    super(element, textRange);

    this.fqn = fqnIsPrimitiveArray(fqn) ? "java.lang.Object" : fqn;

    // myElement set in super()
    memberName = myElement.getText();

    // <init> is the constructor
    if (memberName.equals("<init>")) {
      String simpleClassName = this.fqn.substring(this.fqn.lastIndexOf('.') + 1);
      memberName = simpleClassName;
      isConstructor = true;
    }
  }

  // arrays of primitive type (e.g. int[] or float[][]) are also objects
  private boolean fqnIsPrimitiveArray(@NotNull String fqn) {
    // everything between \Q (quote) and \E (end) is considered to be escaped
    return fqn.matches("(boolean|byte|char|short|int|long|float|double)(\\Q[]\\E)+");
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    if (!(myElement instanceof JavaByteCodeDefMethodName)) {
      // this should not happen (@see JbcReferenceContributor), so log it if it does
      LOGGER.log(
          Level.WARNING,
          "JbcMethodReference has been called for a type that is"
              + " not an instance of JavaByteCodeDefMethodName");
      return null;
    }

    final Project project = myElement.getProject();
    PsiClass psiClass =
        JavaPsiFacade.getInstance(project).findClass(fqn, GlobalSearchScope.allScope(project));
    PsiMethod psiMethod = (psiClass != null) ? findAppropriateOverload(psiClass) : null;

    if (psiClass != null && psiMethod == null) {
      // it might be an implicit (standard) constructor
      if (isConstructor) {
        return psiClass;
      }
      // if it's not a method, it might be a field
      else {
        return psiClass.findFieldByName(memberName, true);
      }
    }

    return psiMethod;
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
    String methodName = memberName;
    String parameterList =
        Arrays.stream(myElement.getParent().getChildren())
            .filter(psiElement -> psiElement instanceof JavaByteCodeParams)
            .map(PsiElement::getText)
            .collect(Collectors.joining());

    // no parentheses -> not a method
    if (!parameterList.contains("(")) {
      return null;
    }

    // this guarantees that the name matches, if the method exists in the class
    PsiMethod[] psiMethods = psiClass.findMethodsByName(methodName, true);

    // get rid of the parentheses and split at comma,
    // e.g. "(java.lang.String,int)" -> [java.lang.String,int]
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
   * TODO: what about varargs and generics ? ... seems to work just fine?
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
