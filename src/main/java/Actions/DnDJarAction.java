/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package Actions;

import com.intellij.ide.DataManager;
import com.intellij.ide.dnd.FileCopyPasteUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ui.JBUI;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;

public class DnDJarAction extends AnAction implements CustomComponentAction {
  private Project project;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    project = e.getProject();
    // final VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
    if (project == null) return;
    openDialog(project, null);
  }

  private void openDialog(Project project, VirtualFile virtualFile) {
    FileChooserDescriptor fileChooserDescriptor =
        new FileChooserDescriptor(true, false, false, false, true, false);
    fileChooserDescriptor.withFileFilter(
        vf -> Pattern.matches(".*\\.(class|jar|zip)", vf.getName()));
    FileChooser.chooseFile(fileChooserDescriptor, project, virtualFile, vf -> openAndDecompile(vf));
  }

  private void openAndDecompile(VirtualFile selectedFile) {
    if (selectedFile.getName().toUpperCase().endsWith(".CLASS"))
      FileEditorManager.getInstance(project).openFile(Objects.requireNonNull(selectedFile), true);
  }
  // todo versioncheck
  @NotNull
  @Override
  public JComponent createCustomComponent(
      @NotNull Presentation presentation, @NotNull String place) {
    final JPanel comp = new JPanel();
    comp.setTransferHandler(new MyTransferHandler());
    comp.add(new JLabel(getTemplatePresentation().getText()));
    comp.add(new JLabel(getTemplatePresentation().getIcon()));
    comp.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            actionPerformed(
                AnActionEvent.createFromInputEvent(DnDJarAction.this, e, ActionPlaces.UNKNOWN));
          }

          @Override
          public void mouseEntered(MouseEvent e) {
            comp.setBackground(JBUI.CurrentTheme.ToolWindow.tabHoveredBackground(true));
          }

          @Override
          // JBColor
          public void mouseExited(MouseEvent e) {
            comp.setBackground(JBUI.CurrentTheme.CustomFrameDecorations.paneBackground());
          }
        });
    comp.setToolTipText(getTemplatePresentation().getDescription());
    return comp;
  }

  private class MyTransferHandler extends TransferHandler {

    @Override
    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
      return transferFlavors != null
          && FileCopyPasteUtil.isFileListFlavorAvailable(transferFlavors);
    }

    @Override
    public boolean importData(JComponent comp, Transferable t) {
      if (canImport(comp, t.getTransferDataFlavors())) {
        List<File> fileList = FileCopyPasteUtil.getFileList(t);
        DataContext context = DataManager.getInstance().getDataContext(comp);
        final Project project = CommonDataKeys.PROJECT.getData(context);
        if (fileList != null) {
          Optional<File> firstJar =
              fileList
                  .stream()
                  .filter(
                      file ->
                          !file.isDirectory()
                              && file.getName().toUpperCase().endsWith(".JAR")
                              && project != null)
                  .findFirst();
          if (firstJar.isPresent()) {
            VirtualFile vFileJar =
                LocalFileSystem.getInstance().refreshAndFindFileByIoFile(firstJar.get());
            openDialog(project, vFileJar);
            return true;
          }
        }
      }
      return false;
    }
  }
}
