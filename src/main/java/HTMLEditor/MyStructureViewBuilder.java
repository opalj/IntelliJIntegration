package HTMLEditor;

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

  OpalEditorBasedStructureViewModel model;
  PsiFile psiFile;
  StructureViewTreeElement root;
  StructureView view;
  FileEditor fileEditor;

  public MyStructureViewBuilder(PsiFile psiFile, StructureViewTreeElement root) {
    this.psiFile = psiFile;
    this.root = root;
  }

  @NotNull
  @Override
  public StructureViewModel createStructureViewModel(@Nullable Editor editor) {
    //        model = new OpalEditorBasedStructureViewModel(editor, psiFile, root);
    return new OpalEditorBasedStructureViewModel(((MyHtmlEditor) fileEditor), psiFile, root);
  }

  @Override
  @NotNull
  public StructureView createStructureView(FileEditor fileEditor, @NotNull Project project) {
    //        final StructureViewModel model = createStructureViewModel(fileEditor instanceof
    // MyHtmlEditor ? ((MyHtmlEditor)fileEditor).getEditor() : null);
    //        StructureView view =
    // StructureViewFactory.getInstance(project).createStructureView(fileEditor, model, project,
    // isRootNodeShown());
    this.fileEditor = fileEditor;
    OpalEditorBasedStructureViewModel newModel =
        new OpalEditorBasedStructureViewModel(((MyHtmlEditor) fileEditor), psiFile, root);
    view = new MyStructureView((MyHtmlEditor) fileEditor, newModel, project, isRootNodeShown());
    //        Disposer.register(view, () -> model.dispose());
    Disposer.register(view, () -> newModel.dispose());
    return view;
  }
}
