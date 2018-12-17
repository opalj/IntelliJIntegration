package HTMLEditor;

import com.intellij.openapi.Disposable;
import com.intellij.ui.JBColor;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.lang.reflect.Field;

import com.sun.webkit.WebPage;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// DataProvider: Allows a component hosting actions to provide context information to the actions.
// CopyProvider: performs copy, enables/disables copy, shows/hide copy
public class MyHtmlEditorUI extends JPanel implements Disposable, DocumentListener, KeyListener {

    private static final Color SEARCH_GOOD_BACKGROUND = new Color(255,255,255);
    private static final Color SEARCH_GOOD_TEXT = new Color(0,0,0);
    private static final Color SEARCH_BAD_BACKGROUND = new Color(255,0,0);
    private static final Color SEARCH_BAD_TEXT = new Color(255,255,255);
    private static final Font SEARCH_FONT = new Font("Arial",Font.PLAIN,19);
  private JFXPanel fxPanel;
  private Group root;
  private Scene scene;
  private WebView webView;
  private WebEngine webEngine;
  private JTextField searchText;
  private boolean controlKeyDown = false;

  public MyHtmlEditorUI(String html) {
    // init the JavaFX-File
    fxPanel = new JFXPanel();
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

    fxPanel.addKeyListener(this);
    this.add(fxPanel);
    fxPanel.addComponentListener(
        new ComponentAdapter() {
          @Override
          public void componentResized(ComponentEvent e) {
            setWebViewSize();
          }
        });

      this.addKeyListener(this);
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

  private void updateSearch(boolean forward) {
      if( searchText.getText().length() >= 1 ) {
          try {
              Field pageField = webEngine.getClass().getDeclaredField("page");
              pageField.setAccessible(true);
              WebPage page = (com.sun.webkit.WebPage) pageField.get(webEngine);

              //JOptionPane.showMessageDialog(null,page.getHtml(page.getMainFrame()),"",JOptionPane.OK_OPTION);

              boolean found = page.find(searchText.getText(), forward, true, false);
              if( found ) {
                  goodSearch();
              } else {
                  badSearch();
              }
          } catch(Exception e) { /* log error could not access page */ }
      } else {
          badSearch();
      }
  }

    private void goodSearch() {
        if( searchText != null ) {
            searchText.setBackground(SEARCH_GOOD_BACKGROUND);
            searchText.setForeground(SEARCH_GOOD_TEXT);
        }
    }

    private void badSearch() {
        if( searchText != null ) {
            searchText.setBackground(SEARCH_BAD_BACKGROUND);
            searchText.setForeground(SEARCH_BAD_TEXT);
        }
    }

    private void setSearchBar() {
        searchText = new JTextField("");
        searchText.getDocument().addDocumentListener(this);
        searchText.addKeyListener(this);
        searchText.setFont(SEARCH_FONT);
        this.add(searchText,BorderLayout.NORTH);
        this.validate();

        searchText.requestFocus();
    }

    private void removeSearchBar() {
      this.remove(searchText);
      searchText = null;
      this.validate();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateSearch(true);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateSearch(true);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateSearch(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
      if( e.getKeyCode() == KeyEvent.VK_CONTROL ) {
          controlKeyDown = true;
      } else if( e.getKeyCode() == KeyEvent.VK_F && controlKeyDown ) {
          if( searchText == null ) {
              setSearchBar();
          } else {
              updateSearch(true);
          }
      } else if( e.getKeyCode() == KeyEvent.VK_G && controlKeyDown && searchText != null ) {
          updateSearch(false);
      } else if( e.getKeyCode() == KeyEvent.VK_ESCAPE && searchText != null ) {
          removeSearchBar();
          fxPanel.requestFocus();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if( e.getKeyCode() == KeyEvent.VK_CONTROL ) {
            controlKeyDown = true;
        }
    }
}
