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
  public void update(AnActionEvent e) {
    final VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
    PsiElement element = e.getData(CommonDataKeys.PSI_ELEMENT);
    final String extension = virtualFile != null ? virtualFile.getExtension() : "";
    // show Action only for java,class & scala files
    e.getPresentation()
            .setEnabledAndVisible((element instanceof PsiClass) && ("java".equals(extension)
                            || "class".equals(extension)));
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
    if (!(element instanceof PsiClass) || project == null ) {
      return;
    }
    virtualFile = element.getNavigationElement().getContainingFile().getVirtualFile();
    String extension = virtualFile.getExtension();
    VirtualFile classFile = null;
    if (!StdFileTypes.CLASS.getDefaultExtension().equals(extension)) {
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
      Notifications.Bus.notify(
              new NotificationGroup("OpalPlugin", NotificationDisplayType.BALLOON, false)
                      .createNotification()
                      .setContent("decompiling : " + classFile.getName()));
      FileEditorManager.getInstance(project).openFile(classFile, true);
      FileEditorManager.getInstance(project).setSelectedEditor(classFile, editorName);
    } else {

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
      VirtualFile outputPath =
              Objects.requireNonNull(CompilerModuleExtension.getInstance(module))
                      .getCompilerOutputPath();
      if (outputPath == null) {
        return null;
      }
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
                    // VirtualFileVisitor.limit(0);
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
