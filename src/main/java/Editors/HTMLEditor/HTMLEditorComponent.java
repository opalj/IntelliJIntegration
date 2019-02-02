package Editors.HTMLEditor;

import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.codeInsight.lookup.impl.LookupImpl;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.ui.JBColor;
import com.sun.webkit.WebPage;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.lang.reflect.Field;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jetbrains.annotations.NotNull;

// DataProvider: Allows a component hosting actions to provide context information to the actions.
// CopyProvider: performs copy, enables/disables copy, shows/hide copy
public class HTMLEditorComponent extends JPanel
    implements DataProvider, Disposable, DocumentListener, KeyListener {

  private static final Color SEARCH_GOOD_BACKGROUND = new Color(255, 255, 255);
  private static final Color SEARCH_GOOD_TEXT = new Color(0, 0, 0);
  private static final Color SEARCH_BAD_BACKGROUND = new Color(255, 0, 0);
  private static final Color SEARCH_BAD_TEXT = new Color(255, 255, 255);
  private static final Font SEARCH_FONT = new Font("Arial", Font.PLAIN, 19);
  private boolean controlKeyDown = false;

  private JFXPanel fxPanel;
  private Group root;
  private Scene scene;
  private WebView webView;
  private WebEngine webEngine;
  private JTextField searchText;

  private HTMLEditor myEditor;
  private Project project;

  public WebEngine getWebEngine() {
    return webEngine;
  }

  public HTMLEditorComponent(VirtualFile html, HTMLEditor e, Project project) {
    // init the JavaFX-File
    this.project = project;
    myEditor = e;
    fxPanel = new JFXPanel();
    Platform.setImplicitExit(false);
    Platform.runLater(
        () -> {
          root = new Group();
          //          scene = new Scene(root, javafx.scene.paint.Color.web("#020202"));
          scene = new Scene(root);
          webView = new WebView();
          webEngine = webView.getEngine();
          webEngine.load("file:" + File.separator + File.separator + html.getPresentableUrl());
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
    html.getFileSystem()
        .addVirtualFileListener(
            new VirtualFileListener() {
              @Override
              public void contentsChanged(@NotNull VirtualFileEvent event) {
                Platform.setImplicitExit(false);
                Platform.runLater(
                    () -> {
                      webEngine.reload();
                    });
              }
            });
    this.addKeyListener(this);
  }

  private void setWebViewSize() {
    Platform.setImplicitExit(false);
    Platform.runLater(() -> webView.setPrefSize(this.getSize().width, this.getSize().height));
  }

  public JComponent getPreferredComponent() {
    return fxPanel;
  }

  @Override
  public void dispose() {
    fxPanel.removeAll();
    // remove all Components of UI;
    removeAll();
  }

  private void updateSearch(boolean forward) {
    if (searchText.getText().length() >= 1) {
      try {
        Field pageField = webEngine.getClass().getDeclaredField("page");
        pageField.setAccessible(true);
        WebPage page = (com.sun.webkit.WebPage) pageField.get(webEngine);

        // JOptionPane.showMessageDialog(null,page.getHtml(page.getMainFrame()),"",JOptionPane.OK_OPTION);

        boolean found = page.find(searchText.getText(), forward, true, false);
        if (found) {
          goodSearch();
        } else {
          badSearch();
        }
      } catch (Exception e) {
        /* log error could not access page */
      }
    } else {
      badSearch();
    }
  }

  private void goodSearch() {
    if (searchText != null) {
      searchText.setBackground(SEARCH_GOOD_BACKGROUND);
      searchText.setForeground(SEARCH_GOOD_TEXT);
    }
  }

  private void badSearch() {
    if (searchText != null) {
      searchText.setBackground(SEARCH_BAD_BACKGROUND);
      searchText.setForeground(SEARCH_BAD_TEXT);
    }
  }

  private void setSearchBar() {
    searchText = new JTextField("");
    searchText.getDocument().addDocumentListener(this);
    searchText.addKeyListener(this);
    searchText.setFont(SEARCH_FONT);
    this.add(searchText, BorderLayout.NORTH);
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
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
      controlKeyDown = true;
    } else if (e.getKeyCode() == KeyEvent.VK_F && controlKeyDown) {
      if (searchText == null) {
        setSearchBar();
      } else {
        updateSearch(true);
      }
    } else if (e.getKeyCode() == KeyEvent.VK_G && controlKeyDown && searchText != null) {
      updateSearch(false);
    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && searchText != null) {
      removeSearchBar();
      fxPanel.requestFocus();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
      controlKeyDown = true;
    }
  }
  // MY SHIT
  public boolean isModified() {
    return true;
  }

  @Override
  public Object getData(final String dataId) {
    if (CommonDataKeys.EDITOR.is(dataId)) {
      return myEditor;
    }
    if (PlatformDataKeys.DOMINANT_HINT_AREA_RECTANGLE.is(dataId)) {
      final LookupImpl lookup = (LookupImpl) LookupManager.getInstance(project).getActiveLookup();
      if (lookup != null && lookup.isVisible()) {
        return lookup.getBounds();
      }
    }
    return null;
  }
}
