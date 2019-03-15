package Components;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.*;
import java.util.Arrays;

import globalData.GlobalData;
import opalintegration.OpalUtil;
import org.jetbrains.annotations.NotNull;

/** a project component to load a virtual file listener to *.class-files. */
public class OpalVirtualFileListener implements ProjectComponent {
  private static Project project;

  /**
   * Instantiates a new Opal virtual file listener.
   *
   * @param project the project
   */
  public OpalVirtualFileListener(Project project) {
    this.project = project;
    myVirtualFileListener myVirtualFileListener = new myVirtualFileListener();
    VirtualFileManager.getInstance().addVirtualFileListener(myVirtualFileListener);
  }

  private static class myVirtualFileListener implements VirtualFileListener {
    /**
     * if a *.class content has been changed it will automatically update the bytecode- and tac-file with it
     *
     * @param event the changed file
     */
    @Override
    public void contentsChanged(@NotNull VirtualFileEvent event) {
      // TODO: does this take care of the NullPointerException ?
      if(event.getFile().getExtension() == null) {
        return;
      }

      if (event.getFile().getExtension().equals(StdFileTypes.CLASS.getDefaultExtension())) {
        Arrays.stream(FileEditorManager.getInstance(project).getEditors(event.getFile()))
            .filter(
                (e) -> !e.getFile().getExtension().equals(StdFileTypes.CLASS.getDefaultExtension()))
            .forEach(
                e -> {
                  OpalUtil.prepare(
                      project, e.getFile().getExtension(), event.getFile(), e.getFile());
                  e.getFile().refresh(false, false);
                });
      }
      else if(event.getFile().getExtension().equals(GlobalData.DISASSEMBLED_FILE_ENDING_JBC)
              ||event.getFile().getExtension().equals(GlobalData.DISASSEMBLED_FILE_ENDING_TAC)) {
        event.getFile().refresh(false,false);
      }
    }
  }
}
