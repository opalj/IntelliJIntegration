package Editors.FileEditor;

import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorImpl;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * An IntelliJ text editor that is used to display our TAC output.
 *
 * <p>Since our language is based on PSI, we use the PsiAwareTextEditorImpl as base.
 */
public class TacTextEditor extends PsiAwareTextEditorImpl {
  public TacTextEditor(
      @NotNull Project project, @NotNull VirtualFile file, TextEditorProvider provider) {
    super(project, file, provider);
  }

  /** @return The name to be displayed in the tab at the bottom of the editor */
  @Override
  @NotNull
  public String getName() {
    return "TAC";
  }
}
