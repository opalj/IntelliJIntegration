package Components;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.*;
import globalData.GlobalData;
import opalintegration.Opal;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.DocumentListener;
import java.util.Arrays;
import java.util.EventListener;

public class OpalVirtualFileListener implements ProjectComponent {
    private static Project project;
    public OpalVirtualFileListener(Project project){
        this.project = project;
        myVirtualFileListener myVirtualFileListener = new myVirtualFileListener();
        VirtualFileManager.getInstance().addVirtualFileListener(myVirtualFileListener);
    }

    private static class myVirtualFileListener implements VirtualFileListener {

        public void contentsChanged(@NotNull VirtualFileEvent event) {
            if(event.getFile().getExtension().equals(StdFileTypes.CLASS.getDefaultExtension())){
                //Messages.showMessageDialog("Classfile "+event.getFile().getName()+" contents changed","property",Messages.getInformationIcon());
                Arrays.stream(FileEditorManager.getInstance(project).getEditors(event.getFile()))
                        .filter((e)->!e.getFile().getExtension().equals("class"))
                        .forEach(e -> {Opal.prepare(e.getFile().getExtension(),event.getFile(),e.getFile());e.getFile().refresh(false,false);});
            }
        }

    }
}
