/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.JavaByteCodeLanguage.gutter;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.markup.ActiveGutterRenderer;
import com.intellij.psi.PsiElement;
import java.awt.*;
import java.awt.event.MouseEvent;
import org.jetbrains.annotations.Nullable;

public class JbcLineMarkerRenderer implements ActiveGutterRenderer {
  private static final int THICKNESS = 6;
  private final PsiElement sourceElement;

  private final int thickness;
  private final Color color;
  private String tooltipText;

  public JbcLineMarkerRenderer(Color color, PsiElement sourceElement) {
    this(color, THICKNESS, sourceElement);
  }

  private JbcLineMarkerRenderer(Color color, int thickness, PsiElement sourceElement) {
    this.color = color;
    this.thickness = thickness;
    this.tooltipText = "";
    this.sourceElement = sourceElement;
  }

  public JbcLineMarkerRenderer setTooltipText(String tooltipText) {
    this.tooltipText = tooltipText;
    return this;
  }

  @Override
  public void paint(Editor editor, Graphics g, Rectangle r) {
    g.setColor(color);
    g.fillRect(r.x, r.y, thickness, r.height);
  }

  @Override
  public void doAction(Editor editor, MouseEvent e) {
    //  scroll to exception table
    if (e.getClickCount() > 0) {
      editor.getCaretModel().moveToOffset(sourceElement.getTextOffset());
      editor.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
    }
  }

  @Override
  public boolean canDoAction(MouseEvent e) {
    return tooltipText != null && !tooltipText.isEmpty();
  }

  @Nullable
  @Override
  public String getTooltipText() {
    return tooltipText;
  }
}
