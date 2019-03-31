package Actions;

import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeExceptionTableBody;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeMethodDeclaration;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodePcNumber;
import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeTypes;
import JavaByteCodeLanguage.gutter.JbcLineMarkerRenderer;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

import java.awt.*;
import java.util.LinkedList;

/**
 *  An Action for marking the try-catch-blocks for a given exception
 */
public class ExceptionMarkerAction extends AnAction {

//    @Override
//    public void update(AnActionEvent e) {
//        final Project project = e.getProject();
//        final Editor editor = e.getData(CommonDataKeys.EDITOR);
//        VirtualFile jbcFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
//        System.out.println("extension: " + jbcFile.getExtension());
//        e.getPresentation().setEnabledAndVisible(project != null && editor != null && jbcFile != null
//            && jbcFile.getExtension().equals("jbc"));
//    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (editor == null || psiFile == null || project == null || virtualFile== null) {
            return;
        }
        PsiElement elementAt = psiFile.findElementAt(editor.getCaretModel().getOffset());
        JavaByteCodeExceptionTableBody parent = PsiTreeUtil.getParentOfType(elementAt, JavaByteCodeExceptionTableBody.class);
        if (parent == null) {
            return;
        }
        LinkedList<PsiElement> psiElementList = new LinkedList<>();
        psiElementList.add(PsiTreeUtil.findSiblingForward(parent.getLbracket(), JavaByteCodeTypes.NUMBER,null));
        psiElementList.add(PsiTreeUtil.findSiblingBackward(parent.getRbracket(), JavaByteCodeTypes.NUMBER,null));
        psiElementList.add(PsiTreeUtil.findSiblingForward(parent.getRbracket(), JavaByteCodeTypes.NUMBER,null));
        if (psiElementList.size() == 3) {
            int[] lines = new int[3];
            int i = 0;
            // take the method to which this instruction belongs to...
            JavaByteCodeMethodDeclaration methodDeclaration =
                    PsiTreeUtil.getParentOfType(elementAt, JavaByteCodeMethodDeclaration.class);
            // ... and iterate over all instructions until the jump target has been found...
            //PsiTreeUtil.findChildrenOfType(methodDeclaration, JavaByteCodePcNumber.class).stream().filter(javaByteCodePcNumber -> )
            for (JavaByteCodePcNumber javaByteCodePcNumber :
                    PsiTreeUtil.findChildrenOfType(methodDeclaration, JavaByteCodePcNumber.class)) {
                // ... and set the caret accordingly
                // streams
                if (javaByteCodePcNumber.getNumber().getText().equals(psiElementList.getFirst().getText())) {
                    //lines[i] = document.getLineNumber(javaByteCodePcNumber.getTextOffset());
                    lines[i] = javaByteCodePcNumber.getTextOffset();
                    i++;
                    psiElementList.removeFirst();
                    if(psiElementList.size() == 0)
                        break;
                }
            } // for()
            setMarker(editor,lines[0],lines[1],lines[2],parent);
        } // jumpTargetPC
    }
    private void setMarker(Editor editor, int startOffset, int endOffset, int catchOffset, PsiElement sourceElement){
        MarkupModel markupModel = editor.getMarkupModel();
        markupModel.removeAllHighlighters();
        String name = sourceElement.getText().split("\\s")[sourceElement.getText().split("\\s").length-1];
        int lineHeight = editor.getLineHeight();
        // two RangeHighlighter for try'n'catch ranges
        //markupModel.addLineHighlighter(startLine,endLine,nulll)
        markupModel.addRangeHighlighter(
                startOffset ,
                endOffset,
                HighlighterLayer.CONSOLE_FILTER,
                null,
                HighlighterTargetArea.LINES_IN_RANGE);
        markupModel.addRangeHighlighter(
                catchOffset ,
                (catchOffset+1) ,  // this is correct
                HighlighterLayer.FIRST,
                null,
                HighlighterTargetArea.LINES_IN_RANGE);
        markupModel.getAllHighlighters()[0].setLineMarkerRenderer(
                new JbcLineMarkerRenderer(new Color(255,0,0,50),sourceElement)
                        .setTooltipText("try : "+name));
        markupModel.getAllHighlighters()[1].setLineMarkerRenderer(
                new JbcLineMarkerRenderer(new Color(0,255,0,50),sourceElement)
                        .setTooltipText("catch : "+name));
        editor.getCaretModel().moveToOffset(startOffset, true);
        editor.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
    }
}
