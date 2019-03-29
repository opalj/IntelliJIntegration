package JavaByteCodeLanguage.psi;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.ActiveGutterRenderer;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.event.MouseEvent;

public class JbcLineMarkerRenderer implements ActiveGutterRenderer {
    private static final int DEEPNESS = 5;
    private static final int THICKNESS = 7;

    private int deepness;
    private int thickness;
    private Color color;
    private String tooltipText;

    // TODO: just for testing purposes, make this nicer ... or rather: make it work
    // note: it seems that you can move it around (tooltip shows), but it won't highlight then
    // (i.e. no color will be seen)
    int moreX = 0;

    public JbcLineMarkerRenderer(Color color) {
        this(color, DEEPNESS, THICKNESS);
    }

    public JbcLineMarkerRenderer(Color color, int deepness, int thickness) {
        this.color = color;
        this.deepness = deepness;
        this.thickness = thickness;
        this.tooltipText = "";
    }

    public JbcLineMarkerRenderer setTooltipText(String tooltipText) {
        this.tooltipText = tooltipText;
        return this;
    }

    public JbcLineMarkerRenderer setMoreX(int moreX) {
        this.moreX = moreX;
        return this;
    }

    @Override
    public void paint(Editor editor, Graphics g, Rectangle r) {
        System.out.println("line height = " + editor.getLineHeight());
        System.out.println("Rectangle: " + r.toString());
        System.out.println("Editor: " + editor.getDocument().getText(new TextRange(0, 50)));

        int height = r.height + editor.getLineHeight() * 0;
        g.setColor(color);
        g.fillRect(r.x + moreX, r.y, thickness + moreX, height);

        // these are used to mark the beginning and end
        g.setColor(Color.BLACK);
//        moreX = 0;
        g.fillRect(r.x + thickness + moreX, r.y, deepness + moreX, thickness);
        g.fillRect(r.x + thickness + moreX, r.y + height - thickness, deepness + moreX, thickness);
    }

    @Override
    public void doAction(Editor editor, MouseEvent e) {
        // maybe scroll to exception table?
    }

    @Override
    public boolean canDoAction(MouseEvent e) {
        return !tooltipText.isEmpty();
    }

    @Nullable
    @Override
    public String getTooltipText() {
        return tooltipText;
    }
}
