/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package taclanguage.structurview;

import com.intellij.ide.structureView.*;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import taclanguage.psi.TAC_file;

class TAC_structureViewModel extends StructureViewModelBase
    implements StructureViewModel.ElementInfoProvider {
  public TAC_structureViewModel(PsiFile psiFile) {
    super(psiFile, new TAC_structureViewElement(psiFile));
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
    return element instanceof TAC_file;
  }
}
