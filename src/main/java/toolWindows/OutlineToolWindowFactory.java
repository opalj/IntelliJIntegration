package toolWindows;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.*;

import org.jetbrains.annotations.NotNull;

public class OutlineToolWindowFactory implements ToolWindowFactory {

    private final JEditorPane toolWindowContent;
    private final JFXPanel fxPanel;
    private ToolWindow toolWindow;

    public OutlineToolWindowFactory() {
        toolWindowContent = new JEditorPane();
        fxPanel = new JFXPanel();
        Platform.setImplicitExit(false);
        Platform.runLater(
                () -> {
                    Group root = new Group();
                    Scene scene = new Scene(root, Color.ALICEBLUE);
                    WebView webView = new WebView();
                    WebEngine webEngine = webView.getEngine();
                    webEngine.load("http://www.google.de/");

                    root.getChildren().add(webView);
                    fxPanel.setScene(scene);
        });
        // toolWindowContent.add(fxPanel);
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        WindowCommManager.getInstance().setOutlineInstance(this);

        this.toolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        // "Content" will appear above the panel (keep in order to remind yourself later)
        Content content = contentFactory.createContent(fxPanel, "Content", false);
        this.toolWindow.getContentManager().addContent(content);
    }
}
