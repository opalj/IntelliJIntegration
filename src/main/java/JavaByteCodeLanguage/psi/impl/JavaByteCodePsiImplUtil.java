package JavaByteCodeLanguage.psi.impl;

import JavaByteCodeLanguage.psi.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;

public class JavaByteCodePsiImplUtil {

    public static String getKey(JavaByteCodeType element) {
        ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.JAVATYPE);
        if (keyNode != null) {
            // IMPORTANT: Convert embedded escaped spaces to simple spaces
            return keyNode.getText().replaceAll("\\\\ ", " ");
        }

        return null;
    }

    public static String getKey(JavaByteCodeMethodName element) {
        ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.STRINGVAR);
        PsiElement parent = element.getParent();
        if(parent instanceof JavaByteCodeJavaOP) {
            PsiElement type = parent.getFirstChild();
//            System.out.println("Parent = " + parent.getText() + " --- Type = " + type.getText());
//            System.out.println("!" + keyNode.getText() + "!");
            return type.getText() + "." + keyNode.getText().replaceAll("\\\\", " ");
        }

        return null;
    }


    public static String getName(JavaByteCodeType element) {
        return getKey(element);
    }

    public static String getName(JavaByteCodeMethodName element) {
        return getKey(element);
    }


    // TODO do we need this? editor not writable anyway?
    public static PsiElement setName(JavaByteCodeType element, String newName) {
        ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.JAVATYPE);
        if (keyNode != null) {

            JavaByteCodeProperty property = JavaByteCodeElementFactory.createProperty(element.getProject(), newName);
            ASTNode newKeyNode = property.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }

    public static PsiElement setName(JavaByteCodeMethodName element, String newName) {
        ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.JAVATYPE);
        if (keyNode != null) {

            JavaByteCodeProperty property = JavaByteCodeElementFactory.createProperty(element.getProject(), newName);
            ASTNode newKeyNode = property.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }

    public static PsiElement getNameIdentifier(JavaByteCodeType element) {
        ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.JAVATYPE);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    public static PsiElement getNameIdentifier(JavaByteCodeMethodName element) {
        ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.JAVATYPE);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

}