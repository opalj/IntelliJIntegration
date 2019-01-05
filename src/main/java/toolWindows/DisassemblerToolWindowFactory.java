package toolWindows;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import java.awt.*;
import javax.swing.*;

import org.jetbrains.annotations.NotNull;

// note: having XML, one factory class may suffice ... but keep things separated for now
public class DisassemblerToolWindowFactory implements ToolWindowFactory {

    ToolWindow toolWindow;
    JPanel toolWindowContent;
    JTextArea textArea = null;
    JScrollBar vScrollBar = null;

    public DisassemblerToolWindowFactory() {
        toolWindowContent = new JPanel();
        toolWindowContent.setLayout(new BorderLayout());

        textArea = new JTextArea("");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        toolWindowContent.add(textArea, BorderLayout.CENTER);

        JBScrollPane scrollPane = new JBScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        vScrollBar = scrollPane.getVerticalScrollBar();
        toolWindowContent.add(scrollPane);
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        WindowCommManager.getInstance().setDisassemblerInstance(this);

        this.toolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(toolWindowContent, "Content", false);
        this.toolWindow.getContentManager().addContent(content);
    }

    public void setText(String text) {
        textArea.setText(text);

        // ########################################################################### REMOVE LATER
        Project currentProject = ProjectManager.getInstance().getOpenProjects()[0];

        // JOptionPane.showMessageDialog(null,(""+currentProject.getBasePath()),"Base
        // path",JOptionPane.OK_OPTION);

        currentProject.getBasePath();
    /*FileEditorManager.getInstance(currentProject).openFile(
    LocalFileSystem.getInstance().findFileByPath("C:/Users/Marcel/IdeaProjects/intellijintegration/build/classes/java/main/Compile/Compiler.class")
    , true);*/
    }
}
