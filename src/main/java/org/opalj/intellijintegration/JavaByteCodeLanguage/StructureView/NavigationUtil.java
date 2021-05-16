/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.JavaByteCodeLanguage.StructureView;

import com.intellij.ide.util.PsiNavigationSupport;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.NavigatableFileEditor;
import com.intellij.pom.Navigatable;
import org.opalj.intellijintegration.Editors.disEditor.DisTextEditor;
import org.opalj.intellijintegration.JavaByteCodeNamedElement;

/** A utility class that is used to help with navigation through the structure view. */
public class NavigationUtil {

  /**
   * @see NavigatableFileEditor#navigateTo(Navigatable) The NavigatableFileEditor is needed because
   *     the underlying file is a .jbc file, which does not correspond to the editor of the .class
   *     file
   * @see Navigatable#navigate(boolean)
   * @param element The element to navigate to
   * @param requestFocus {@code true} if focus requesting is necessary
   */
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
