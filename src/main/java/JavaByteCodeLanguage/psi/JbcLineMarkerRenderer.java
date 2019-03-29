package JavaByteCodeLanguage.psi;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.LineMarkerRenderer;

import java.awt.*;

public class JbcLineMarkerRenderer implements LineMarkerRenderer {
    private static final int DEEPNESS = 0;
    private static final int THICKNESS = 1;
    Color color;

    public JbcLineMarkerRenderer(Color color) {
        this.color = color;
    }

    @Override
    public void paint(Editor editor, Graphics g, Rectangle r) {
        int height = r.height + editor.getLineHeight();
        g.setColor(color);
        g.fillRect(r.x, r.y, THICKNESS, height);
        g.fillRect(r.x + THICKNESS, r.y, DEEPNESS, THICKNESS);
        g.fillRect(r.x + THICKNESS, r.y + height - THICKNESS, DEEPNESS, THICKNESS);
    }
}
