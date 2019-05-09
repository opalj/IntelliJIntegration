/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package JavaByteCodeLanguage.psi.impl;

import JavaByteCodeLanguage.StructureView.NavigationUtil;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeMethodDeclaration;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeMethodHead;
import JavaByteCodeLanguage.psi.JavaByteCodeNamedElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ColoredItemPresentation;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiNameIdentifierOwner;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class MethodDeclarationImplUtil {

  private static final Logger LOGGER = Logger.getLogger(JavaByteCodePsiImplUtil.class.getName());

  /** @see NavigationUtil#navigate(JavaByteCodeNamedElement, boolean) */
  public static void navigate(JavaByteCodeMethodDeclaration element, boolean requestFocus) {
    NavigationUtil.navigate(element, requestFocus);
  }

  public static String getName(JavaByteCodeMethodDeclaration element) {
    return element.getMethodHead().getText();
  }

  /** @see PsiNameIdentifierOwner#getNameIdentifier() */
  public static PsiElement getNameIdentifier(@NotNull JavaByteCodeMethodDeclaration element) {
    ASTNode keyNode = element.getMethodHead().getNode();
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
  public static ItemPresentation getPresentation(JavaByteCodeMethodDeclaration element) {
    return new ColoredItemPresentation() {
      private final JavaByteCodeMethodHead methodHead = element.getMethodHead();

      @Override
      public String getPresentableText() {
        return methodHead.getMethodName().getText().replaceAll("throws .*", "")
            + ": "
            + methodHead.getType().getText();
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
              Objects.requireNonNull(methodHead.getModifierV()).getText().length() == 0
                  ? methodHead.getType().getText()
                  : methodHead.getModifierV().getText() + " " + methodHead.getType().getText();
          String prehelpinger =
              element
                  .getAnnotationList()
                  .stream()
                  .map(PsiElement::getText)
                  .collect(Collectors.joining(" "));
          helpinger = prehelpinger.length() > 0 ? prehelpinger + " " + helpinger : helpinger;
          return PsiElementFactory.SERVICE
              .getInstance(element.getProject())
              .createMethodFromText(helpinger + " method()", null)
              .getIcon(flags);
        } catch (Exception e) {
          // just a warning because a not found methodicon dont mess up the structview
          LOGGER.log(Level.WARNING, e.toString(), e);
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
