package Actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.LineMarkerRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.awt.*;

public class ExceptionMarkerAction extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        VirtualFile jbcFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        System.out.println("extension: " + jbcFile.getExtension());
        e.getPresentation().setEnabled(project != null && editor != null && jbcFile != null
            && jbcFile.getExtension().equals("jbc"));


        for(int i=0; i < 10; ++i) {
            editor.getMarkupModel().addLineHighlighter(i, HighlighterLayer.ERROR, null);
        }
        editor.getMarkupModel().getAllHighlighters()[0].setLineMarkerRenderer(new ExceptionLineMarkerRenderer(Color.RED));

        System.out.println("here");
        if(e.getPresentation().isEnabled()) {
            actionPerformed(e);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        VirtualFile jbcFile = e.getData(CommonDataKeys.VIRTUAL_FILE);

        System.out.println("here2");
        for(int i=0; i < 10; ++i) {
            editor.getMarkupModel().addLineHighlighter(i, HighlighterLayer.ERROR, null);
        }

        editor.getMarkupModel().addRangeHighlighter(0, editor.getLineHeight(), HighlighterLayer.ERROR,
                null, HighlighterTargetArea.EXACT_RANGE);
        editor.getMarkupModel().getAllHighlighters()[0].setLineMarkerRenderer(new ExceptionLineMarkerRenderer(Color.RED));
    }

    class ExceptionLineMarkerRenderer implements LineMarkerRenderer {
        private static final int DEEPNESS = 0;
        private static final int THICKNESS = 5;
        private final Color myColor;

        public ExceptionLineMarkerRenderer(Color color) {
            myColor = color;
        }

        @Override
        public void paint(Editor editor, Graphics g, Rectangle r) {
            int height = r.height + editor.getLineHeight();
            g.setColor(myColor);
            g.fillRect(r.x, r.y, THICKNESS, height);
            g.fillRect(r.x + THICKNESS, r.y, DEEPNESS, THICKNESS);
            g.fillRect(r.x + THICKNESS, r.y + height - THICKNESS, DEEPNESS, THICKNESS);
        }
    }
}
