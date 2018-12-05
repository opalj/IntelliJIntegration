package HTMLEditor;

import Compile.Compiler;
import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.*;
import globalData.GlobalData;
import opalintegration.Opal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import saveFile.SaveFile;
import saveFile.exceptions.ErrorWritingFileException;
import saveFile.exceptions.InputNullException;
import saveFile.exceptions.IsNotAFileException;
import saveFile.exceptions.NotEnoughRightsException;
import toolWindows.WindowCommManager;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/*
 * @example: https://github.com/JetBrains/intellij-community/blob/master/images/src/org/intellij/images/editor/impl/ImageEditorImpl.java
 */
public class MyHtmlEditor implements FileEditor {

    private final MyHtmlEditorUI editorUI;
    private final VirtualFile virtualFile;
    private boolean disposed;


    public MyHtmlEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        this.virtualFile = virtualFile;

        String html = prepareHtml(project, virtualFile);
        editorUI = new MyHtmlEditorUI(html);

        // show TAC (currently all methods) in toolWindow
        // TODO: show TAC only for selected methods
        showTAC(project, virtualFile);
    }

    @NotNull
    @Override
    public JComponent getComponent() {
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

    // =================================================================

    private void showTAC(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        String tac = Opal.ThreeWayDisAssemblerString(virtualFile.getPath());
        WindowCommManager wcm = WindowCommManager.getInstance();
        wcm.setDisassemblerText(tac);
    }

    // 1. compile project file
    // 2. OPAL: toHtml()
    // 3. return html-file
    // 4. pass file to editor
    // TODO: this currently does more (e.g. create dir for disassembled files)
    private String prepareHtml(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        // All files selected in the "Project"-View
        if(Compiler.make(project)) {
            String classPath = virtualFile.getPath();

            // get the HTML format of the class file
            String classHtmlForm = Opal.toHTMLForm(classPath);

            // Save the decompiled code to a file
            String basePath = project.getBasePath();

            File baseDir = new File(basePath);
            File temp = (new File(classPath)).getParentFile();
            ArrayList<String> dirNames = new ArrayList<>();
            while (!temp.getAbsolutePath().equals(baseDir.getAbsolutePath())) {
                dirNames.add(temp.getName());
                temp = temp.getParentFile();
            }

            File disassembledDir = new File(basePath + File.separator + GlobalData.DISASSEMBLED_FILES_DIR);
            if (!disassembledDir.exists()) {
                disassembledDir.mkdir();
            }

            temp = new File(disassembledDir.getAbsolutePath());
            for (int i = 0; i < dirNames.size(); i++) {
                temp = new File(temp.getAbsolutePath() + File.separator + dirNames.get(dirNames.size() - (i + 1)));
                if (!temp.exists()) {
                    temp.mkdir();
                }
            }

            File classFile = new File(classPath);
            String noEnding = classFile.getName();
            if (noEnding.contains(".")) {
                String[] parts = noEnding.split("\\.");
                String tempNoEnding = null;
                for (int i = 0; i < (parts.length - 1); i++) {
                    if (i == 0) {
                        tempNoEnding = parts[0];
                    } else {
                        tempNoEnding = (tempNoEnding + "." + parts[i]);
                    }
                }
                noEnding = tempNoEnding;
            }
            File disassembledFile = new File(temp.getAbsolutePath() + File.separator + noEnding + "." +
                    GlobalData.DISASSEMBLED_FILE_ENDING);

            if (!disassembledFile.exists()) {
                try {
                    disassembledFile.createNewFile();
                } catch (IOException e) {
                }
            }

            try {
                SaveFile.saveFile(classHtmlForm, disassembledFile.getAbsolutePath());
            } catch (InputNullException e0) {
            } catch (NotEnoughRightsException e1) {
            } catch (IsNotAFileException e2) {
            } catch (ErrorWritingFileException e3) {
            }

            return classHtmlForm;
        }

        return null;
    } // prepareHtml()
}
