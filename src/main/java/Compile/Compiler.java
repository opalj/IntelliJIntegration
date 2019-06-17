/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package Compile;

import com.intellij.compiler.CompilerManagerImpl;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

/** a compiler helper class */
public final class Compiler {

  private static final Logger LOGGER = Logger.getLogger(Compiler.class.getName());

  /**
   * compiles an a specific java file for a given project if the java file contains in the given the
   * project
   *
   * @param project the actual project
   * @param javaFile the java file to compile
   * @return true if java file contains in the given project
   */
  public static boolean make(@NotNull final Project project, VirtualFile javaFile) {
    ProjectFileIndex projectFileIndex = ProjectFileIndex.getInstance(project);
    Module module = projectFileIndex.getModuleForFile(javaFile);
    if (module != null) {
      CompilerManager.getInstance(project).compile(new VirtualFile[] {javaFile}, null);
      asyncCompiling(CompilerManager.getInstance(project));
      return true;
    }
    return false;
  }

  /**
   * wait until the compiling process is finished
   *
   * @param compilerManager the manager for the current project
   */
  private static void asyncCompiling(CompilerManager compilerManager) {
    int i = 50; // 50*0.2 s = 10 sek till end
    do {
      try {
        TimeUnit.MILLISECONDS.sleep(200);
      } catch (InterruptedException e) {
        LOGGER.log(Level.SEVERE, e.toString(), e);
      }
      i--;
    } while (compilerManager.isCompilationActive() && i > 0);
  }
}
