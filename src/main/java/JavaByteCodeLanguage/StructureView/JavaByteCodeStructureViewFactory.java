/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package JavaByteCodeLanguage.StructureView;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The PsiStructureViewFactory is used to register a structure view via the
 * com.intellij.lang.psiStructureViewFactory extension point in plugin.xml
 */
public class JavaByteCodeStructureViewFactory implements PsiStructureViewFactory {
  @Nullable
  @Override
  public StructureViewBuilder getStructureViewBuilder(@NotNull PsiFile psiFile) {
    return new TreeBasedStructureViewBuilder() {
      @NotNull
      @Override
      public StructureViewModel createStructureViewModel(@Nullable Editor editor) {
        return new JavaByteCodeStructureViewModel(psiFile);
      }
    };
  }
}
