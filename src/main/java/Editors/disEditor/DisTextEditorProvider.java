package Editors.disEditor;

import JavaByteCodeLanguage.psi.JbcLineMarkerRenderer;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import globalData.GlobalData;

import java.awt.*;
import java.util.Objects;
import opalintegration.OpalUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class DisTextEditorProvider extends PsiAwareTextEditorProvider {
  @NonNls private static final String EDITOR_TYPE_ID = "OPAL-DIS";

  @Override
  public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
    // restrict to .class files only
    String fileExtension = file.getExtension();
    return (fileExtension != null)
        && (fileExtension.equals("class")
            || fileExtension.equals(GlobalData.DISASSEMBLED_FILE_ENDING_JBC));
  }

  @NotNull
  @Override
  public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
    if (!Objects.equals(file.getExtension(), GlobalData.DISASSEMBLED_FILE_ENDING_JBC)) {
      file = OpalUtil.prepare(project, GlobalData.DISASSEMBLED_FILE_ENDING_JBC, file, null);
    }

    DisTextEditor disTextEditor = new DisTextEditor(project, file, this);
    MarkupModel markupModel = disTextEditor.getEditor().getMarkupModel();
    markupModel.removeAllHighlighters();

    int startLine = 10;
    int numLines = 20;
    int endLine = startLine + numLines;
    int lineHeight = disTextEditor.getEditor().getLineHeight();
    markupModel.addRangeHighlighter(
            startLine * lineHeight,
//            disTextEditor.getEditor().getDocument().getLineCount(),   // correct? No, see below
            endLine * lineHeight,  // this is correct
            HighlighterLayer.CONSOLE_FILTER,
            null,
            HighlighterTargetArea.LINES_IN_RANGE);
        markupModel.addRangeHighlighter(
                20 * lineHeight,
                ((endLine + 1) + 30) * lineHeight,  // this is correct
            HighlighterLayer.FIRST,
            null,
            HighlighterTargetArea.LINES_IN_RANGE);

    markupModel.getAllHighlighters()[0].setLineMarkerRenderer(
            new JbcLineMarkerRenderer(JBColor.RED)
                    .setTooltipText("RedException"));

    markupModel.getAllHighlighters()[1].setLineMarkerRenderer(
            new JbcLineMarkerRenderer(JBColor.GREEN, 4, 4)
                    .setTooltipText("GreenException"));

    return disTextEditor;
  }

  @NotNull
  @Override
  public String getEditorTypeId() {
    return EDITOR_TYPE_ID;
  }

  @NotNull
  @Override
  public FileEditorPolicy getPolicy() {
    return FileEditorPolicy.NONE;
  }
}
