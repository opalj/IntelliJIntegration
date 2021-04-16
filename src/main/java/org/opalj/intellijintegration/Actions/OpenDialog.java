/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.Actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.opalj.intellijintegration.Components.SampleDialogWrapper;

public class OpenDialog extends AnAction {
  Project project;

  @Override
  public void update(@NotNull AnActionEvent e) {
    project = e.getProject();
    // todo EditorActionManager.getInstance().getActionHandler(IdeActions.);
    String extension = ActionUtil.ExtString(e);
    e.getPresentation().setEnabledAndVisible(project != null && "jar".equals(extension));
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    SampleDialogWrapper sampleDialogWrapper =
        new SampleDialogWrapper(e.getProject(), e.getData(CommonDataKeys.VIRTUAL_FILE));
    sampleDialogWrapper.setSize(300, 500);
    sampleDialogWrapper.show();
  }
}
