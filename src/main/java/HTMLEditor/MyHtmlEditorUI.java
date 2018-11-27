package HTMLEditor;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MyHtmlEditorUI extends JPanel {

    private JFXPanel fxPanel;
    private Group root;
    private Scene scene;
    private WebView webView;
    private WebEngine webEngine;

    public MyHtmlEditorUI() {
        // init the JavaFX-File
        JFXPanel fxPanel = new JFXPanel();
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            root = new Group();
            scene = new Scene(root);
            webView = new WebView();
            webEngine = webView.getEngine();
            webEngine.load("http://www.google.de/");
            root.getChildren().add(webView);
            fxPanel.setScene(scene);
        });
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.add(fxPanel);
        fxPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setWebViewSize();
            }
        });
    }

    public JComponent getContentComponent() {
        return fxPanel;
    }

    private void setWebViewSize(){
        Platform.setImplicitExit(false);
        Platform.runLater(
                () -> {

            webView.setPrefSize(this.getSize().width,this.getSize().height);
        });



    }}
