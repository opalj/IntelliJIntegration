package JavaByteCodeLanguage.StructureView;

import JavaByteCodeLanguage.LanguageAndFileType.JavaByteCode;
import JavaByteCodeLanguage.psi.*;
import JavaByteCodeLanguage.psi.impl.JavaByteCodeLocVarTableDelerationImpl;
import JavaByteCodeLanguage.psi.impl.JavaByteCodeMethodDeclarationImpl;
import JavaByteCodeLanguage.psi.impl.JavaByteCodeMethodHeadImpl;
import JavaByteCodeLanguage.psi.impl.JavaByteCodeTypeImpl;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.lang.java.JavaStructureViewBuilderFactory;
import com.intellij.navigation.ColoredItemPresentation;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.presentation.java.JavaPresentationUtil;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.ui.SimpleTextAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class JavaByteCodeStructureViewElement implements StructureViewTreeElement, SortableTreeElement {
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
    private int i = 0;
    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        ItemPresentation presentation = element.getPresentation();
        ColoredItemPresentation coloredItemPresentation = new ColoredItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getName();
                //PsiMethod[] allMethods = JavaPsiFacade.getInstance(element.getProject()).findClass(FilenameIndex.getFilesByName(element.getProject(),GlobalSearchScope.allScope(element.getProject()))).
            }

            @Nullable
            @Override
            public String getLocationString() {
                return null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
//                if(element instanceof  JavaByteCodeMethodDeclaration)
//                    ((JavaByteCodeMethodDeclaration) element).getMethodHead()
                int flags = Iconable.ICON_FLAG_READ_STATUS | Iconable.ICON_FLAG_VISIBILITY;
                //Icon test = PsiElementFactory.SERVICE.getInstance(element.getProject()).createMethod("privatetest", PsiType.getTypeByName()).getIcon(flags);
                return null;
            }
            @Nullable
            @Override
            public TextAttributesKey getTextAttributesKey() {
                return null;
            }
        };
        //presentationData.addText(element.getName(), SimpleTextAttributes.GRAYED_BOLD_ATTRIBUTES);
       // return presentation != null ? presentation : coloredItemPresentation;
        return coloredItemPresentation;
    }

    @NotNull
    @Override
    public TreeElement[] getChildren() {
        if (element instanceof PsiElement) {
            PsiElement[] properties = PsiTreeUtil.getChildrenOfType(element,PsiElement.class);//PsiTreeUtil.getChildrenOfType(element, JavaByteCodeMethodDeclaration.class);
            List<TreeElement> treeElements = new ArrayList<>(properties.length);
            for (PsiElement property : properties) {
                if(property instanceof  NavigatablePsiElement) {
                    if(property instanceof  JavaByteCodeMethodDeclaration)
                    treeElements.add(new JavaByteCodeStructureViewElement((JavaByteCodeMethodDeclarationImpl) property));
                    if(property instanceof JavaByteCodeLocVarTableDeleration)
                    treeElements.add(new JavaByteCodeStructureViewElement((JavaByteCodeLocVarTableDelerationImpl) property));
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
        return element.canNavigateToSource();
    }
}
