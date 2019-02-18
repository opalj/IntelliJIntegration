package JavaByteCodeLanguage.psi;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JbcMethodReference extends PsiReferenceBase<PsiElement> {

    private Project project;
    private String fqn; // the FQN of the class the method belongs to

    private String methodName;
    private String returnType;
    private String parameterList;

    public JbcMethodReference(@NotNull PsiElement element, TextRange textRange, String fqn) {
        super(element, textRange);

        this.project = element.getProject();
        this.fqn = fqn;

        this.methodName = element.getText();
        this.returnType = element.getParent().getParent().getChildren()[1].getText();
        this.parameterList = element.getParent().getLastChild().getText();
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        if(myElement instanceof JavaByteCodeDefMethodName) {
            PsiClass psiClass = JavaPsiFacade.getInstance(project)
                    .findClass(fqn, GlobalSearchScope.allScope(project));
            PsiMethod psiMethod = findAppropriateOverload(psiClass);
            return psiMethod;
        }

        return null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    @NotNull
    @Override
    public PsiElement getElement() {
        return myElement;
    }

    private PsiMethod findAppropriateOverload(PsiClass psiClass) {
        PsiMethod[] psiMethods = psiClass.findMethodsByName(methodName, true);
        System.out.println("I'm here !! " + psiMethods.length);

        if(psiMethods.length == 1) {
            return psiMethods[0];
        }

        // get rid of the parentheses and split at comma, e.g. "(java.lang.String, void)" -> [java.lang.String, void]
        String[] params = parameterList.substring(1, parameterList.length()-1).split(",");

        for(PsiMethod psiMethod : psiMethods) {
            boolean isSameName = methodName.equals(psiMethod.getName());
            boolean isSameRetType = returnType.equals(psiMethod.getReturnType().getCanonicalText());
            boolean isSameParameterList = true;

            // TODO
            boolean isEarlyExit = true;
            PsiParameter[] psiParameters = psiMethod.getParameterList().getParameters();
            for(int i=0; i < psiParameters.length; ++i) {
                System.out.println("Param-compare: " + params[i] + " -- " + psiParameters[i].getType().getCanonicalText()
                    + " ... " + params[i].equals(psiParameters[i].getType().getCanonicalText()));

                if(!params[i].equals(psiParameters[i].getType().getCanonicalText())) {
                    isSameParameterList = false;
                    break;
                }

                if(i == psiParameters.length-1) {
                    isEarlyExit = false;
                }
            }

            if(isSameName && isSameRetType && isSameParameterList && !isEarlyExit) {
                return psiMethod;
            }
        }

        return psiMethods[0];
    }
}
