package Components;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.ui.EditorTextField;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class DragDropJars implements ApplicationComponent {
  @Override
  public void initComponent() {
    AWTEventListener awtEventListener =
        new AWTEventListener() {

          @Override
          public void eventDispatched(AWTEvent event) {
            if (event instanceof MouseEvent) {
              MouseEvent mouseEvent = (MouseEvent) event;
              JComponent jComponent = (JComponent) mouseEvent.getSource();
              if (jComponent instanceof EditorTextField) {
                EditorTextField editorTextField = (EditorTextField) jComponent;
                TransferHandler transferHandler = editorTextField.getTransferHandler();
                if (transferHandler != null) {}
              }
            }
          }
        };
    Toolkit.getDefaultToolkit()
        .addAWTEventListener(
            awtEventListener, AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK);
  }
}
