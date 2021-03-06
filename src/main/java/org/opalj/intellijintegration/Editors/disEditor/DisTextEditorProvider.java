/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.Editors.disEditor;

import static org.opalj.intellijintegration.globalData.GlobalData.BYTECODE_EDITOR_ID;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.Notifications;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import java.util.Objects;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.opalj.intellijintegration.globalData.GlobalData;
import org.opalj.intellijintegration.opalintegration.OpalUtil;

public class DisTextEditorProvider extends PsiAwareTextEditorProvider {
  @NonNls private static final String EDITOR_TYPE_ID = BYTECODE_EDITOR_ID;

  @Override
  public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
    // restrict to .class files only
    String fileExtension = file.getExtension();
    return (fileExtension != null)
        && (fileExtension.equals("class")
            || fileExtension.equals(GlobalData.DISASSEMBLED_FILE_ENDING_JBC));
  }

  @NotNull
  @Override
  public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
    if (!Objects.equals(file.getExtension(), GlobalData.DISASSEMBLED_FILE_ENDING_JBC)) {
      Notifications.Bus.notify(
          NotificationGroupManager.getInstance()
              .getNotificationGroup("OpalPlugin")
              .createNotification()
              .setContent("decompiling : " + file.getName()));
      return new DisTextEditor(
          project,
          OpalUtil.prepare(project, GlobalData.DISASSEMBLED_FILE_ENDING_JBC, file, null),
          this);
    }
    return new DisTextEditor(project, file, this);
  }

  @NotNull
  @Override
  public String getEditorTypeId() {
    return EDITOR_TYPE_ID;
  }

  @NotNull
  @Override
  public FileEditorPolicy getPolicy() {
    return FileEditorPolicy.NONE;
  }
}
