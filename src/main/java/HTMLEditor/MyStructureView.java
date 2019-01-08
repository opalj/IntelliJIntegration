package HTMLEditor;

import com.intellij.ide.structureView.StructureView;
import com.intellij.ide.structureView.StructureViewFactory;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.openapi.project.Project;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;

public class MyStructureView implements StructureView {

  private MyHtmlEditor htmlEditor;
  private StructureViewModel model;
  private OpalEditorBasedStructureViewModel opalModel;
  private Project project;
  private boolean isRootNodeShown;

  public MyStructureView(
      MyHtmlEditor htmlEditor,
      OpalEditorBasedStructureViewModel model,
      Project project,
      boolean isRootNodeShown) {
    this.htmlEditor = htmlEditor;
    this.opalModel = model;
    this.project = project;
    this.isRootNodeShown = isRootNodeShown;
  }

  @Override
  public MyHtmlEditor getFileEditor() {
    return htmlEditor;
  }

  @Override
  public boolean navigateToSelectedElement(boolean requestFocus) {
    return false;
  }

  @Override
  public JComponent getComponent() {
    StructureView view =
        StructureViewFactory.getInstance(project)
            .createStructureView(htmlEditor, opalModel, project, isRootNodeShown);
    return view.getComponent();
  }

  @Override
  public void centerSelectedRow() {}

  @Override
  public void restoreState() {}

  @Override
  public void storeState() {}

  @NotNull
  @Override
  public OpalEditorBasedStructureViewModel getTreeModel() {
    return opalModel;
  }

  @Override
  public void dispose() {
    // TODO
  }
}
