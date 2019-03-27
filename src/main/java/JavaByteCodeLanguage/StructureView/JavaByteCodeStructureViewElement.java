package JavaByteCodeLanguage.StructureView;

import JavaByteCodeLanguage.autoGenerated.psi.*;
import JavaByteCodeLanguage.autoGenerated.psi.impl.*;
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
class JavaByteCodeStructureViewElement implements StructureViewTreeElement, SortableTreeElement {
  private final NavigatablePsiElement element;

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
        if (jbcElement instanceof JavaByteCodeFieldArea) {
          treeElements.addAll(iterateThroughArea(jbcElement));
          continue;
        }

        if (jbcElement instanceof JavaByteCodeMethodArea) {
          treeElements.addAll(iterateThroughArea(jbcElement));
          continue;
        }

        // tables
        if (jbcElement instanceof JavaByteCodeTableArea) {
          treeElements.addAll(iterateThroughArea(jbcElement));
        }
      }
      return treeElements.toArray(new TreeElement[0]);
    } else {
      return EMPTY_ARRAY;
    }
  }

  /**
   * iterates over all elements in the designated area and creates an entry for the respective
   * elements (fields, methods and tables) in the structure view
   *
   * @param area the region which contains all method definitions
   * @return a list of elements which will be shown in the structure view
   */
  private List<TreeElement> iterateThroughArea(@NotNull PsiElement area) {
    List<TreeElement> treeElements = new ArrayList<>();

    for (PsiElement jbcElement : area.getChildren()) {
      if (jbcElement instanceof JavaByteCodeMethodDeclaration) {
        treeElements.add(
            new JavaByteCodeStructureViewElement((JavaByteCodeMethodDeclarationImpl) jbcElement));
        continue;
      }
      if (jbcElement instanceof JavaByteCodeFieldsDeclaration) {
        treeElements.add(
            new JavaByteCodeStructureViewElement((JavaByteCodeFieldsDeclarationImpl) jbcElement));
        continue;
      }

      if (jbcElement instanceof JavaByteCodeLocVarTableDeclaration) {
        treeElements.add(
            new JavaByteCodeStructureViewElement(
                (JavaByteCodeLocVarTableDeclarationImpl) jbcElement));
        continue;
      }

      if (jbcElement instanceof JavaByteCodeExceptionTableDeclaration) {
        treeElements.add(
            new JavaByteCodeStructureViewElement(
                (JavaByteCodeExceptionTableDeclarationImpl) jbcElement));
        continue;
      }

      if (jbcElement instanceof JavaByteCodeStackMapTableDeclaration) {
        treeElements.add(
            new JavaByteCodeStructureViewElement(
                (JavaByteCodeStackMapTableDeclarationImpl) jbcElement));
        continue;
      }

      if (jbcElement instanceof JavaByteCodeLocVarTypeTableDeclaration) {
        treeElements.add(
            new JavaByteCodeStructureViewElement(
                (JavaByteCodeLocVarTypeTableDeclarationImpl) jbcElement));
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
