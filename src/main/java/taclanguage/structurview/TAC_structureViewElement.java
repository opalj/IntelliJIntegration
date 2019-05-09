/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package taclanguage.structurview;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import taclanguage.autogenerated.psi.TACFieldArea;
import taclanguage.autogenerated.psi.TACFieldsDeclaration;
import taclanguage.autogenerated.psi.TACMethodArea;
import taclanguage.autogenerated.psi.TACMethodDeclaration;
import taclanguage.autogenerated.psi.impl.TACFieldsDeclarationImpl;
import taclanguage.autogenerated.psi.impl.TACMethodDeclarationImpl;

class TAC_structureViewElement implements StructureViewTreeElement, SortableTreeElement {
  private final NavigatablePsiElement element;

  public TAC_structureViewElement(NavigatablePsiElement element) {
    this.element = element;
  }

  @Override
  public Object getValue() {
    return element;
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
    return element.canNavigateToSource();
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
    return element.getPresentation() == null
        ? new PresentationData(element.getText(), null, null, null)
        : element.getPresentation();
  }

  @NotNull
  @Override
  public TreeElement[] getChildren() {
    if (element != null) {
      PsiElement[] jbcElements = PsiTreeUtil.getChildrenOfType(element, PsiElement.class);
      if (jbcElements == null) {
        return EMPTY_ARRAY;
      }
      List<TreeElement> treeElements = new ArrayList<>(jbcElements.length);
      for (PsiElement jbcElement : jbcElements) {
        if (jbcElement instanceof TACFieldArea || jbcElement instanceof TACMethodArea) {
          treeElements.addAll(iterateThroughMethods(jbcElement));
        }
      }
      return treeElements.toArray(new TreeElement[0]);
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
      if (method instanceof TACMethodDeclaration) {
        treeElements.add(new TAC_structureViewElement((TACMethodDeclarationImpl) method));
        continue;
      }
      if (method instanceof TACFieldsDeclaration) {
        treeElements.add(new TAC_structureViewElement((TACFieldsDeclarationImpl) method));
      }
    }

    return treeElements;
  }
}
