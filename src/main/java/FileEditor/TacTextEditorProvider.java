package FileEditor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import globalData.GlobalData;
import opalintegration.Opal;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class TacTextEditorProvider extends PsiAwareTextEditorProvider {
  @NonNls private static final String EDITOR_TYPE_ID = "OPAL-TAC";

  @Override
  public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
    // for now restrict to .class files only
    String fileExtension = file.getExtension();
    return (fileExtension != null)
        && (fileExtension.equals("class") || fileExtension.equals("tac"));
  }

  // FileEditorManager.getInstance(project).setSelectedEditor(file,"class");
  // FileEditorManager.getInstance(project).getSelectedEditor
  //            viewer =
  // EditorFactory.getInstance().createEditor(FileDocumentManager.getInstance().getDocument(file));
  // if (FileEditorManager.getInstance(project).isFileOpen(file))

  @NotNull
  @Override
  public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
    // TODO Tac soll auf die Classfile gelinked werden.
    // not best impl
    //        FileEditor[] allEditors = FileEditorManager.getInstance(project).getAllEditors();
    //        for(FileEditor editor : allEditors){
    //            if(editor.getFile().equals(file))
    //                return  editor;
    //        }
    if (!file.getExtension().equals(GlobalData.DISASSEMBLED_FILE_ENDING_TAC)) {
      // Eventuell eine eigene EditorKlasse?
      file = Opal.prepareTAC(project, file);
      //            Collection<VirtualFile> allTACFiles = FilenameIndex.getAllFilesByExt(project,
      // GlobalData.DISASSEMBLED_FILE_ENDING_TAC);
      //            for (VirtualFile tacfile : allTACFiles) {
      //                if
      // (file.getNameWithoutExtension().equals(tacfile.getNameWithoutExtension())) {
      //                    return super.createEditor(project, tacfile);
      //                }
      //                }
    }
    // return super.createEditor(project, file);
    return new TacTextEditor(project, file, this);
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
