package JavaByteCodeLanguage.psi.impl;

import JavaByteCodeLanguage.LanguageAndFileType.JavaByteCode;
import JavaByteCodeLanguage.psi.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.impl.SharedPsiElementImplUtil;
import com.intellij.psi.impl.source.resolve.ClassResolverProcessor;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaByteCodePsiImplUtil {

    // TODO rename to getJavaType
    public static String getJavaType(JavaByteCodeJType element) {
        ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.JAVATYPE);
        if (keyNode != null) {
            // IMPORTANT: Convert embedded escaped spaces to simple spaces
            return keyNode.getText().replaceAll("\\\\ ", " ");
        }

        return null;
    }

    // TODO rename to getStringVar
    public static String getStringVar(JavaByteCodeDefMethodName element) {
        ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.STRINGVAR);
        PsiElement parent = element.getParent().getParent();
        if(parent instanceof JavaByteCodeJavaOP) {
            PsiElement type = parent.getFirstChild();
//            System.out.println("Parent = " + parent.getText() + " --- Type = " + type.getText());
//            System.out.println("!" + keyNode.getText() + "!");
            return type.getText() + "." + keyNode.getText().replaceAll("\\\\", " ");
        }

        return null;
    }


    public static String getName(JavaByteCodeJType element) {
        return getJavaType(element);
    }

    public static String getName(JavaByteCodeDefMethodName element) {
        return getStringVar(element);
    }


    public static PsiElement setName(JavaByteCodeJType element, String newName) {
        ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.JAVATYPE);
        if (keyNode != null) {
            JavaByteCodeType type = JavaByteCodeElementFactory.createType(element.getProject(), "xyz");
            ASTNode newKeyNode = type.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }

    public static PsiElement setName(JavaByteCodeDefMethodName element, String newName) {
        ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.STRINGVAR);
        if (keyNode != null) {
            JavaByteCodeMethodName property = JavaByteCodeElementFactory.createMethodName(element.getProject(), newName);
            ASTNode newKeyNode = property.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }

    public static PsiElement getNameIdentifier(JavaByteCodeJType element) {
        ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.JAVATYPE);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    public static PsiElement getNameIdentifier(JavaByteCodeDefMethodName element) {
        ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.STRINGVAR);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }


    public static PsiReference[] getReferences(JavaByteCodeJType element) {
        JavaClassReferenceProvider provider = new JavaClassReferenceProvider();
        try {
            System.out.println("Name = " + element.getName());
            System.out.println("NameId = " + element.getNameIdentifier().getText());
            System.out.println("==================");
            if(element.getName().contains("Print")) {
                return provider.getReferencesByString(element.getName(), element, 0);
            }
            return provider.getReferencesByString(element.getName(), element, 0);
        } catch(Exception e) {
            return element.getReferences();
        }
    }

    public static PsiReference[] getReferences(JavaByteCodeDefMethodName element) {
        PsiElementResolveResult psiElementResolveResult;
        ClassResolverProcessor classResolverProcessor;
        PsiReferenceContributor psiReferenceContributor;
        PsiLiteralExpression psiLiteralExpression;

        /*
         * To locate the Java method, use JavaPsiFacade.findClass() to find a class by its qualified name
         * and then PsiClass.findMethodsByName() to locate a specific method.
         */
        JavaPsiFacade javaPsiFacade;
        PsiClass psiClass;

        return ReferenceProvidersRegistry.getReferencesFromProviders(element);

//        JavaClassReferenceProvider provider = new JavaClassReferenceProvider();
//        try {
//            if(element.getName() != null) System.out.println(element.getName());
//            int offset = element.getName().lastIndexOf('.');
//            String name = element.getName();
//            if(name.contains("println")) {
//                PsiReference[] references = provider.getReferencesByString(name, element, 0);
//                return references;
//            }
//            return provider.getReferencesByString(element.getName(), element, offset);
//        } catch(Exception e) {
//            return element.getReferences();
//        }
    }
}