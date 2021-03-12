/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package listeners;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import globalData.GlobalData;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import opalintegration.OpalUtil;
import org.jetbrains.annotations.NotNull;

public class OpalVirtualFileListener implements BulkFileListener {

  private final Project project;

  public OpalVirtualFileListener(Project project) {
    this.project = project;
  }

  @Override
  public void after(@NotNull List<? extends VFileEvent> events) {
    events.forEach(
        event -> {
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
        });
  }
}
