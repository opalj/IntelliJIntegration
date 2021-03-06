/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.taclanguage.syntaxhighlighter;

import com.intellij.openapi.fileTypes.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class TAC_syntaxHighlighterFactory extends SyntaxHighlighterFactory {
  @NotNull
  @Override
  public TAC_syntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile) {
    return new TAC_syntaxHighlighter();
  }
}
