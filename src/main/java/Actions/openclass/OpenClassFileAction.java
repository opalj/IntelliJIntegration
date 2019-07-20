/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package Actions.openclass;

import Actions.ActionUtil;
import Compile.Compiler;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.vfs.*;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import opalintegration.OpalUtil;
import org.jetbrains.annotations.NotNull;

/** The type Open class file action performs to open a specified editor (tac/bytecode) */
class OpenClassFileAction extends AnAction {
  private final String editorName;

  /**
   * Instantiates a new Open class file action.
   *
   * @param editorName the editor name witch will be opened
   */
  OpenClassFileAction(String editorName) {
    super();
    this.editorName = editorName;
  }

  @Override
  public void update(@NotNull AnActionEvent e) {
    // show Action only for java,class & scala files
    String extension = ActionUtil.ExtString(e);
    PsiElement element = e.getData(CommonDataKeys.PSI_ELEMENT);
    e.getPresentation()
        .setEnabledAndVisible(
            (element instanceof PsiClass)
                && ("java".equals(extension)
                    || "class".equals(extension)
                    || "scala".equals(extension)));
  }

  /**
   * performed after a click event to open a the specified editor
   *
   * @param e the event fired if action is performed
   */
  @Override
  public void actionPerformed(AnActionEvent e) {
    // Load the file
    Project project = e.getData(CommonDataKeys.PROJECT);
    final PsiElement element = e.getData(CommonDataKeys.PSI_ELEMENT);
    // currently selected file in the project view
    VirtualFile virtualFile;
    if (!(element instanceof PsiClass) || project == null) {
      return;
    }
    // virtualFile = element.getNavigationElement().getContainingFile().getVirtualFile();
    virtualFile = Objects.requireNonNull(e.getData(CommonDataKeys.PSI_FILE)).getVirtualFile();
    String extension = virtualFile.getExtension();
    //if (StdFileTypes.CLASS.getDefaultExtension().equals(extension)) { // if you clicked on class-file
      if (ProjectFileIndex.getInstance(project).isInLibrary(virtualFile)) {
        String FileName = OpalUtil.getJarFileRootAndFileName(virtualFile.getParent())[1];
        Collection<VirtualFile> virtualFilesByName =
            FilenameIndex.getVirtualFilesByName(
                project,
                virtualFile.getNameWithoutExtension() + ".class",
                GlobalSearchScope.allScope(project));
        for (VirtualFile vf : virtualFilesByName) {
          if (vf.getPath().contains(FileName)) {
            FileEditorManager.getInstance(project).openFile(vf, true);
            FileEditorManager.getInstance(project).setSelectedEditor(vf, editorName);
            return;
          }
          }
        Notifications.Bus.notify(
                new NotificationGroup("OpalPlugin", NotificationDisplayType.BALLOON, false)
                        .createNotification()
                        .setContent("can't open the class file : " + virtualFile.getName()));
        }else
      //}//else build a class file
      new Compiler().make(project,virtualFile,editorName);
      }
  } // actionPerformed

