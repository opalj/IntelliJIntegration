/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.Actions;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import java.util.regex.Pattern;

public class DecompileFromJar {

  public static void openDialog(Project project, VirtualFile virtualFile, String editorName) {
    FileChooserDescriptor fileChooserDescriptor =
        new FileChooserDescriptor(true, false, false, false, true, false);
    fileChooserDescriptor.withFileFilter(
        vf -> Pattern.matches(".*\\.(class|jar|zip)", vf.getName()));
    FileChooser.chooseFile(
        fileChooserDescriptor,
        project,
        virtualFile,
        vf -> openAndDecompile(project, vf, editorName));
  }

  public static void openAndDecompile(
      Project project, VirtualFile selectedFile, String editorName) {
    if (selectedFile.getName().toUpperCase().endsWith(".CLASS")) {
      FileEditorManager.getInstance(project).openFile(selectedFile, true);
      FileEditorManager.getInstance(project).setSelectedEditor(selectedFile, editorName);
    }
  }
}
