package Actions;

import Compile.Compiler;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectCoreUtil;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.ui.configuration.projectRoot.daemon.ProjectStructureElement;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.messages.impl.Message;
import opalintegration.Opal;
import org.jetbrains.annotations.NotNull;

/**
 * The type Open class file action performs to open a specified editor (tac/bytecode)
 */
public class OpenClassFileAction extends AnAction {
  private String editorName;

  /**
   * Instantiates a new Open class file action.
   *
   * @param editorName the editor name witch will be opened
   */
  public OpenClassFileAction(String editorName) {
    super();this.editorName = editorName;
  }
  @Override
  public void update(AnActionEvent e) {
    final Project project = e.getProject();
    final VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
    // show Action only for java files
    e.getPresentation()
        .setEnabledAndVisible(
            project != null
                && ("java".equals(virtualFile.getExtension())
                    || "scala".equals(virtualFile.getExtension())
                    ||"class".equals(virtualFile.getExtension())));
  }

  /**
   *  performed after a clickevent to open a the specified editor
   * @param event
   */
  @Override
  public void actionPerformed(AnActionEvent event) {
    // Load the file
      final Project project = event.getProject();
      // currently selected file in the project view
      VirtualFile virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE);
      VirtualFile classFile = null;
      if(!virtualFile.getExtension().equals(StdFileTypes.CLASS.getDefaultExtension())) {
        if (Compiler.make(project, virtualFile)) {
          classFile = getCorrespondingClassFile(project, virtualFile);
        }else if(ProjectFileIndex.getInstance(project).isInLibrary(virtualFile)) {
          String FileName = Opal.getJarFileRootAndFileName(virtualFile.getParent())[1];
          Collection<VirtualFile> virtualFilesByName = FilenameIndex.getVirtualFilesByName(project, virtualFile.getNameWithoutExtension()+".class", GlobalSearchScope.allScope(project));
          for(VirtualFile vf : virtualFilesByName){
            if(vf.getPath().contains(FileName)) {
              classFile = vf;
              break;
            }
          }
        }
      }else{
        classFile = virtualFile;
      }
    if(classFile != null) {
      FileEditorManager.getInstance(project).openFile(classFile, true);
      FileEditorManager.getInstance(project).setSelectedEditor(classFile, editorName);
    }else {
      Notifications.Bus.notify(new Notification("OpalPlugin", "OpalPlugin", "can't find or create a class file for : " + virtualFile.getName(), NotificationType.INFORMATION));
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
    //else if (javaFile.getPath().contains("!")) {
//      File tempJavaFile = null;
//      File tempClassFile = null;
//      try {
//        tempJavaFile = new File(FileUtil.getTempDirectory() + File.separator + javaFile.getName());
//        Document document = FileDocumentManager.getInstance().getDocument(javaFile);
//        FileUtil.writeToFile(tempJavaFile, document.getText());
//        VirtualFile virtualTempJavaFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(tempJavaFile);
//        //todo should maybe be dont with compileManager(Impl).compile(new VirtualFile[]{virtualTempJavaFile},null);
//        String jarFilePath = Opal.getJarFileRoot(javaFile)
//                .substring(0, Opal.getJarFileRoot(javaFile).lastIndexOf("/") + 1);
//        String reduceequalsfold =
//                recFolder(new File(jarFilePath), new ArrayList<String>()).stream().reduce("", (s, s2) -> s.concat("/*;").concat(s2)).replaceFirst("/\\*;", "");
//        RunCommand.runJavaC(tempJavaFile.getPath(), reduceequalsfold);
//        tempClassFile =
//                new File(
//                        FileUtil.getTempDirectory()
//                                + File.separator
//                                + javaFile.getNameWithoutExtension()
//                                + ".class");
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//      return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(tempClassFile);
//    }
    return null;
  }// getCorrespondingClassFile()
  /**
   * helpermethod to find all path that contains jars.
   *
   * @param parrent the parrentfolder
   * @param jarName the jarPath array
   * @return the jarPath array list
   */
  public ArrayList<String> recFolder(File parrent, ArrayList<String> jarName) {
    for (File file : parrent.listFiles()) {
      if (file.isDirectory()) {
        recFolder(file, jarName);
      }else if(file.getName().contains("jar")){
        if(!jarName.contains(file.getParentFile() + "\\"))
        jarName.add(file.getParentFile() + "\\");
      }
    }
    return jarName;
  }
}
