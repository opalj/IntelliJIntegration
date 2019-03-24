package syntaxHighlighting.psi.impl;

import Editors.FileEditor.TacTextEditor;
import Editors.disEditor.DisTextEditor;
import com.intellij.ide.util.PsiNavigationSupport;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ColoredItemPresentation;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.util.Iconable;
import com.intellij.pom.Navigatable;
import com.intellij.psi.*;
//import syntaxHighlighting.SimpleIcons;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import org.jetbrains.annotations.NotNull;
import syntaxHighlighting.*;
import syntaxHighlighting.psi.*;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TACPsiImplUtil {
    @Nullable
    public static String getJavaTypeString(@NotNull TACJType element) {
        ASTNode javaTypeNode = element.getNode();
        if (javaTypeNode != null) {
            // IMPORTANT: Convert embedded escaped spaces to simple spaces
            return javaTypeNode.getText().replaceAll("\\\\ ", " ");
        }

        return null;
    }

    /**
     * @param element an invoked method, e.g. println in java.io.PrintStream { void println() }
     * @return the name of the method as a String (e.g. "println")
     */
    @Nullable
    public static String getMethodName(@NotNull TACJMethodHead element) {
        ASTNode stringVarNode = element.getNode().findChildByType(TAC_elementTypeHolder.J_METHOD_HEAD);
        if (stringVarNode != null) {
            // IMPORTANT: Convert embedded escaped spaces to simple spaces
            return stringVarNode.getText().replaceAll("\\\\", " ");
        }

        return null;
    }

    public static String getName(TACJType element) {
        return getJavaTypeString(element);
    }

    public static String getName(TACJMethodHead element) {
        return getMethodName(element);
    }

    /** @see PsiNamedElement#setName */
    public static PsiElement setName(@NotNull TACJType element, String newName) {
        ASTNode keyNode = element.getNode();
        if (keyNode != null) {
            TACJType type = TAC_elementFactory.createType(element.getProject(), newName);
            ASTNode newKeyNode = type.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }

    /** @see PsiNamedElement#setName */
    public static PsiElement setName(@NotNull TACJMethodHead element, String newName) {
        ASTNode keyNode = element.getNode().findChildByType(TAC_elementTypeHolder.J_METHOD_HEAD);
        if (keyNode != null) {
            TACJMethodHead property =
                    TAC_elementFactory.createMethodName(element.getProject(), newName);
            ASTNode newKeyNode = property.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }


    /*public static PsiElement setName(
            @NotNull JavaByteCodeLocVarTableDeclaration element, String newName) {
        ASTNode keyNode = element.getLocVarTableHead().getNode();
        if (keyNode != null) {
            // SEE ABOVE
        }
        return element;
    }*/

    /*public static PsiElement setName(@NotNull JavaByteCodeMethodDeclaration element, String newName) {
        ASTNode keyNode = element.getMethodHead().getNode();
        if (keyNode != null) {
            // SEE ABOVE
        }
        return element;
    }*/
    /** @see PsiNameIdentifierOwner#getNameIdentifier() */
    @Nullable
    public static PsiElement getNameIdentifier(@NotNull TACJType element) {
        ASTNode keyNode = element.getNode();
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    /** @see PsiNameIdentifierOwner#getNameIdentifier() */
    @Nullable
    public static PsiElement getNameIdentifier(@NotNull TACJMethodHead element) {
        ASTNode keyNode = element.getNode().findChildByType(TAC_elementTypeHolder.J_METHOD_HEAD);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    /** @see PsiNameIdentifierOwner#getNameIdentifier() */

    /*public static PsiElement getNameIdentifier(@NotNull JavaByteCodeLocVarTableDeclaration element) {
        ASTNode keyNode = element.getLocVarTableHead().getNode();
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }*/
    /**
     * @see PsiElement#getReferences() for further details
     * @see com.intellij.psi.PsiReferenceRegistrar for further details
     * @param element a class type (as FQN, e.g. java.lang.String)
     * @return the references to each element in the FQN (e.g. ["java", "lang", "String"])
     */
    public static PsiReference[] getReferences(TACJType element) {
        JavaClassReferenceProvider provider = new JavaClassReferenceProvider();
        try {
            return provider.getReferencesByString(element.getName(), element, 0);
        } catch (Exception e) {
            return element.getReferences();
        }
    }

    /**
     * @see PsiElement#getReferences() for further details
     * @param element an invoked method in our JavaByteCode-Editor
     * @return the Java-references to this method
     */
    @NotNull
    public static PsiReference[] getReferences(TACJMethodHead element) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(element);
    }


    /** @see PsiNameIdentifierOwner#getNameIdentifier() */
    /*public static PsiElement getNameIdentifier(@NotNull JavaByteCodeMethodDeclaration element) {
        ASTNode keyNode = element.getMethodHead().getNode();
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }*/

    public static void navigate(TACJMethodHead element, boolean requestFocus) {
        navigate((TAC_namedElement) element, requestFocus);
    }

    public static void navigate(TAC_namedElement element, boolean requestFocus) {
        Navigatable descriptor = PsiNavigationSupport.getInstance().getDescriptor(element);
        FileEditor editor = FileEditorManager.getInstance(element.getProject()).getSelectedEditor();
        if (editor instanceof TacTextEditor) {
            ((TacTextEditor) editor).navigateTo(descriptor);
        } else {
            ((Navigatable) element).navigate(requestFocus);
        }
    }
    public static ItemPresentation getPresentation(TACJMethodHead element) {
        ColoredItemPresentation coloredItemPresentation =
                new ColoredItemPresentation() {
                    private final TACJMethodHead methodHead = element;

                    @Nullable
                    @Override
                    public String getPresentableText() {
                        return methodHead.getJMethodName().getText() + ":" + methodHead.getJReturnValue().getText();
                    }

                    @Nullable
                    @Override
                    public String getLocationString() {
                        return null;
                    }

                    @Nullable
                    @Override
                    public Icon getIcon(boolean unused) {
                        int flags = Iconable.ICON_FLAG_READ_STATUS | Iconable.ICON_FLAG_VISIBILITY;
                        try {
                            String helpinger =
                                    methodHead.getJModifier().getText().length() == 0
                                            ? methodHead.getJReturnValue().getText()
                                            : methodHead.getJModifier().getText() + " " + methodHead.getJReturnValue().getText();
                            Icon icon =
                                    PsiElementFactory.SERVICE
                                            .getInstance(element.getProject())
                                            .createMethodFromText(helpinger + " method()", null)
                                            .getIcon(flags);
                            return icon;
                        } catch (Exception e) {
                            return null;
                        }
                    }

                    @Nullable
                    @Override
                    public TextAttributesKey getTextAttributesKey() {
                        return null;
                    }
                };
        return coloredItemPresentation;
    }

    /*public static ItemPresentation getPresentation(JavaByteCodeLocVarTableDeclaration element) {
        ColoredItemPresentation coloredItemPresentation =
                new ColoredItemPresentation() {
                    @Nullable
                    @Override
                    public String getPresentableText() {
                        return element.getName();
                    }

                    @Nullable
                    @Override
                    public String getLocationString() {
                        return null;
                    }

                    @Nullable
                    @Override
                    public Icon getIcon(boolean unused) {
                        return null;
                    }

                    @Nullable
                    @Override
                    public TextAttributesKey getTextAttributesKey() {
                        return null;
                    }
                };
        return coloredItemPresentation;
    }

    public static ItemPresentation getPresentation(JavaByteCodeMethodDeclaration element) {
        ColoredItemPresentation coloredItemPresentation =
                new ColoredItemPresentation() {
                    private final JavaByteCodeMethodHead methodHead = element.getMethodHead();

                    @Nullable
                    @Override
                    public String getPresentableText() {
                        return methodHead.getMethodName().getText() + ":" + methodHead.getType().getText();
                    }

                    @Nullable
                    @Override
                    public String getLocationString() {
                        return null;
                    }

                    @Nullable
                    @Override
                    public Icon getIcon(boolean unused) {
                        int flags = Iconable.ICON_FLAG_READ_STATUS | Iconable.ICON_FLAG_VISIBILITY;
                        try {
                            String helpinger =
                                    methodHead.getModifierV().getText().length() == 0
                                            ? methodHead.getType().getText()
                                            : methodHead.getModifierV().getText() + " " + methodHead.getType().getText();
                            Icon icon =
                                    PsiElementFactory.SERVICE
                                            .getInstance(element.getProject())
                                            .createMethodFromText(helpinger + " method()", null)
                                            .getIcon(flags);
                            return icon;
                        } catch (Exception e) {
                            return null;
                        }
                    }

                    @Nullable
                    @Override
                    public TextAttributesKey getTextAttributesKey() {
                        return null;
                    }
                };
        return coloredItemPresentation;
    }*/
    /*public static void navigate(JavaByteCodeMethodDeclaration element, boolean requestFocus) {
        navigate((JavaByteCodeNamedElement) element, requestFocus);
    }

    public static void navigate(JavaByteCodeLocVarTableDeclaration element, boolean requestFocus) {
        navigate((JavaByteCodeNamedElement) element, requestFocus);
    }

    public static void navigate(JavaByteCodeNamedElement element, boolean requestFocus) {
        Navigatable descriptor = PsiNavigationSupport.getInstance().getDescriptor(element);
        FileEditor editor = FileEditorManager.getInstance(element.getProject()).getSelectedEditor();
        if (editor instanceof DisTextEditor) {
            ((DisTextEditor) editor).navigateTo(descriptor);
        } else {
            ((Navigatable) element).navigate(requestFocus);
        }
    }*/
}