package Editors.HTMLEditor;

import com.intellij.ide.structureView.StructureView;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyStructureViewBuilder extends TreeBasedStructureViewBuilder {

  PsiFile psiFile;
  StructureViewTreeElement root;
  FileEditor fileEditor;

  public MyStructureViewBuilder(PsiFile psiFile, StructureViewTreeElement root) {
    this.psiFile = psiFile;
    this.root = root;
  }

  @NotNull
  @Override
  public StructureViewModel createStructureViewModel(@Nullable Editor editor) {
    // ignore, as Editors.disEditor.HTMLEditor is not a TextEditor and hence needs special treatment
    return new OpalEditorBasedStructureViewModel(((HTMLEditor) fileEditor), psiFile, root);
  }

  @Override
  @NotNull
  public StructureView createStructureView(FileEditor fileEditor, @NotNull Project project) {
    this.fileEditor = fileEditor;
    OpalEditorBasedStructureViewModel opalModel =
        new OpalEditorBasedStructureViewModel(((HTMLEditor) fileEditor), psiFile, root);
    MyStructureView view =
        new MyStructureView((HTMLEditor) fileEditor, opalModel, project, isRootNodeShown());
    Disposer.register(view, () -> opalModel.dispose());
    return view;
  }
}
