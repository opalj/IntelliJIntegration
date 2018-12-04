package HTMLEditor;

import Compile.Compiler;
import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.*;
import com.intellij.openapi.vfs.newvfs.RefreshQueue;
import globalData.GlobalData;
import opalintegration.Opal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import saveFile.SaveFile;
import saveFile.exceptions.ErrorWritingFileException;
import saveFile.exceptions.InputNullException;
import saveFile.exceptions.IsNotAFileException;
import saveFile.exceptions.NotEnoughRightsException;
import toolWindows.DisassemblerToolWindowFactory;
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

    private FileEditorState fileEditorState;
    private final Project project;
    private final VirtualFile virtualFile;
    private boolean disposed;


    DataProvider dt;

    public MyHtmlEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) throws IOException {
        this.project = project;
        this.virtualFile = virtualFile;

        // TODO first compile stuff
        // 1. compile project file
        // 2. OPAL: toHtml()
        // 3. return html-file (with LoadFile)
        // 4. pass file to editor
        String html = prepareHtml(project, virtualFile);

        editorUI = new MyHtmlEditorUI(html);
        showTAC(project, virtualFile);
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

    // TODO - not sure if needed
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



    // =================================================================

    void showTAC(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        String tac = Opal.ThreeWayDisAssemblerString(virtualFile.getPath());
        WindowCommManager wcm = WindowCommManager.getInstance();
//        wcm.setDisassemblerInstance(new DisassemblerToolWindowFactory());
        wcm.setDisassemblerText(tac);
    }

    String prepareHtml(@NotNull Project project, @NotNull VirtualFile virtualFile) throws IOException {
        // All files selected in the "Project"-View
        if(project != null && Compiler.make(project)) {
            String classPath = virtualFile.getPath();

            // Decompile class-file
            String dec = null;
            dec = Opal.toHTMLForm(classPath);
//            WindowCommManager.getInstance().setDisassemblerText(dec);

            // Save the decompiled code to a file
            String basePath = project.getBasePath();

            File baseDir = new File(basePath);
            File temp = (new File(classPath)).getParentFile();
            ArrayList<String> dirNames = new ArrayList<String>();
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
                SaveFile.saveFile(dec, disassembledFile.getAbsolutePath());
            } catch (InputNullException e0) {
            } catch (NotEnoughRightsException e1) {
            } catch (IsNotAFileException e2) {
            } catch (ErrorWritingFileException e3) {
            }

            // Open the just saved file in an editor

            FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
            fileEditorManager.openFile(
                    LocalFileSystem.getInstance().refreshAndFindFileByIoFile(disassembledFile)
                    , true);

            return dec;
        }

        return null;
    } // prepareHtml()


    final String getEnding(String fileName) {
        if( fileName.contains(".") ) {
            String [] parts = fileName.split("\\.");
            return parts[(parts.length - 1)];
        } else {
            return null;
        }
    }
}
