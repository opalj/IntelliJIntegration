package HTMLEditor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.*;
import com.intellij.openapi.vfs.newvfs.RefreshQueue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;


/*
 * @example: https://github.com/JetBrains/intellij-community/blob/master/images/src/org/intellij/images/editor/impl/ImageEditorImpl.java
 */
public class MyHtmlEditor implements FileEditor {

    private final MyHtmlEditorUI editorUI;

    private FileEditorState fileEditorState;
    private final Project project;
    private final VirtualFile virtualFile;
    private boolean disposed;

    public MyHtmlEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        this.project = project;
        this.virtualFile = virtualFile;
        editorUI = new MyHtmlEditorUI();
        // TODO ...
        fileEditorState = FileEditorState.INSTANCE;
        // end
        VirtualFileManager.getInstance().addVirtualFileListener(new VirtualFileListener() {
            @Override
            public void propertyChanged(@NotNull VirtualFilePropertyEvent event) {
                MyHtmlEditor.this.propertyChanged(event);
            }

            @Override
            public void contentsChanged(@NotNull VirtualFileEvent event) {
                MyHtmlEditor.this.contentsChanged(event);
            }
        });

        setValue(virtualFile);
    }

    // TODO
    void setValue(VirtualFile virtualFile) {
        try {

        } catch(Exception e) {

        }
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        // TODO: currently unable to select/mark HTML content on JPanel
        return editorUI;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return editorUI;
    }

    @NotNull
    @Override
    public String getName() {
        // TODO: is this OK?
        return "HTML Viewer";
    }

    @Override
    public void setState(@NotNull FileEditorState state) {
        fileEditorState = state;
    }

    @Override
    public boolean isModified() {
        // for now assume read-only document
        return false;
    }

    @Override
    public boolean isValid() {
        // valid as long as NOT disposed AND its contents are still valid (e.g. not deleted)
        return !disposed && virtualFile.isValid();
    }

    @Override
    public void selectNotify() {
        // for now can remain empty (called when editor is selected)
    }

    @Override
    public void deselectNotify() {
        // for now can remain empty (called when editor is deselected)
    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {
    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {
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
    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {

    }


    // TODO
    void propertyChanged(@NotNull VirtualFilePropertyEvent event) {
        if(virtualFile.equals(event.getFile())) {
            // change document
            Runnable postRunnable = () -> {
                String fileExtension = virtualFile.getExtension();
                if(fileExtension != null && fileExtension.equals("class")) {
                    setValue(virtualFile);
                }
                else {
                    setValue(null);
                    // close editor
                    FileEditorManager editorManager = FileEditorManager.getInstance(project);
                    editorManager.closeFile(virtualFile);
                }
            }; // postRunnable

            virtualFile.refresh(true, false, postRunnable);
        }
    }

    // TODO (what does this do?)
    void contentsChanged(@NotNull VirtualFileEvent event) {
        if(virtualFile.equals(event.getFile())) {
            // change document
            Runnable postRunnable = () -> setValue(virtualFile);
            RefreshQueue.getInstance().refresh(true, false, postRunnable,
                    ModalityState.current(), virtualFile);
        }
    }
}
