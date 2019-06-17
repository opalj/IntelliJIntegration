/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package Actions;

import Components.SampleDialogWrapper;
import com.intellij.ide.DataManager;
import com.intellij.ide.dnd.FileCopyPasteUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import com.intellij.ui.MouseDragHelper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Optional;

import static com.intellij.util.ui.JBUI.CurrentTheme.ToolWindow.hoveredIconBackground;

public class DnDJarAction extends AnAction implements CustomComponentAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getProject();
        //final VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (project == null) return;
        openDialog(project,null);
    }
    private void openDialog(Project project, VirtualFile virtualFile){
        SampleDialogWrapper sampleDialogWrapper = new SampleDialogWrapper(project, virtualFile);
        sampleDialogWrapper.setSize(300, 500);
        sampleDialogWrapper.show();
    }
    //todo versioncheck
    @NotNull
    @Override
    public JComponent createCustomComponent(@NotNull Presentation presentation, @NotNull String place) {
        final JPanel comp = new JPanel();
        comp.setTransferHandler(new MyTransferHandler());
        comp.add(new JLabel(getTemplatePresentation().getText()));
        comp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                actionPerformed(AnActionEvent.createFromInputEvent(DnDJarAction.this, e,
                        ActionPlaces.UNKNOWN));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //comp.setBackground(hoveredIconBackground());
                comp.setBackground(new JBColor(0x4C5052,0x4C5052));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                comp.setBackground(JBColor.background());
            }
        });
        comp.setToolTipText(getTemplatePresentation().getDescription());
        return comp;
    }
    private class MyTransferHandler extends TransferHandler {

        @Override
        public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
            return transferFlavors != null && FileCopyPasteUtil.isFileListFlavorAvailable(transferFlavors);
        }
        @Override
        public boolean importData(JComponent comp, Transferable t) {
            if(canImport(comp,t.getTransferDataFlavors())){
                List<File> fileList = FileCopyPasteUtil.getFileList(t);
                DataContext context = DataManager.getInstance().getDataContext(comp);
                final Project project = CommonDataKeys.PROJECT.getData(context);
                if (fileList != null) {
                    Optional<File> firstJar = fileList.stream()
                            .filter(file -> !file.isDirectory() && file.getName().toUpperCase().endsWith(".JAR") && project != null)
                            .findFirst();
                    if(firstJar.isPresent()) {
                        VirtualFile vFileJar = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(firstJar.get());
                        openDialog(project,vFileJar);
                        return true;
                    }
                }
            }
            return false;
        }

    }

}
