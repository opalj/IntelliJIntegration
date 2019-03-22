package JavaByteCodeLanguage.psi.impl;

import Editors.disEditor.DisTextEditor;
import JavaByteCodeLanguage.autoGenerated.psi.*;
import JavaByteCodeLanguage.psi.*;
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
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A utility class that serves as a delegate for our PSI-elements that are automatically generated
 * by the parser.
 *
 * <p>(In the grammar, see JavaByteCode.bnf file, we can define methods for selected tree elements.
 * Since they are generated by the parser, we delegate the implementations to this utility class, so
 * that the definitions aren't lost when re-generating the code.)
 */
public class JavaByteCodePsiImplUtil {
  private static final Logger LOGGER = Logger.getLogger(JavaByteCodePsiImplUtil.class.getName());
  public static void navigate(JavaByteCodeNamedElement element, boolean requestFocus) {
    Navigatable descriptor = PsiNavigationSupport.getInstance().getDescriptor(element);
    FileEditor editor = FileEditorManager.getInstance(element.getProject()).getSelectedEditor();
    if (editor instanceof DisTextEditor && descriptor != null) {
      ((DisTextEditor) editor).navigateTo(descriptor);
    } else {
      ((Navigatable) element).navigate(requestFocus);
    }
  }

  // =============================
  // =========DefMethodName=======
  // =============================

  /**
   * @see PsiElement#getReferences() for details
   * @param element an invoked method in our JavaByteCode-Editor
   * @return the Java-references to this method
   */
  @NotNull
  public static PsiReference[] getReferences(JavaByteCodeDefMethodName element) {
    return ReferenceProvidersRegistry.getReferencesFromProviders(element);
  }

  public static String getName(JavaByteCodeDefMethodName element) {
    return getStringVar(element);
  }

  /**
   * @param element an invoked method, e.g. println in java.io.PrintStream { void println() }
   * @return the name of the method as a String (e.g. "println")
   */
  @Nullable
  public static String getStringVar(@NotNull JavaByteCodeDefMethodName element) {
    ASTNode stringVarNode = element.getNode().findChildByType(JavaByteCodeTypes.STRINGVAR);
    if (stringVarNode != null) {
      // IMPORTANT: Convert embedded escaped spaces to simple spaces
      return stringVarNode.getText().replaceAll("\\\\", " ");
    }

    return null;
  }

  /** @see PsiNamedElement#setName */
  public static PsiElement setName(@NotNull JavaByteCodeDefMethodName element, String newName) {
    ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.STRINGVAR);
    if (keyNode != null) {
      JavaByteCodeMethodName property =
          JavaByteCodeElementFactory.createMethodName(element.getProject(), newName);
      ASTNode newKeyNode = property.getFirstChild().getNode();
      element.getNode().replaceChild(keyNode, newKeyNode);
    }
    return element;
  }

  /** @see PsiNameIdentifierOwner#getNameIdentifier() */
  @Nullable
  public static PsiElement getNameIdentifier(@NotNull JavaByteCodeDefMethodName element) {
    ASTNode keyNode = element.getNode().findChildByType(JavaByteCodeTypes.STRINGVAR);
    if (keyNode != null) {
      return keyNode.getPsi();
    } else {
      return null;
    }
  }

  // =============================
  // =============JType===========
  // =============================

  /**
   * @see PsiElement#getReferences() for details
   * @param element a class type (as FQN, e.g. java.lang.String)
   * @return the references to each element in the FQN (e.g. ["java", "lang", "String"])
   */
  public static PsiReference[] getReferences(JavaByteCodeJType element) {
    JavaClassReferenceProvider provider = new JavaClassReferenceProvider();
    try {
      return provider.getReferencesByString(element.getName(), element, 0);
    } catch (Exception e) {
      // TODO: this can cause StackOverFlowError
      return element.getReferences();
    }
  }

  public static String getName(JavaByteCodeJType element) {
    return getJavaType(element);
  }

  /**
   * @param element a Java type, i.e. a class type (e.g. java.lang.String)
   * @return the String representation of the type
   */
  @Nullable
  public static String getJavaType(@NotNull JavaByteCodeJType element) {
    ASTNode javaTypeNode = element.getNode();
    if (javaTypeNode != null) {
      // IMPORTANT: Convert embedded escaped spaces to simple spaces
      return javaTypeNode.getText().replaceAll("\\\\ ", " ");
    }

    return null;
  }

  /** @see PsiNamedElement#setName */
  public static PsiElement setName(@NotNull JavaByteCodeJType element, String newName) {
    ASTNode keyNode = element.getNode();
    if (keyNode != null) {
      JavaByteCodeType type = JavaByteCodeElementFactory.createType(element.getProject(), newName);
      ASTNode newKeyNode = type.getFirstChild().getNode();
      element.getNode().replaceChild(keyNode, newKeyNode);
    }
    return element;
  }

  /** @see PsiNameIdentifierOwner#getNameIdentifier() */
  @Nullable
  public static PsiElement getNameIdentifier(@NotNull JavaByteCodeJType element) {
    ASTNode keyNode = element.getNode();
    if (keyNode != null) {
      return keyNode.getPsi();
    } else {
      return null;
    }
  }

  // =============================
  // ======MethodDeclaration======
  // =============================

  public static String getName(JavaByteCodeMethodDeclaration element) {
    return element.getMethodHead().getText();
  }

  public static PsiElement setName(@NotNull JavaByteCodeMethodDeclaration element, String newName) {
    ASTNode keyNode = element.getMethodHead().getNode();
    if (keyNode != null) {
      // SEE ABOVE
    }
    return element;
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
                  return PsiElementFactory.SERVICE
                                  .getInstance(element.getProject())
                                  .createMethodFromText(helpinger + " method()", null)
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

  public static void navigate(JavaByteCodeMethodDeclaration element, boolean requestFocus) {
    navigate((JavaByteCodeNamedElement) element, requestFocus);
  }

  // =============================
  // ===LocVarTableDeclaration====
  // =============================

  public static String getName(JavaByteCodeLocVarTableDeclaration element) {
    return element.getTablename().getText();
  }

  public static PsiElement setName(
      @NotNull JavaByteCodeLocVarTableDeclaration element, String newName) {
    ASTNode keyNode = element.getTablename().getNode();
    if (keyNode != null) {
      // SEE ABOVE
    }
    return element;
  }

  /** @see PsiNameIdentifierOwner#getNameIdentifier() */
  public static PsiElement getNameIdentifier(@NotNull JavaByteCodeLocVarTableDeclaration element) {
    ASTNode keyNode = element.getTablename().getNode();
    if (keyNode != null) {
      return keyNode.getPsi();
    } else {
      return null;
    }
  }

  public static ItemPresentation getPresentation(JavaByteCodeLocVarTableDeclaration element) {
    return new ColoredItemPresentation() {
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
  }

  public static void navigate(JavaByteCodeLocVarTableDeclaration element, boolean requestFocus) {
    navigate((JavaByteCodeNamedElement) element, requestFocus);
  }
}
