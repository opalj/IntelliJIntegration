package FileEditor;

import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorImpl;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class TacTextEditor extends PsiAwareTextEditorImpl {
  public TacTextEditor(
      @NotNull Project project, @NotNull VirtualFile file, TextEditorProvider provider) {
    super(project, file, provider);
  }

  @Override
  @NotNull
  public String getName() {
    return "TAC";
  }
}
