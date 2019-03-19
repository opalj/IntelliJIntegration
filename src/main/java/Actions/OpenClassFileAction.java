package Actions;

import Compile.Compiler;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.vfs.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import opalintegration.OpalUtil;
import org.jetbrains.annotations.NotNull;

/** The type Open class file action performs to open a specified editor (tac/bytecode) */
class OpenClassFileAction extends AnAction {
  private String editorName;

  /**
   * Instantiates a new Open class file action.
   *
   * @param editorName the editor name witch will be opened
   */
  public OpenClassFileAction(String editorName) {
    super();
    this.editorName = editorName;
  }

  @Override
  public void update(AnActionEvent e) {
    final Project project = e.getProject();
    final VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
    final String extension = virtualFile != null ? virtualFile.getExtension() : "";
    // show Action only for java,class & scala files
    e.getPresentation()
        .setEnabledAndVisible(
            project != null
                && ("java".equals(extension)
                    // || "scala".equals(extension) // TODO: check how it works for scala files
                    || "class".equals(extension)));
  }

  /**
   * performed after a clickevent to open a the specified editor
   *
   * @param event the event fired if action is peformed
   */
  @Override
  public void actionPerformed(AnActionEvent event) {
    // Load the file
    Project project = event.getData(CommonDataKeys.PROJECT);
    // currently selected file in the project view
    VirtualFile virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE);
    String extension = virtualFile != null ? virtualFile.getExtension() : "";
    VirtualFile classFile = null;
    if (project != null && !extension.equals(StdFileTypes.CLASS.getDefaultExtension())) {
      if (Compiler.make(project, virtualFile)) {
        classFile = getCorrespondingClassFile(project, virtualFile);
      } else if (ProjectFileIndex.getInstance(project).isInLibrary(virtualFile)) {
        String FileName = OpalUtil.getJarFileRootAndFileName(virtualFile.getParent())[1];
        Collection<VirtualFile> virtualFilesByName =
            FilenameIndex.getVirtualFilesByName(
                project,
                virtualFile.getNameWithoutExtension() + ".class",
                GlobalSearchScope.allScope(project));
        for (VirtualFile vf : virtualFilesByName) {
          if (vf.getPath().contains(FileName)) {
            classFile = vf;
            break;
          }
        }
      }
    } else {
      classFile = virtualFile;
    }
    if (classFile != null) {
      //      Notifications.Bus.notify(
      //          new Notification(
      //              "OpalPlugin",
      //              "OpalPlugin",
      //              "decompiling : " + classFile.getName(),
      //              NotificationType.INFORMATION));
      Notifications.Bus.notify(
          new NotificationGroup("OpalPlugin", NotificationDisplayType.BALLOON, false)
              .createNotification()
              .setContent("decompiling : " + classFile.getName()));
      FileEditorManager.getInstance(project).openFile(classFile, true);
      FileEditorManager.getInstance(project).setSelectedEditor(classFile, editorName);
    } else {
      //      Notifications.Bus.notify(
      //          new Notification(
      //              "OpalPlugin",
      //              "OpalPlugin",
      //              "can't find or create a class file for : " + virtualFile.getName(),
      //              NotificationType.INFORMATION));
      Notifications.Bus.notify(
          new NotificationGroup("OpalPlugin", NotificationDisplayType.BALLOON, false)
              .createNotification()
              .setContent("can't find or create a class file for : " + virtualFile.getName()));
    }
  } // actionPerformed

  /**
   * Will look for the corresponding .class-file of the passed in .java-file in the output directory
   * of the module, and return it
   *
   * @param project the current project (needed to determine the current module)
   * @param javaFile the .java-file whose corresponding .class-file we're looking for
   * @return the .class-File corresponding to javaFile
   */
  private VirtualFile getCorrespondingClassFile(Project project, VirtualFile javaFile) {
    // get the current module
    ProjectFileIndex projectFileIndex = ProjectFileIndex.getInstance(project);
    Module module = projectFileIndex.getModuleForFile(javaFile);
    // get the output directory
    if (module != null) {
      VirtualFile outputPath = CompilerModuleExtension.getInstance(module).getCompilerOutputPath();
      outputPath.refresh(false, true);
      // the name of the class file we are looking for
      String classFileName = javaFile.getNameWithoutExtension() + ".class";
      // collect all classFiles in the output directory
      List<VirtualFile> classFiles = new ArrayList<>();
      VfsUtilCore.visitChildrenRecursively(
          outputPath,
          new VirtualFileVisitor<VirtualFile>() {
            @NotNull
            @Override
            public Result visitFileEx(@NotNull VirtualFile file) {
              if (!file.isDirectory() && file.getName().equals(classFileName)) {
                classFiles.add(file);
                VirtualFileVisitor.limit(-1);
                return VirtualFileVisitor.SKIP_CHILDREN;
              }
              return CONTINUE;
            }
          });
      VirtualFile classFile = null;
      if (!classFiles.isEmpty()) {
        classFile = classFiles.get(0);
      }
      return classFile;
    }
    return null;
  } // getCorrespondingClassFile()
}
