package Actions;

import Compile.Compiler;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;

public class OpenClassFileAction extends AnAction {
  public OpenClassFileAction(String Text) {
    super(Text);
  }

  public OpenClassFileAction() {
    super();
  }

  @Override
  public void update(AnActionEvent e) {
    final Project project = e.getProject();
    final VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);

    // show Action only for java files
    e.getPresentation()
        .setEnabledAndVisible(
            project != null
                && virtualFile != null
                && (virtualFile.getExtension().equals("java")
                    || virtualFile.getExtension().equals("scala")));
  }

  @Override
  public void actionPerformed(AnActionEvent event) {
    final Project project = event.getProject();
    if (project != null && Compiler.make(project)) {
      // currently selected file in the project view
      VirtualFile javaFile = event.getData(CommonDataKeys.VIRTUAL_FILE);
      VirtualFile classFile = getCorrespondingClassFile(project, javaFile);
      if (classFile == null) {
        // TODO: compile entire module (think about dependencies) or just the current file?
        // CompilerManager.getInstance(project).compile(new VirtualFile[] {javaFile}, );
        CompilerManager.getInstance(project).rebuild(null);
        do {
          try {
            TimeUnit.SECONDS.sleep(2);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } while (CompilerManager.getInstance(project).isCompilationActive());
        classFile = getCorrespondingClassFile(project, javaFile);
      }
      FileEditorManager.getInstance(project).openFile(classFile, true);
      FileEditorManager.getInstance(project).setSelectedEditor(classFile, "OPAL-DIS");
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
  } // getCorrespondingClassFile()
}
