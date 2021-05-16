/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.taclanguage.structurview;

import com.intellij.ide.structureView.*;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.*;

public class TAC_structureViewFactory implements PsiStructureViewFactory {

  @Nullable
  @Override
  public StructureViewBuilder getStructureViewBuilder(@NotNull final PsiFile psiFile) {
    return new TreeBasedStructureViewBuilder() {
      @NotNull
      @Override
      public StructureViewModel createStructureViewModel(@Nullable Editor editor) {
        return new TAC_structureViewModel(psiFile);
      }
    };
  }
}
