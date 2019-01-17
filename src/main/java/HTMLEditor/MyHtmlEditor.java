package HTMLEditor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.EditorWithProviderComposite;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import java.beans.PropertyChangeListener;
import javafx.scene.web.WebEngine;
import javax.swing.*;
import opalintegration.Opal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * @example: https://github.com/JetBrains/intellij-community/blob/master/images/src/org/intellij/images/editor/impl/ImageEditorImpl.java
 */
public class MyHtmlEditor implements FileEditor {

  private final MyHtmlEditorUI editorUI;
  private final VirtualFile virtualFile;
  private VirtualFile htmlFile;
  private final Project project;
  private boolean disposed;

  public MyHtmlEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {
    this.project = project;
    this.virtualFile = virtualFile;
    htmlFile =
        Opal.prepareHtml(
            project,
            virtualFile); // TODO: doesn't update after .class file changes (similar for TAC)
    editorUI = new MyHtmlEditorUI(htmlFile);

    Disposer.register(this, editorUI);
  }

  public Project getProject() {
    return project;
  }

  public WebEngine getWebEngine() {
    return editorUI.getWebEngine();
  }

  // TODO: find appropriate place to call this from (currently @ MyHtmlEditor#getName())
  private void setTacEditorName() {
    FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
    EditorWithProviderComposite ewpc;

    for (EditorWindow ew : fileEditorManagerEx.getWindows()) {
      ewpc = ew.findFileComposite(virtualFile);

      if (ewpc == null) {
        continue;
      }

      FileEditor fe = ewpc.getSelectedEditorWithProvider().first;
      FileEditorProvider fep = ewpc.getSelectedEditorWithProvider().second;

      ewpc.setDisplayName(fe, fep.getEditorTypeId());
    }
  }

  @NotNull
  @Override
  public JComponent getComponent() {
    return editorUI;
  }

  @Nullable
  @Override
  public JComponent getPreferredFocusedComponent() {
    return editorUI.getPreferredComponent();
  }

  @NotNull
  @Override
  public String getName() {
    // setTacEditorName();
    return "OPAL HTML";
  }

  @Override
  public void setState(@NotNull FileEditorState state) {}

  @Override
  public boolean isModified() {
    return false;
  }

  @Override
  public boolean isValid() {
    // valid as long as NOT disposed AND its contents are still valid (e.g. file not deleted)
    return !disposed && virtualFile.isValid();
  }

  @Override
  public void selectNotify() {
    // for now can remain empty (called when editor is selected)
    //    Messages.showInfoMessage("selectNotify(): " + virtualFile.getName(), "MyHtmlEditor");
  }

  @Override
  public void deselectNotify() {
    // for now can remain empty (called when editor is deselected)
    //    Messages.showInfoMessage("deselectNotify() " + virtualFile.getName(), "MyHtmlEditor");
  }

  @Override
  public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {}

  @Override
  public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {}

  @Nullable
  @Override
  public BackgroundEditorHighlighter getBackgroundHighlighter() {
    return null;
  }

  @Nullable
  @Override
  public FileEditorLocation getCurrentLocation() {
    return null;
  }

  @Override
  public void dispose() {
    disposed = true;
    Disposer.dispose(editorUI);
  }

  @Nullable
  @Override
  public <T> T getUserData(@NotNull Key<T> key) {
    return null;
  }

  @Override
  public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {}

  @Override
  public VirtualFile getFile() {
    return virtualFile;
  }

  @Nullable
  @Override
  public StructureViewBuilder getStructureViewBuilder() {
    PsiFile psiFile = PsiManager.getInstance(project).findFile(htmlFile);
    StructureViewTreeElement root = new MyStructureViewTreeElement(false, (XmlFile) psiFile, this);
    return new MyStructureViewBuilder(psiFile, root);
  }
}
