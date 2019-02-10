package Compile;

import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jdom.JDOMException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class Compiler {

  /*
   * Compile a project with a given (open) Project-Object
   */
  public static boolean make(@NotNull final Project project) {
    CompilerManager compManager = CompilerManager.getInstance(project);
    CompileScope projectCompileScope = compManager.createProjectCompileScope(project);
    boolean uptoDate = compManager.isUpToDate(projectCompileScope);
    if (!uptoDate) {
      // ApplicationManager.getApplication().invokeAndWait( () -
      // compManager.make(null);
      compManager.makeWithModalProgress(projectCompileScope, null);
      do {
        try {
          TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } while (compManager.isCompilationActive());
    }
    return uptoDate;
  }

  /*
   * Compile a project with a given filePath-String,
   * searches naively the first .idea-Dir or *.ipr-file
   */
  public boolean make(@NotNull String filePath) {
    //filePath = find(filePath);

    ProjectManager projectManager = ProjectManager.getInstance();
    Project project;
    // Look for OpenProjects
    for (Project openProject : projectManager.getOpenProjects()) {
      if (openProject != null && openProject.getBasePath().equals(filePath)) {
        project = openProject;
        return make(project);
      }
    }
    // try to open project
    try {
      project = projectManager.loadAndOpenProject(filePath);
      if (project == null) {
        return false;
      }
      boolean bMade = make(project);
      projectManager.closeProject(project);
      return bMade;
    } catch (IOException | JDOMException e) {
      e.printStackTrace();
    }
    return false;
  }
}
