/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package Compile;

import com.intellij.compiler.CompilerManagerImpl;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompileStatusNotification;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.CompilerProjectExtension;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import com.intellij.openapi.vfs.VirtualFileVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.javac.ExternalJavacManager;

/** a compiler helper class */
public class Compiler {

//  private static final Logger LOGGER = Logger.getLogger(Compiler.class.getName());
  /**
   * compiles an whole project
   *
   * @param project the actual project
  **/
  public void make(@NotNull final Project project) {
    CompilerManager compilerManager = CompilerManager.getInstance(project);
    CompileScope projectCompileScope = compilerManager.createProjectCompileScope(project);
    make(project, projectCompileScope,null);
  }

  public void make(@NotNull final Project project, CompileScope scope, CompileStatusNotification compilingNotifaction) {
    CompilerManager compilerManager = CompilerManager.getInstance(project);
    //if (!compilerManager.isUpToDate(scope)) {
      compilerManager.makeWithModalProgress(scope, compilingNotifaction);
      Notifications.Bus.notify(
              new NotificationGroup("OpalPlugin", NotificationDisplayType.BALLOON, false)
                      .createNotification()
                      .setContent("building "+scope.toString()+" to show "));
      //compilerManager.make(scope, compilingNotifaction);
    //}
  }

  /**
   * compiles an a specific java file for a given project if the java file contains in the given the
   * project
   *
   * @param project     the actual project
   * @param virtualFile the java file to compile
   * @return true if java file contains in the given project
   */
  public void make(@NotNull final Project project, VirtualFile virtualFile, String editorName) {
    CompilerManager compilerManager = CompilerManager.getInstance(project);
    if (!compilerManager.isCompilableFileType(virtualFile.getFileType())) {
      Notifications.Bus.notify(
              new NotificationGroup("OpalPlugin", NotificationDisplayType.BALLOON, false)
                      .createNotification()
                      .setContent("can't find a registered compiler in given project for: " + virtualFile.getName()));
      return; // maybe more
    }
    VirtualFile[] virtualFiles = new VirtualFile[]{virtualFile};
    CompileScope filesCompileScope = compilerManager.createFilesCompileScope(virtualFiles);
//    compilerManager.addCompiler();
    compilingNotifaction = (aborted, errors, warnings, compileContext) -> {
      if (aborted) { // do nothing if manually channeled
        return;
      }
      if (errors == 0) {
        VirtualFile classFile = getCorrespondingClassFile(compileContext.getProject(),virtualFile);
        ApplicationManager.getApplication().invokeLater(()->FileEditorManager.getInstance(compileContext.getProject()).openFile(classFile, true), ModalityState.NON_MODAL);
        FileEditorManager.getInstance(compileContext.getProject()).setSelectedEditor(classFile, editorName);
      }else{
        ApplicationManager.getApplication().invokeLater(queue.poll());
      }
    };
    queue = new LinkedBlockingQueue<>();
    queue.add(()->   make(project, filesCompileScope, compilingNotifaction));
    queue.add(()->make(project));
    ApplicationManager.getApplication().invokeLater(()->compilerManager.compile(virtualFiles, compilingNotifaction));
  }

  private CompileStatusNotification compilingNotifaction;
  private Queue<Runnable> queue ;

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
    LinkedList<VirtualFile> vfList = new LinkedList<>();
    // get the output directory
    if (Objects.requireNonNull(CompilerProjectExtension.getInstance(project)).getCompilerOutput() != null) {
      vfList.add(Objects.requireNonNull(CompilerProjectExtension.getInstance(project)).getCompilerOutput());
    }
    if (module != null) {
      if (Objects.requireNonNull(CompilerModuleExtension.getInstance(module)).getCompilerOutputPath() != null) {
        vfList.add(Objects.requireNonNull(CompilerModuleExtension.getInstance(module)).getCompilerOutputPath());
      }
      if (Objects.requireNonNull(CompilerModuleExtension.getInstance(module)).getCompilerOutputPathForTests()!= null) {
        vfList.add(Objects.requireNonNull(CompilerModuleExtension.getInstance(module)).getCompilerOutputPathForTests());
      }
    }
    if (vfList.isEmpty()) {
      return null;
    }
    List<VirtualFile> classFiles = new ArrayList<>();
    for (VirtualFile outputPath : vfList) {
      outputPath.refresh(true, true);
      // the name of the class file we are looking for
      String classFileName = javaFile.getNameWithoutExtension() + ".class";
      // collect all classFiles in the output directory
      VfsUtilCore.visitChildrenRecursively(
              outputPath,
              new VirtualFileVisitor<VirtualFile>() {
                @NotNull
                @Override
                public Result visitFileEx(@NotNull VirtualFile file) {
                  if (!file.isDirectory() && file.getName().equals(classFileName)) {
                    classFiles.add(file);
                    return VirtualFileVisitor.SKIP_CHILDREN;
                  }
                  return CONTINUE;
                }
              });
    }
      if (!classFiles.isEmpty()) {
        return classFiles.get(0);
      }else{
        //TODO
        return null;
      }
  }

}
