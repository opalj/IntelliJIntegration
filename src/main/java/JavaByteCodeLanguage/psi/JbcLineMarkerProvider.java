package JavaByteCodeLanguage.psi;

import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeMethodDeclaration;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.openapi.editor.markup.*;
import com.intellij.psi.PsiElement;

import com.intellij.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Collection;
import java.util.List;

public class JbcLineMarkerProvider implements LineMarkerProvider {

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement element) {
        if(element instanceof JavaByteCodeMethodDeclaration) {
            LineMarkerInfo lineMarkerInfo = new LineMarkerInfo(
              element,
              element.getTextRange(),
                    Icons.CLASS_ICON,
                    0,
                    null,
                    null,
                    GutterIconRenderer.Alignment.CENTER
            );

            lineMarkerInfo.highlighter = new JbcRangeHighlighter(0, 100, HighlighterTargetArea.EXACT_RANGE);
            lineMarkerInfo.highlighter.setLineMarkerRenderer(new JbcLineMarkerRenderer(Color.RED));

            return lineMarkerInfo;
        }

        RelatedItemLineMarkerProvider s;

        return null;
    }

    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> elements, @NotNull Collection<LineMarkerInfo> result) {

    }
}
