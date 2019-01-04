package FileEditor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FilenameIndex;
import globalData.GlobalData;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class TacFileEditor extends TextEditorProvider {
    // TODO: should this class implement "DumbAware" ?
    @NonNls
    private static final String EDITOR_TYPE_ID = "OPAL-TAC";

    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
        // for now restrict to .class files only
        String fileExtension = file.getExtension();
        return (fileExtension != null) && fileExtension.equals("class");// TODO siehe unten || fileExtension.equals("tac"));
    }

    //FileEditorManager.getInstance(project).setSelectedEditor(file,"class");
    //FileEditorManager.getInstance(project).getSelectedEditor
//            viewer = EditorFactory.getInstance().createEditor(FileDocumentManager.getInstance().getDocument(file));

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        //TODO Tac soll auf die Classfile gelinked werden.
        if (FileEditorManager.getInstance(project).isFileOpen(file)) {
            FileEditorManager.getInstance(project).setSelectedEditor(file, "class");
            return FileEditorManager.getInstance(project).getSelectedEditor();
        }
        // ENDDO
        if (!file.getExtension().equals(GlobalData.DISASSEMBLED_FILE_ENDING_TAC)) {
            Collection<VirtualFile> allTACFiles = FilenameIndex.getAllFilesByExt(project, GlobalData.DISASSEMBLED_FILE_ENDING_TAC);
            for (VirtualFile tacfile : allTACFiles) {
                if (file.getNameWithoutExtension().equals(tacfile.getNameWithoutExtension())) {
                    file = tacfile;
                    break;
                }
            }
        }
        return super.createEditor(project, file);
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
        return FileEditorPolicy.PLACE_BEFORE_DEFAULT_EDITOR;
    }
}
