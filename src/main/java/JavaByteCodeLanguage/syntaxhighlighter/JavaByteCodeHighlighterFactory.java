package JavaByteCodeLanguage.syntaxhighlighter;

import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The SyntaxHighlighterFactory is used to register a syntax highlighter via the
 * com.intellij.lang.syntaxHighlighterFactory platform extension point in plugin.xml
 */
public class JavaByteCodeHighlighterFactory extends SyntaxHighlighterFactory {
  @NotNull
  @Override
  public SyntaxHighlighter getSyntaxHighlighter(
      @Nullable Project project, @Nullable VirtualFile virtualFile) {
    //    return new JavaByteCodeSyntaxHighlighter();
    return new JbcSyntaxHighlighter();
  }
}
