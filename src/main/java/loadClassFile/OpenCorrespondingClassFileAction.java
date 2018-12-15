package loadClassFile;

import Compile.Compiler;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.*;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class OpenCorrespondingClassFileAction extends AnAction {

  public OpenCorrespondingClassFileAction() {
    // Set the menu item name.
    super("Compile And Open .class-File");
  }

  @Override
  public void update(AnActionEvent e) {
    final Project project = e.getProject();
    final VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);

    // show Action only for java files
    e.getPresentation()
        .setEnabledAndVisible(
            project != null && virtualFile != null && virtualFile.getExtension().equals("java"));
  }

  @Override
  public void actionPerformed(AnActionEvent event) {
    final Project project = event.getProject();

    if (project != null && Compiler.make(project)) {
      // currently selected file in the project view
      VirtualFile javaFile = event.getData(CommonDataKeys.VIRTUAL_FILE);

      // TODO: refresh needed ? (A snchronous refresh will block until the refresh is done)
      VirtualFileManager.getInstance().syncRefresh();
      VirtualFile classFile = getCorrespondingClassFile(project, javaFile);
      FileEditorManager.getInstance(project).openFile(classFile, true);
    } // if
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
    VirtualFile outputPath = CompilerModuleExtension.getInstance(module).getCompilerOutputPath();

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
            // TODO: could be improved by e.g. breaking at the right moment and returning the last
            // element
            if (!file.isDirectory()) {
              classFiles.add(file);
            }

            return CONTINUE;
          }
        });

    // find the classFile we need, and return it
    VirtualFile classFile = classFiles.get(0);
    for (VirtualFile cf : classFiles) {
      if (cf.getName().equals(classFileName)) {
        classFile = cf;
        break;
      }
    }

    return classFile;
  } // getCorrespondingClassFile()
}
