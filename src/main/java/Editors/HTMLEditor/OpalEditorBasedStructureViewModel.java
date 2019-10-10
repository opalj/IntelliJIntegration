/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package Editors.HTMLEditor;

import com.intellij.ide.structureView.FileEditorPositionListener;
import com.intellij.ide.structureView.ModelListener;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Filter;
import com.intellij.ide.util.treeView.smartTree.Grouper;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// _TODO: currently many methods unimplemented, how much of them is needed ?
@Deprecated
public class OpalEditorBasedStructureViewModel implements StructureViewModel {
  private final HTMLEditor myEditor;
  private final PsiFile myPsiFile;
  private final StructureViewTreeElement myRoot;

  // Editor should be Editors.disEditor.HTMLEditor !!
  public OpalEditorBasedStructureViewModel(
      final HTMLEditor editor, PsiFile psiFile, StructureViewTreeElement root) {
    myEditor = editor;
    myPsiFile = psiFile;
    myRoot = root;
  }

  @Nullable
  @Override
  public Object getCurrentEditorElement() {
    if (myEditor == null) return null;
    if (!myPsiFile.isValid()) return null;

    return null;
  }

  @Override
  public void addEditorPositionListener(@NotNull FileEditorPositionListener listener) {
    // _TODO - FileEditorPositionListener (might not need since read-only !!)
    // The listener interface which allows the structure view to receive notifications about
    // * changes to the editor selection.
  }

  @Override
  public void removeEditorPositionListener(@NotNull FileEditorPositionListener listener) {
    // _TODO - FileEditorPositionListener (might not need since read-only !!)
    // The listener interface which allows the structure view to receive notifications about
    // * changes to the editor selection.
  }

  @Override
  public void addModelListener(@NotNull ModelListener modelListener) {
    // _TODO - ModelListener:
    // The listener interface which allows the structure view to receive notification about
    // * changes to data shown in the structure view.
  }

  @Override
  public void removeModelListener(@NotNull ModelListener modelListener) {
    // _TODO - ModelListener:
    // The listener interface which allows the structure view to receive notification about
    // * changes to data shown in the structure view.
  }

  @NotNull
  @Override
  public StructureViewTreeElement getRoot() {
    return myRoot;
  }

  @Override
  public void dispose() {}

  @Override
  public boolean shouldEnterElement(Object element) {
    return false;
  }

  @NotNull
  @Override
  public Grouper[] getGroupers() {
    return new Grouper[0];
  }

  @NotNull
  @Override
  public Sorter[] getSorters() {
    return new Sorter[0];
  }

  @NotNull
  @Override
  public Filter[] getFilters() {
    return new Filter[0];
  }
}