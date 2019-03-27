package JavaByteCodeLanguage.StructureView;

import Editors.disEditor.DisTextEditor;
import JavaByteCodeLanguage.psi.JavaByteCodeNamedElement;
import com.intellij.ide.util.PsiNavigationSupport;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.pom.Navigatable;

public class NavigationUtil {

    public static void navigate(JavaByteCodeNamedElement element, boolean requestFocus) {
        Navigatable descriptor = PsiNavigationSupport.getInstance().getDescriptor(element);
        FileEditor editor = FileEditorManager.getInstance(element.getProject()).getSelectedEditor();
        if (editor instanceof DisTextEditor && descriptor != null) {
            ((DisTextEditor) editor).navigateTo(descriptor);
        } else {
            ((Navigatable) element).navigate(requestFocus);
        }
    }
}
