package HTMLEditor;

import com.intellij.openapi.Disposable;
import com.intellij.ui.JBColor;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JComponent;
import javax.swing.JPanel;

// DataProvider: Allows a component hosting actions to provide context information to the actions.
// CopyProvider: performs copy, enables/disables copy, shows/hide copy
public class MyHtmlEditorUI extends JPanel implements Disposable {

  private JFXPanel fxPanel;
  private Group root;
  private Scene scene;
  private WebView webView;
  private WebEngine webEngine;

  public MyHtmlEditorUI(String html) {
    // init the JavaFX-File
    JFXPanel fxPanel = new JFXPanel();
    Platform.setImplicitExit(false);
    Platform.runLater(
        () -> {
          root = new Group();
          scene = new Scene(root);
          webView = new WebView();
          webEngine = webView.getEngine();
          webEngine.loadContent(html);
          root.getChildren().add(webView);
          fxPanel.setScene(scene);
        });
    this.setLayout(new BorderLayout());
    this.setBackground(JBColor.background());
    this.add(fxPanel);
    fxPanel.addComponentListener(
        new ComponentAdapter() {
          @Override
          public void componentResized(ComponentEvent e) {
            setWebViewSize();
          }
        });
  }

  public JComponent getContentComponent() {
    return fxPanel;
  }

  private void setWebViewSize() {
    Platform.setImplicitExit(false);
    Platform.runLater(
        () -> {
          webView.setPrefSize(this.getSize().width, this.getSize().height);
        });
  }

  public void setWebSite(String filepath) {
    Platform.setImplicitExit(false);
    Platform.runLater(
        () -> {
          webEngine.load(filepath);
        });
  }

  @Override
  public void dispose() {
    // TODO: dispose of this editor

    // from Container ("grandpa of JPanel")
    removeAll();

    fxPanel = null;
    webEngine = null;
    webView = null;
    scene = null;
    root = null;
  }
}
