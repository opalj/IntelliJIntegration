package syntaxHighlighting;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Icons;
import org.jetbrains.annotations.NotNull;
import syntaxHighlighting.psi.impl.TACMethodHeadImpl;

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
        // TODO create getPresentation for TACJMethodHead in PsiImplUtil
        return presentation != null ? presentation : new PresentationData("presentable", "location",
                Icons.CLASS_ICON, null);
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
                System.out.println(property.getClass().getSimpleName());
                    if (property instanceof TACMethodHead) {
                        treeElements.add(
                                new TAC_structureViewElement(
                                        (TACMethodHeadImpl) property));
                    }
            }

            return treeElements.toArray(new TreeElement[treeElements.size()]);
        } else {
            return EMPTY_ARRAY;
        }
    }


}