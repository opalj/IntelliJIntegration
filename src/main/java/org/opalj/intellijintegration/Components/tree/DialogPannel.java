/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.Components.tree;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.ex.FileSystemTreeImpl;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.JarFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.opalj.intellijintegration.Actions.DecompileFromJar;

import static org.opalj.intellijintegration.globalData.GlobalData.TAC_EDITOR_ID;

public class DialogPannel extends JFrame {
  private Project project;
  private JButton durchsuchenButton;
  private JTree tree1;
  private JPanel panel1 = new JPanel();
  private FileSystemTreeImpl fileSystemTree;

  public DialogPannel(@NotNull Project project1, @Nullable VirtualFile virtualFile) {
    this.project = project1;
    // this.panel1.add(durchsuchenButton);
    FileChooserDescriptor fileChooserDescriptor =
        new FileChooserDescriptor(true, false, false, false, true, false);
    fileChooserDescriptor.withFileFilter(
        vf -> Pattern.matches(".*\\.(class|jar|zip)", vf.getName()));
    fileSystemTree = new FileSystemTreeImpl(project, fileChooserDescriptor);
    if (virtualFile != null) {
      fileSystemTree.select(virtualFile, null);
      fileSystemTree.expand(virtualFile, null);
    }
    tree1 = fileSystemTree.getTree();
    JScrollPane scrollable = new JBScrollPane(tree1);
    panel1.add(scrollable);
    this.setContentPane(panel1);
    ActionListener mousebuttonactionlistener =
        e -> {
          FileChooserDescriptor fileChooserDescriptorL =
              new FileChooserDescriptor(false, false, true, false, false, false);
          VirtualFile vfile = FileChooser.chooseFile(fileChooserDescriptorL, project, null);
          VirtualFile jarRootForLocalFile = JarFileSystem.getInstance().getVirtualFileForJar(vfile);
          fileSystemTree.select(jarRootForLocalFile, null);
          fileSystemTree.expand(jarRootForLocalFile, null);
          tree1 = fileSystemTree.getTree();
        };
    durchsuchenButton.addActionListener(mousebuttonactionlistener);
    this.pack();
  }

  public void decompile() {
    VirtualFile selectedFile = fileSystemTree.getSelectedFile();
    // open the file
    DecompileFromJar.openAndDecompile(project, selectedFile, TAC_EDITOR_ID);
  }
}
