package org.opalj.intellijintegration.Components;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.opalj.intellijintegration.Components.tree.DialogPannel;

public class SampleDialogWrapper extends DialogWrapper {
  private Project project;
  private VirtualFile virtualFile;
  private DialogPannel dialogPannel;

  public SampleDialogWrapper(@NotNull Project project, @Nullable VirtualFile virtualFile) {
    super(true); // use current window as parent
    this.project = project;
    this.virtualFile = virtualFile;
    init();
    setTitle("Jar Opener");
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    dialogPannel = new DialogPannel(project, virtualFile);
    return dialogPannel.getRootPane();
  }

  @Override
  protected void doOKAction() {
    dialogPannel.decompile();
    super.doOKAction();
  }

  @Override
  protected void dispose() {
    dialogPannel.dispose();
    super.dispose();
  }
}
