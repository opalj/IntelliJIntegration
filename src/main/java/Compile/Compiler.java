package Compile;

import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/** a compiler helper class */
public final class Compiler {

  /**
   * compiles an whole project
   *
   * @param project
   * @return true if the project was up-to-date
   */
  public static boolean make(@NotNull final Project project) {
    CompilerManager compManager = CompilerManager.getInstance(project);
    CompileScope projectCompileScope = compManager.createProjectCompileScope(project);
    boolean uptoDate = compManager.isUpToDate(projectCompileScope);
    if (!uptoDate) {
      compManager.make(projectCompileScope, null);
      do {
        try {
          TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } while (compManager.isCompilationActive());
    }
    return uptoDate;
  }
  /**
   * compiles an a specific java file for a given project if the java file contains in the given the
   * project
   *
   * @param project
   * @param javaFile
   * @return true if java file contains in the given project
   */
  public static boolean make(@NotNull final Project project, VirtualFile javaFile) {
    ProjectFileIndex projectFileIndex = ProjectFileIndex.getInstance(project);
    Module module = projectFileIndex.getModuleForFile(javaFile);
    if (module != null) {
      CompilerManager.getInstance(project).compile(new VirtualFile[] {javaFile}, null);
      do {
        try {
          TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } while (CompilerManager.getInstance(project).isCompilationActive());
      return true;
    }
    return false;
  }
}
