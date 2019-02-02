package HTMLEditor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.NavigatableFileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import javafx.scene.web.WebEngine;
import opalintegration.Opal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/*
 * @example: https://github.com/JetBrains/intellij-community/blob/master/images/src/org/intellij/images/editor/impl/ImageEditorImpl.java
 */
public class HTMLEditor extends UserDataHolderBase implements NavigatableFileEditor {

  private final HTMLEditorComponent editorUI;
  private final VirtualFile virtualFile;
  private VirtualFile htmlFile;
  private final Project project;
  private boolean disposed;
  private final PropertyChangeSupport myChangeSupport = new PropertyChangeSupport(this);
  String
  public HTMLEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {
    this.project = project;
    this.virtualFile = virtualFile;
    htmlFile =
        Opal.prepareHtml(
            project,
            virtualFile); // TODO: doesn't update after .class file changes (similar for TAC)
    editorUI = new HTMLEditorComponent(htmlFile, this);

    Disposer.register(this, editorUI);
  }

  public Project getProject() {
    return project;
  }

  public WebEngine getWebEngine() {
    return editorUI.getWebEngine();
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
    //     setTacEditorName();
    return "Bytecode";
  }

  @Override
  public void setState(@NotNull FileEditorState state) {}

  @Override
  public boolean isModified() {
    return editorUI.isModified();
  }

  @Override
  public boolean isValid() {
    // valid as long as NOT disposed AND its contents are still valid (e.g. file not deleted)
    return !disposed && virtualFile.isValid();
  }

  @Override
  public void selectNotify() {
    // for now can remain empty (called when editor is selected)
    //    Messages.showInfoMessage("selectNotify(): " + virtualFile.getName(), "HTMLEditor");
  }

  @Override
  public void deselectNotify() {
    // for now can remain empty (called when editor is deselected)
    //    Messages.showInfoMessage("deselectNotify() " + virtualFile.getName(), "HTMLEditor");
  }

  @Override
  public void addPropertyChangeListener(@NotNull final PropertyChangeListener listener) {
    myChangeSupport.addPropertyChangeListener(listener);
  }

  @Override
  public void removePropertyChangeListener(@NotNull final PropertyChangeListener listener) {
    myChangeSupport.removePropertyChangeListener(listener);
  }

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
    // Messages.showInfoMessage("getUserData(): " + key.toString(), "HTMLEditor");
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

  @Override
  public boolean canNavigateTo(@NotNull Navigatable navigatable) {
    Messages.showInfoMessage("canNavigateTo(): " + virtualFile.getName(), "HTMLEditor");
    return false;
  }

  @Override
  public void navigateTo(@NotNull Navigatable navigatable) {
    Messages.showInfoMessage("navigateTo(): " + virtualFile.getName(), "HTMLEditor");
  }
}
