package FileEditor;

import com.intellij.openapi.fileEditor.FileEditor;
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
        return (fileExtension != null) && (fileExtension.equals("class") || fileExtension.equals("tac"));
    }

    //FileEditorManager.getInstance(project).setSelectedEditor(file,"class");
    //FileEditorManager.getInstance(project).getSelectedEditor
//        FileEditorManager instance = FileEditorManager.getInstance(project);
//        if(file.getExtension().equals(GlobalData.TAC_FILE_ENDING)) {
//            viewer = EditorFactory.getInstance().createEditor(FileDocumentManager.getInstance().getDocument(file));
//            instance.setSelectedEditor(file,"tac");
//        }else {
//            Collection<VirtualFile> allTACFiles = FilenameIndex.getAllFilesByExt(project, GlobalData.TAC_FILE_ENDING);
//            for (VirtualFile tacfile : allTACFiles) {
//                System.out.println(tacfile.getNameWithoutExtension());
//                if (file.getNameWithoutExtension().equals(tacfile.getNameWithoutExtension())) {
//                viewer = EditorFactory.getInstance().createEditor(FileDocumentManager.getInstance().getDocument(tacfile));
//                    instance.setSelectedEditor(tacfile, "class");
//                }
//            }
//        }
//        return instance.getSelectedEditor();
//    }
    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        if (!file.getExtension().equals(GlobalData.TAC_FILE_ENDING)) {
            Collection<VirtualFile> allTACFiles = FilenameIndex.getAllFilesByExt(project, GlobalData.TAC_FILE_ENDING);
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
