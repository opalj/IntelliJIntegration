package HTMLEditor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.awt.*;

public class HtmlEditorAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);

        FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        VirtualFile virtualFile = project.getProjectFile();
        fileEditorManager.openFile(virtualFile, true);

        Color defaultScheme = editor.getColorsScheme().getDefaultBackground();
        Messages.showInfoMessage(defaultScheme + "", "Color Scheme");

        // make sure it's a ".class" file!
//        FileEditor htmlEditor = new MyHtmlEditor(project, virtualFile);
        MyFileEditorProvider provider = new MyFileEditorProvider();
        provider.createEditor(project, virtualFile);    // this will call MyHtmlEditor() !!
    }
}
