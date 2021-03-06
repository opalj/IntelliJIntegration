/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.Editors.disEditor;

import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorImpl;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class DisTextEditor extends PsiAwareTextEditorImpl {
  DisTextEditor(@NotNull Project project, @NotNull VirtualFile file, TextEditorProvider provider) {
    super(project, file, provider);
  }

  @Override
  @NotNull
  public String getName() {
    return "Bytecode";
  }
}
