/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.Components.list;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.*;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DialogPannel extends JFrame {
  private final DefaultListModel<JarEntry> dlm;
  private JPanel panel1;
  private JScrollPane scrollable;
  private JList jlist;
  private JButton durchsuchenButton;
  private JTree tree1;
  private int lastIndex;
  private File file;
  private Project project;

  public DialogPannel(@NotNull Project project1, @Nullable VirtualFile virtualFile) {
    this.project = project1;
    this.panel1.add(durchsuchenButton);
    dlm = new DefaultListModel<>();
    if (virtualFile != null) {
      file = new File(virtualFile.getPath());
      addList(file);
    }
    jlist = new JBList(dlm);
    scrollable = new JBScrollPane(jlist);
    panel1.add(scrollable);
    jlist.getSelectionModel().addListSelectionListener(e -> lastIndex = e.getLastIndex());
    this.setContentPane(panel1);
    ActionListener mousebuttonactionlistener =
        e -> {
          JFileChooser jFileChooser = new JFileChooser();
          jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
          jFileChooser.setFileFilter(
              new FileFilter() {
                @Override
                public boolean accept(File f) {
                  return f.isDirectory() || f.getName().toUpperCase().endsWith(".JAR");
                }

                @Override
                public String getDescription() {
                  return null;
                }
              });
          jFileChooser.showOpenDialog(null);
          file = jFileChooser.getSelectedFile();
          addList(jFileChooser.getSelectedFile());
        };
    durchsuchenButton.addActionListener(mousebuttonactionlistener);
  }

  private void addList(File file) {
    dlm.removeAllElements();
    try {
      JarFile jarFile = new JarFile(file);
      List<JarEntry> list = Collections.list(jarFile.entries());
      list.stream()
          .filter(entry -> entry.getName().toUpperCase().endsWith(".CLASS"))
          .forEach(dlm::addElement);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.pack();
  }

  public void decompile() {
    if (!dlm.isEmpty() && dlm.getSize() >= lastIndex) {
      JarEntry jarEntry = dlm.get(lastIndex);
      // open the file
      VirtualFile localVirtualFileByPath =
          JarFileSystem.getInstance().findLocalVirtualFileByPath(file.getPath());
      assert localVirtualFileByPath != null;
      localVirtualFileByPath = localVirtualFileByPath.findFileByRelativePath(jarEntry.getName());
      FileEditorManager.getInstance(project)
          .openFile(Objects.requireNonNull(localVirtualFileByPath), true);
    }
  }

  private void createUIComponents() {
    panel1 = new JPanel();
  }
}
