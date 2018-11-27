package toolWindows;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;



public class OutlineToolWindowFactory implements ToolWindowFactory {

    private final JEditorPane toolWindowContent;
    private ToolWindow toolWindow;
    public OutlineToolWindowFactory() {
        toolWindowContent = new JEditorPane();
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        WindowCommManager.getInstance().setOutlineInstance(this);

        this.toolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        // "Content" will appear above the panel (keep in order to remind yourself later)
        Content content = contentFactory.createContent(toolWindowContent, "Content", false);
        this.toolWindow.getContentManager().addContent(content);
    }
}
