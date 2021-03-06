/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.Actions;

import static org.opalj.intellijintegration.globalData.GlobalData.TAC_EDITOR_ID;

import com.intellij.ide.DataManager;
import com.intellij.ide.dnd.FileCopyPasteUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ui.JBUI;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Optional;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;

public class DnDJarAction extends AnAction implements CustomComponentAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    if (project == null) return;
    DecompileFromJar.openDialog(project, null, TAC_EDITOR_ID);
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
                AnActionEvent.createFromInputEvent(
                    e,
                    ActionPlaces.UNKNOWN,
                    null,
                    DataManager.getInstance().getDataContext(e.getComponent())));
          }

          @Override
          public void mouseEntered(MouseEvent e) {
            comp.setBackground(JBUI.CurrentTheme.ToolWindow.hoverBackground());
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
        Project project = CommonDataKeys.PROJECT.getData(context);
        if (project != null && fileList != null) {
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
            DecompileFromJar.openDialog(project, vFileJar, TAC_EDITOR_ID);
            return true;
          }
        }
      }
      return false;
    }
  }
}
