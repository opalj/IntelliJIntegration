package Actions;

import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeJType;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.Objects;
import taclanguage.autogenerated.psi.TACJType;

class JumpToResolve extends AnAction {
  private final String editorName;

  JumpToResolve(String editorName) {
    this.editorName = editorName;
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    Project project = e.getProject();
    Editor editor = e.getData(CommonDataKeys.EDITOR);
    PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
    if (editor == null || psiFile == null || project == null) {
      return;
    }
    PsiElement elementAt = psiFile.findElementAt(editor.getCaretModel().getOffset());
    PsiElement fullyQualifiedClass =
        "OPAL-DIS".equals(editorName)
            ? PsiTreeUtil.getParentOfType(elementAt, JavaByteCodeJType.class)
            : PsiTreeUtil.getParentOfType(elementAt, TACJType.class);
    if (fullyQualifiedClass != null) {
      // e.g. java.lang.String -> resolve = String
      PsiElement resolve =
          fullyQualifiedClass.getReferences()[fullyQualifiedClass.getReferences().length - 1]
              .resolve();
      PsiFile resolvePsiFile = Objects.requireNonNull(resolve).getContainingFile();
      VirtualFile resolvedVirtualFile = resolvePsiFile.getVirtualFile();
      FileEditorManager.getInstance(project).openFile(resolvedVirtualFile, true);
      FileEditorManager.getInstance(project).setSelectedEditor(resolvedVirtualFile, "OPAL-DIS");
      return;
    }
    Notifications.Bus.notify(
        new NotificationGroup("OpalPlugin", NotificationDisplayType.BALLOON, false)
            .createNotification()
            .setContent("can't find navigation path"));
  }
}
