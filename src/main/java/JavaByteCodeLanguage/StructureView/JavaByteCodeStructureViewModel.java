/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package JavaByteCodeLanguage.StructureView;

import JavaByteCodeLanguage.LanguageAndFileType.JavaByteCode;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.TextEditorBasedStructureViewModel;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/** @see TextEditorBasedStructureViewModel */
class JavaByteCodeStructureViewModel extends StructureViewModelBase
    implements StructureViewModel.ElementInfoProvider {

  public JavaByteCodeStructureViewModel(@NotNull PsiFile psiFile) {
    super(psiFile, new JavaByteCodeStructureViewElement(psiFile));
  }

  @NotNull
  public Sorter[] getSorters() {
    return new Sorter[] {Sorter.ALPHA_SORTER};
  }

  @Override
  public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
    return false;
  }

  @Override
  public boolean isAlwaysLeaf(StructureViewTreeElement element) {
    return element instanceof JavaByteCode;
  }
}
