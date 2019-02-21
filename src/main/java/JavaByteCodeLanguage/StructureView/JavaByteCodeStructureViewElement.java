package JavaByteCodeLanguage.StructureView;

import JavaByteCodeLanguage.psi.JavaByteCodeLocVarTableDeclaration;
import JavaByteCodeLanguage.psi.JavaByteCodeMethodDeclaration;
import JavaByteCodeLanguage.psi.impl.JavaByteCodeLocVarTableDeclarationImpl;
import JavaByteCodeLanguage.psi.impl.JavaByteCodeMethodDeclarationImpl;
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

public class JavaByteCodeStructureViewElement
    implements StructureViewTreeElement, SortableTreeElement {
  private NavigatablePsiElement element;

  public JavaByteCodeStructureViewElement(NavigatablePsiElement element) {
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
    if (element instanceof PsiElement) {
      PsiElement[] properties =
          PsiTreeUtil.getChildrenOfType(
              element,
              PsiElement
                  .class); // PsiTreeUtil.getChildrenOfType(element,
                           // JavaByteCodeMethodDeclaration.class);
      List<TreeElement> treeElements = new ArrayList<>(properties.length);
      for (PsiElement property : properties) {
        if (property instanceof NavigatablePsiElement) {
          if (property instanceof JavaByteCodeMethodDeclaration)
            treeElements.add(
                new JavaByteCodeStructureViewElement((JavaByteCodeMethodDeclarationImpl) property));
          if (property instanceof JavaByteCodeLocVarTableDeclaration)
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
    return false; // element.canNavigateToSource();
  }
}
