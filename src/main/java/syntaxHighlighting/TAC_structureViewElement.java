package syntaxHighlighting;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import syntaxHighlighting.psi.TAC_file;
import syntaxHighlighting.psi.impl.TACJMethodHeadImpl;
import syntaxHighlighting.psi.impl.TACJTypeImpl;
import syntaxHighlighting.psi.impl.TACPropertyImpl;
import syntaxHighlighting.psi.impl.TACTypeImpl;

import java.util.ArrayList;
import java.util.List;

public class TAC_structureViewElement implements StructureViewTreeElement, SortableTreeElement {
    private NavigatablePsiElement element;

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
        ItemPresentation presentation = element.getPresentation();
        return presentation != null ? presentation : new PresentationData();
    }

    @Override
    public TreeElement[] getChildren() {
        if (element instanceof PsiElement) {
            PsiElement[] properties =
                    PsiTreeUtil.getChildrenOfType(
                            element, PsiElement.class); // PsiTreeUtil.getChildrenOfType(element,
            // JavaByteCodeMethodDeclaration.class);
            List<TreeElement> treeElements = new ArrayList<>(properties.length);
            for (PsiElement property : properties) {
                if (property instanceof NavigatablePsiElement) {

                    if (property instanceof TACJMethodHead)
                        treeElements.add(
                                new TAC_structureViewElement(
                                        (TACJMethodHeadImpl) property));
                }
            }
            return treeElements.toArray(new TreeElement[treeElements.size()]);
        } else {
            return EMPTY_ARRAY;
        }
    }

    /* @Override
    public TreeElement[] getChildren() {
        if (element instanceof TAC_file) {
            TACProperty[] properties = PsiTreeUtil.getChildrenOfType(element, TACProperty.class);
            List<TreeElement> treeElements = new ArrayList<TreeElement>(properties.length);
            for (TACProperty property : properties) {
                if (property instanceof NavigatablePsiElement)
                    if (property instanceof TACType)
                        treeElements.add(
                                new TAC_structureViewElement((TACTypeImpl) property));
                if (property instanceof NavigatablePsiElement)
                    if (property instanceof TACJMethodHead)
                        treeElements.add(
                                new TAC_structureViewElement((TACJMethodHeadImpl) property));
                if (property instanceof NavigatablePsiElement)
                    if (property instanceof TACJType)
                        treeElements.add(
                                new TAC_structureViewElement((TACJTypeImpl) property));
                treeElements.add(new TAC_structureViewElement((TACPropertyImpl) property));
            }
            return treeElements.toArray(new TreeElement[treeElements.size()]);
        } else {
            return EMPTY_ARRAY;
        }
    } */

}