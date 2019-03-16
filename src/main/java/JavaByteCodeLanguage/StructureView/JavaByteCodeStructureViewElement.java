package JavaByteCodeLanguage.StructureView;

import JavaByteCodeLanguage.autoGenerated.psi.*;
import JavaByteCodeLanguage.autoGenerated.psi.impl.JavaByteCodeLocVarTableDeclarationImpl;
import JavaByteCodeLanguage.autoGenerated.psi.impl.JavaByteCodeMethodDeclarationImpl;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * @see StructureViewTreeElement
 * @see SortableTreeElement
 */
public class JavaByteCodeStructureViewElement
    implements StructureViewTreeElement, SortableTreeElement {
  private NavigatablePsiElement element;

  JavaByteCodeStructureViewElement(NavigatablePsiElement element) {
    this.element = element;
  }

  @Override
  public Object getValue() {
    return element;
  }

  @NotNull
  @Override
  public String getAlphaSortKey() {
    String name = element.getName();
    return name != null ? name : "";
  }

  @NotNull
  @Override
  public ItemPresentation getPresentation() {
    return element.getPresentation() == null ? new PresentationData() : element.getPresentation();
  }

  @NotNull
  @Override
  public TreeElement[] getChildren() {
    if (element != null) {
      PsiElement[] properties = PsiTreeUtil.getChildrenOfType(element, PsiElement.class);
      List<TreeElement> treeElements = new ArrayList<>(properties.length);
      for (PsiElement property : properties) {
        if (property instanceof JavaByteCodeFieldsDeclaration) {
          // TODO: iterate over fields
          continue;
        }

        if (property instanceof JavaByteCodeMethodArea) {
          treeElements.addAll(iterateThroughMethods(property));
          continue;
        }

        // tables
        if (property instanceof JavaByteCodeLocVarTableDeclaration) {
          treeElements.add(
              new JavaByteCodeStructureViewElement(
                  (JavaByteCodeLocVarTableDeclarationImpl) property));
        }
      }
      return treeElements.toArray(new TreeElement[treeElements.size()]);
    } else {
      return EMPTY_ARRAY;
    }
  }

  /**
   * iterates over all methods in the designated method area and creates an entry for the respective
   * elements (methods and tables) in the structure view
   *
   * @param methodArea the region which contains all method definitions
   * @return a list of elements which will be shown in the structure view
   */
  private List<TreeElement> iterateThroughMethods(@NotNull PsiElement methodArea) {
    List<TreeElement> treeElements = new ArrayList<>();

    for (PsiElement method : methodArea.getChildren()) {
      if (method instanceof JavaByteCodeMethodDeclaration) {
        treeElements.add(
            new JavaByteCodeStructureViewElement((JavaByteCodeMethodDeclarationImpl) method));
      }
    }

    return treeElements;
  }

  @Override
  public void navigate(boolean requestFocus) {
    element.navigate(requestFocus);
  }

  @Override
  public boolean canNavigate() {
    return element.canNavigate();
  }

  @Override
  public boolean canNavigateToSource() {
    return true; // element.canNavigateToSource();
  }
}
