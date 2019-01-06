package HTMLEditor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import opalintegration.Opal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;

/*
 * @example: https://github.com/JetBrains/intellij-community/blob/master/images/src/org/intellij/images/editor/impl/ImageEditorImpl.java
 */
public class MyHtmlEditor implements FileEditor {

    private final MyHtmlEditorUI editorUI;
    private final VirtualFile virtualFile; // HTMLVF
    private final Project project;
    private boolean disposed;

    public MyHtmlEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        this.project = project;
        this.virtualFile = Opal.prepareHtml(project, virtualFile);
        editorUI = new MyHtmlEditorUI(this.virtualFile);
        Disposer.register(this, editorUI);
    }

    // TODO needed or n ot?
    public Project getProject() {
        return project;
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return editorUI;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return editorUI.getPreferdComponent();
    }

    @NotNull
    @Override
    public String getName() {
        return "OPAL HTML";
    }

    @Override
    public void setState(@NotNull FileEditorState state) {
        // TODO
        //        fileEditorState = state;
    }

    @Override
    public boolean isModified() {
        // TODO
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
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {
    }

    @Override
    public VirtualFile getFile() { return virtualFile; }
    StructureViewBuilder structureViewBuilder;
    @Nullable
    @Override
    public StructureViewBuilder getStructureViewBuilder() {
//    LanguageFileTypeStructureViewBuilderProvider structureViewBuilderProvider =
//        new LanguageFileTypeStructureViewBuilderProvider();
//
//    FileType xHtmlFileType = XHtmlFileType.INSTANCE;
//    xHtmlFileType = StdFileTypes.JS;
//    structureViewBuilder =
//        structureViewBuilderProvider.getStructureViewBuilder(xHtmlFileType, virtualFile, project);
//
//    return structureViewBuilder;

        // this creates a HTML-structure-view
//        PsiFile psiFile = PsiFileFactory.getInstance(project).createFileFromText(
//                StdLanguages.XHTML, prepareHtml(project, virtualFile)
//        );

       PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
        StructureViewTreeElement root;
        root = new MyStructureViewTreeElement(false, (XmlFile)psiFile);
        Messages.showInfoMessage("root = " + root, "HtmlEditor#StructViewBuilder");
        structureViewBuilder = new MyStructureViewBuilder(psiFile, root);

        return structureViewBuilder;
    }
}
