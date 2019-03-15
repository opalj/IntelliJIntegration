package syntaxHighlighting.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.*;
//import syntaxHighlighting.SimpleIcons;
import syntaxHighlighting.TACProperty;
import syntaxHighlighting.TAC_elementFactory;
import syntaxHighlighting.TAC_elementTypeHolder;
import syntaxHighlighting.TAC_icons;
import syntaxHighlighting.psi.*;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TACPsiImplUtil {
    public static String getKey(TACProperty element) {
        ASTNode keyNode = element.getNode().findChildByType(TAC_elementTypeHolder.KEY);
        if (keyNode != null) {
            // IMPORTANT: Convert embedded escaped spaces to simple spaces
            return keyNode.getText().replaceAll("\\\\ ", " ");
        } else {
            return null;
        }
    }

    public static String getValue(TACProperty element) {
        ASTNode valueNode = element.getNode().findChildByType(TAC_elementTypeHolder.VALUE);
        if (valueNode != null) {
            return valueNode.getText();
        } else {
            return null;
        }
    }

    public static String getName(TACProperty element) {
        return getKey(element);
    }

    public static PsiElement setName(TACProperty element, String newName) {
        ASTNode keyNode = element.getNode().findChildByType(TAC_elementTypeHolder.KEY);
        if (keyNode != null) {

            TACProperty property = TAC_elementFactory.createProperty(element.getProject(), newName);
            ASTNode newKeyNode = property.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }

    public static PsiElement getNameIdentifier(TACProperty element) {
        ASTNode keyNode = element.getNode().findChildByType(TAC_elementTypeHolder.KEY);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    public static ItemPresentation getPresentation(final TACProperty element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getKey();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return element.getContainingFile().getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return TAC_icons.FILE;
            }
        };
    }
}