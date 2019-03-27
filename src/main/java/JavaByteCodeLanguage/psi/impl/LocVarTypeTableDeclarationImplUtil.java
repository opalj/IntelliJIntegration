package JavaByteCodeLanguage.psi.impl;

import JavaByteCodeLanguage.StructureView.NavigationUtil;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeLocVarTypeTableDeclaration;
import JavaByteCodeLanguage.psi.JavaByteCodeNamedElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ColoredItemPresentation;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class LocVarTypeTableDeclarationImplUtil {

  /** @see NavigationUtil#navigate(JavaByteCodeNamedElement, boolean) */
  public static void navigate(
      JavaByteCodeLocVarTypeTableDeclaration element, boolean requestFocus) {
    NavigationUtil.navigate(element, requestFocus);
  }

  public static String getName(JavaByteCodeLocVarTypeTableDeclaration element) {
    return element.getTablename().getText();
  }

  /** @see PsiNameIdentifierOwner#getNameIdentifier() */
  public static PsiElement getNameIdentifier(
      @NotNull JavaByteCodeLocVarTypeTableDeclaration element) {
    ASTNode keyNode = element.getTablename().getNode();
    if (keyNode != null) {
      return keyNode.getPsi();
    } else {
      return null;
    }
  }

  /**
   * Returns the presentation of the tree element.
   *
   * @see ColoredItemPresentation
   * @return the element presentation.
   */
  public static ItemPresentation getPresentation(JavaByteCodeLocVarTypeTableDeclaration element) {
    return new ColoredItemPresentation() {
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
  }
}
