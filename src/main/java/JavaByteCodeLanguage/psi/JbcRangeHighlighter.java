package JavaByteCodeLanguage.psi;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class JbcRangeHighlighter implements RangeHighlighter {

    int start;
    int end;
    LineMarkerRenderer lineMarkerRenderer;
    Color errorStripeColor;
    String errorToolTip;
    HighlighterTargetArea targetArea;

    public JbcRangeHighlighter(int start, int end, HighlighterTargetArea targetArea) {
        this.start = start;
        this.end = end;
        this.targetArea = targetArea;
    }

    @Override
    public int getLayer() {
        return HighlighterLayer.ERROR;
    }

    @NotNull
    @Override
    public HighlighterTargetArea getTargetArea() {
        return targetArea;
    }

    @Nullable
    @Override
    public TextAttributes getTextAttributes() {
        return null;
    }

    @Nullable
    @Override
    public LineMarkerRenderer getLineMarkerRenderer() {
        return lineMarkerRenderer;
    }

    @Override
    public void setLineMarkerRenderer(@Nullable LineMarkerRenderer renderer) {
        lineMarkerRenderer = renderer;
    }

    @Nullable
    @Override
    public CustomHighlighterRenderer getCustomRenderer() {
        return null;
    }

    @Override
    public void setCustomRenderer(CustomHighlighterRenderer renderer) {

    }

    @Nullable
    @Override
    public GutterIconRenderer getGutterIconRenderer() {
        return null;
    }

    @Override
    public void setGutterIconRenderer(@Nullable GutterIconRenderer renderer) {

    }

    @Nullable
    @Override
    public Color getErrorStripeMarkColor() {
        return errorStripeColor;
    }

    @Override
    public void setErrorStripeMarkColor(@Nullable Color color) {
        errorStripeColor = color;
    }

    @Nullable
    @Override
    public Object getErrorStripeTooltip() {
        return errorToolTip;
    }

    @Override
    public void setErrorStripeTooltip(@Nullable Object tooltipObject) {
        errorToolTip = tooltipObject.toString();
    }

    @Override
    public boolean isThinErrorStripeMark() {
        return false;
    }

    @Override
    public void setThinErrorStripeMark(boolean value) {

    }

    @Nullable
    @Override
    public Color getLineSeparatorColor() {
        return null;
    }

    @Override
    public void setLineSeparatorColor(@Nullable Color color) {

    }

    @Override
    public void setLineSeparatorRenderer(LineSeparatorRenderer renderer) {

    }

    @Override
    public LineSeparatorRenderer getLineSeparatorRenderer() {
        return null;
    }

    @Nullable
    @Override
    public SeparatorPlacement getLineSeparatorPlacement() {
        return null;
    }

    @Override
    public void setLineSeparatorPlacement(@Nullable SeparatorPlacement placement) {

    }

    @Override
    public void setEditorFilter(@NotNull MarkupEditorFilter filter) {

    }

    @NotNull
    @Override
    public MarkupEditorFilter getEditorFilter() {
        return null;
    }

    @NotNull
    @Override
    public Document getDocument() {
        return null;
    }

    @Override
    public int getStartOffset() {
        return start;
    }

    @Override
    public int getEndOffset() {
        return end;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void setGreedyToLeft(boolean greedy) {

    }

    @Override
    public void setGreedyToRight(boolean greedy) {

    }

    @Override
    public boolean isGreedyToRight() {
        return false;
    }

    @Override
    public boolean isGreedyToLeft() {
        return false;
    }

    @Override
    public void dispose() {

    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {

    }
}
