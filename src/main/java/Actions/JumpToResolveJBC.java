package Actions;

import JavaByteCodeLanguage.autoGenerated.psi.JavaByteCodeJType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.Objects;

public class JumpToResolveJBC extends JumpToResolve {
  private JumpToResolveJBC() {
    super("OPAL-DIS");
  }

  @Override
  public void update(AnActionEvent e) {
    final VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
    Editor editor = e.getData(CommonDataKeys.EDITOR);
    PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
    final String extension = virtualFile != null ? virtualFile.getExtension() : "";
    PsiElement element =
        Objects.requireNonNull(psiFile)
            .findElementAt(Objects.requireNonNull(editor).getCaretModel().getOffset());
    element = PsiTreeUtil.getParentOfType(element, JavaByteCodeJType.class);
    e.getPresentation().setEnabledAndVisible(element != null && "jbc".equals(extension));
  }
}
