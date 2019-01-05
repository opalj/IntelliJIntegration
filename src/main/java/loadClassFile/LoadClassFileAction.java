package loadClassFile;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import opalintegration.Opal;

import javax.swing.*;
import java.io.File;

public class LoadClassFileAction extends AnAction {

    public LoadClassFileAction() {
        // Set the menu item name.
        super("Load Class-file into bytecode disassembler");
    }

    public static final void openClassFile(Project project, VirtualFile classFile) {
        String classFileEnding = classFile.getExtension();
        if (classFileEnding == null || !classFileEnding.toUpperCase().equals("CLASS")) {
            JOptionPane.showMessageDialog(
                    null,
                    "The given file is not a class-file.\nCan only decompile " + "class-files.",
                    "Error",
                    JOptionPane.OK_OPTION);
            return;
        }
        // Save the decompiled code to a file
        File disassembledFile = Opal.prepareTAC(project, classFile);
        // Open the just saved file in an editor
        FileEditorManager.getInstance(project)
                .openFile(LocalFileSystem.getInstance().refreshAndFindFileByIoFile(disassembledFile), true);
    }

    public void actionPerformed(AnActionEvent event) {
        // All files selected in the "Project"-View
        VirtualFile[] virtualFiles = event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        // Load the file
        openClassFile(event.getProject(), virtualFiles[0]);
    }

}
