/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package Actions;

import static globalData.GlobalData.BYTECODE_EDITOR_ID;
import static globalData.GlobalData.TAC_EDITOR_ID;

import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeMethodHead;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import java.util.Objects;
import taclanguage.autogenerated.psi.TACMethodHead;

public class JumpBetweenEditors extends AnAction {

  private Class<? extends PsiElement> srcMethodName;
  private Class<? extends PsiElement> destMethodName;
  private String srcEditorName;
  private String destEditorId;
  private Project project;
  private Editor editor;
  private PsiFile psiFile;

  @Override
  public void update(AnActionEvent e) {
    project = e.getProject();
    editor = e.getData(CommonDataKeys.EDITOR);
    psiFile = e.getData(CommonDataKeys.PSI_FILE);
    if (project == null || editor == null || psiFile == null) {
      e.getPresentation().setEnabledAndVisible(false);
      return;
    }
    VirtualFile virtualFile = psiFile.getVirtualFile();
    PsiElement element = psiFile.findElementAt(editor.getCaretModel().getOffset());
    if (virtualFile.getExtension() == null) {
      e.getPresentation().setEnabledAndVisible(false);
    }
    // jbc strat
    else if (virtualFile.getExtension().equals("jbc")) {
      srcMethodName = JavaByteCodeMethodHead.class;
      destMethodName = TACMethodHead.class;
      srcEditorName = "Bytecode";
      destEditorId = TAC_EDITOR_ID;
      element = PsiTreeUtil.getParentOfType(element, JavaByteCodeMethodHead.class);
      e.getPresentation().setEnabledAndVisible(element != null);
    }
    // tac strat
    else if (virtualFile.getExtension().equals("tac")) {
      srcMethodName = TACMethodHead.class;
      destMethodName = JavaByteCodeMethodHead.class;
      srcEditorName = "TAC";
      destEditorId = BYTECODE_EDITOR_ID;
      element = PsiTreeUtil.getParentOfType(element, TACMethodHead.class);
      e.getPresentation().setEnabledAndVisible(element != null);
    } else {
      e.getPresentation().setEnabledAndVisible(false);
    }
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    PsiElement elementAt = PsiUtil.getElementAtOffset(psiFile, editor.getCaretModel().getOffset());
    PsiElement methodHead = PsiTreeUtil.getParentOfType(elementAt, srcMethodName);
    String fileNameWithoutExtension = psiFile.getVirtualFile().getNameWithoutExtension();
    FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);

    VirtualFile classFile = null;
    for (VirtualFile vf : fileEditorManager.getOpenFiles()) {
      if (vf.getNameWithoutExtension().equals(fileNameWithoutExtension)) {
        classFile = vf;
        break;
      }
    }

    if (classFile == null) {
      return;
    }

    FileEditor[] fileEditors = fileEditorManager.getAllEditors(classFile);
    for (FileEditor fileEditor : fileEditors) {
      if (fileEditor.getName().equals(srcEditorName)) {
        fileEditorManager.openFile(classFile, true);
        fileEditorManager.setSelectedEditor(classFile, destEditorId);
        // the destination editor
        FileEditor selectedEditor = fileEditorManager.getSelectedEditor();
        if (selectedEditor == null || selectedEditor.getFile() == null) {
          return;
        }
        // the destination file
        PsiFile otherFile = PsiManager.getInstance(project).findFile(selectedEditor.getFile());
        if (otherFile == null) {
          return;
        }
        otherFile.accept(
            new PsiElementVisitor() {
              @Override
              public void visitElement(PsiElement element) {
                super.visitElement(element);
                // ~= element instanceof SomeClass (e.g. JavaByteCodeMethodName)
                if (destMethodName.isInstance(element)) {
                  if (element.getText().equals(Objects.requireNonNull(methodHead).getText())) {
                    Objects.requireNonNull(fileEditorManager.getSelectedTextEditor())
                        .getCaretModel()
                        .moveToOffset(element.getTextOffset());
                    fileEditorManager
                        .getSelectedTextEditor()
                        .getScrollingModel()
                        .scrollToCaret(ScrollType.CENTER);
                  }
                } else {
                  for (PsiElement child : element.getChildren()) {
                    visitElement(child);
                  }
                }
              }
            }); // tacFile.accept()

        return;
      } // if(name.equals("TAC))
    } // for(fileEditors)
  }
}