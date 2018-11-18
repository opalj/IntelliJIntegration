package toolWindows;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

// note: having XML, one factory class may suffice ... but keep things separated for now
public class DisassemblerToolWindowFactory implements ToolWindowFactory {

    ToolWindow toolWindow;
    JLabel labelToDisplay;
    JPanel toolWindowContent;

    public DisassemblerToolWindowFactory() {
        labelToDisplay = new JLabel("Here goes the bytecode analysis");
        toolWindowContent = new JPanel();
        toolWindowContent.add(labelToDisplay);
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        WindowCommManager.getInstance().setDisassemblerInstance(this);

        this.toolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(toolWindowContent, "XYZ",false);
        this.toolWindow.getContentManager().addContent(content);
    }

    public void setText(String text) {
        labelToDisplay.setText(text);
    }

}
