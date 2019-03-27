package JavaByteCodeLanguage.psi.impl;

import JavaByteCodeLanguage.StructureView.NavigationUtil;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeFieldsDeclaration;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeLocVarTableDeclaration;
import JavaByteCodeLanguage.psi.JavaByteCodeNamedElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ColoredItemPresentation;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FieldsDeclarationImplUtil {

    private static final Logger LOGGER = Logger.getLogger(JavaByteCodePsiImplUtil.class.getName());

    public static void navigate(JavaByteCodeFieldsDeclaration element, boolean requestFocus) {
        NavigationUtil.navigate(element, requestFocus);
    }

    public static String getName(JavaByteCodeFieldsDeclaration element) {
        return element.getDefMethodName().getText();
    }

    public static PsiElement getNameIdentifier(@NotNull JavaByteCodeFieldsDeclaration element) {
        ASTNode keyNode = element.getDefMethodName().getNode();
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    public static ItemPresentation getPresentation(JavaByteCodeFieldsDeclaration element) {
        return new ColoredItemPresentation() {
            @Override
            public String getPresentableText() {
                return element.getDefMethodName().getText()+" : "+element.getType().getText();
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
                    return PsiElementFactory.SERVICE
                            .getInstance(element.getProject())
                            .createFieldFromText(element.getText(), null)
                            .getIcon(flags);
                } catch (Exception e) {
                    // just a warning because a not found methodicon dont mess up the structview
                    LOGGER.log(Level.WARNING,e.toString(),e);
                    return null;
                }
            }
            @Nullable
            @Override
            public TextAttributesKey getTextAttributesKey() {
                return null;
            }
        };
    }
}
