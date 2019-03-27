package Components;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.*;
import globalData.GlobalData;
import java.util.Arrays;
import java.util.Objects;
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
    OpalVirtualFileListener.project = project;
    myVirtualFileListener myVirtualFileListener = new myVirtualFileListener();
    VirtualFileManager.getInstance().addVirtualFileListener(myVirtualFileListener);
  }

  private static class myVirtualFileListener implements VirtualFileListener {
    /**
     * if a *.class content has been changed it will automatically update the bytecode- and tac-file
     * with it
     *
     * @param event the changed file
     */
    @Override
    public void contentsChanged(@NotNull VirtualFileEvent event) {
      if (event.getFile().getExtension() == null) {
        return;
      }
      if (event.getFile().getExtension().equals(StdFileTypes.CLASS.getDefaultExtension())) {
        Arrays.stream(FileEditorManager.getInstance(project).getEditors(event.getFile()))
            .filter(
                (e) ->
                    !Objects.equals(
                        Objects.requireNonNull(e.getFile()).getExtension(),
                        StdFileTypes.CLASS.getDefaultExtension()))
            .forEach(
                e -> {
                  OpalUtil.prepare(
                      project,
                      Objects.requireNonNull(e.getFile().getExtension()),
                      event.getFile(),
                      e.getFile());
                  e.getFile().refresh(false, false);
                });
      } else if (GlobalData.DISASSEMBLED_FILE_ENDING_JBC.equals(event.getFile().getExtension())
          || GlobalData.DISASSEMBLED_FILE_ENDING_TAC.equals(event.getFile().getExtension())) {
        event.getFile().refresh(false, false);
      }
    }
  }
}
