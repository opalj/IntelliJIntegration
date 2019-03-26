package syntaxHighlighting;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @see FoldingBuilderEx for documentation
 *     <p>The FoldingBuilder is registered in the com.intellij.lang.foldingBuilder extension point
 */
public class TACFoldingBuilder extends FoldingBuilderEx {

  private static final List<Class<? extends PsiElement>> psiTypesWithFoldingSupport =
      Arrays.asList(
          TACAttributesArea.class,
          TACInnerTable.class,
          TACFieldArea.class,
          TACMethodArea.class,
          TACMethodDeclaration.class);

  @NotNull
  @Override
  public FoldingDescriptor[] buildFoldRegions(
      @NotNull PsiElement root, @NotNull Document document, boolean quick) {
    // a descriptor defines a single folding region
    List<FoldingDescriptor> descriptors = new ArrayList<>();

    for (Class<? extends PsiElement> psiType : psiTypesWithFoldingSupport) {
      descriptors.addAll(createDescriptors(root, psiType));
    }

    return descriptors.toArray(new FoldingDescriptor[0]);
  }

  @Override
  public boolean isCollapsedByDefault(@NotNull ASTNode node) {
    // collapse everything apart from the methods (hence method area should be open too)
    PsiElement psiElement = node.getPsi();
    return !(psiElement instanceof TACMethodDeclaration
        || psiElement instanceof TACMethodArea);
  }

  @Nullable
  @Override
  public String getPlaceholderText(@NotNull ASTNode node) {
    return "...";
  }

  /**
   * Creates folding descriptors for a given element. Note: the text of the element should have an
   * opening brace '{' (and ideally a matching closing brace)
   *
   * @param root the element for which folding is requested
   * @param clazz the type of element to search for
   * @return a list of FoldingDescriptors for each element of type clazz
   */
  private <T extends PsiElement> List<FoldingDescriptor> createDescriptors(
      PsiElement root, Class<T> clazz) {
    // returns an empty list if nothing is found
    Collection<PsiElement> psiElements = PsiTreeUtil.findChildrenOfType(root, clazz);

    List<FoldingDescriptor> descriptors = new ArrayList<>();
    for (PsiElement psiElement : psiElements) {
      String text = psiElement.getText();
      int firstBrace = text.indexOf('{');

      // add a descriptor that replaces the text in-between the braces with three dots
      descriptors.add(
          new FoldingDescriptor(
              psiElement.getNode(),
              new TextRange(
                  psiElement.getTextRange().getStartOffset() + firstBrace + 1,
                  psiElement.getTextRange().getEndOffset() - 1)) {
            @Override
            public String getPlaceholderText() {
              return "...";
            }
          });
    }

    return descriptors;
  }
}
