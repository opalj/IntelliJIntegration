package Actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import javax.swing.*;

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
    // Open class file with respect to TAC ending
    FileEditorManager.getInstance(project).openFile(classFile, true);
    FileEditorManager.getInstance(project).setSelectedEditor(classFile, "OPAL-TAC");
  }

  public void actionPerformed(AnActionEvent event) {
    // All files selected in the "Project"-View
    VirtualFile[] virtualFiles = event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
    // Load the file
    openClassFile(event.getProject(), virtualFiles[0]);
  }

  @Override
  public void update(AnActionEvent e) {
    final Project project = e.getProject();
    final VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
    // show Action only for java files
    e.getPresentation()
        .setEnabledAndVisible(
            // YODA to prevent NullPointerException of getExtension()
            project != null && virtualFile != null && "class".equals(virtualFile.getExtension()));
  }
}
