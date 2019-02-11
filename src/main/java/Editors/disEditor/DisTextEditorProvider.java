package Editors.disEditor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import globalData.GlobalData;
import opalintegration.Opal;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class DisTextEditorProvider extends PsiAwareTextEditorProvider {
  @NonNls private static final String EDITOR_TYPE_ID = "OPAL-DIS";

  @Override
  public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
    // for now restrict to .class files only
    String fileExtension = file.getExtension();
    return (fileExtension != null)
        && (fileExtension.equals("class")
            || fileExtension.equals(GlobalData.DISASSEMBLED_FILE_ENDING_JBC));
  }

  @NotNull
  @Override
  public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
    if (!file.getExtension().equals(GlobalData.DISASSEMBLED_FILE_ENDING_JBC)) {
      file = Opal.DisassemblertoVF(file);
    }
    return new DisTextEditor(project, file, this);
  }

  @NotNull
  @Override
  public String getEditorTypeId() {
    return EDITOR_TYPE_ID;
  }

  @NotNull
  @Override
  public FileEditorPolicy getPolicy() {
    // This keeps the default editor so that one can switch between the two
    return FileEditorPolicy.NONE;
  }
}
