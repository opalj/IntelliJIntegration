package HTMLEditor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/*
 * @example: https://github.com/JetBrains/intellij-community/blob/master/images/src/org/intellij/images/editor/impl/ImageFileEditorProvider.java
 */
public class MyFileEditorProvider implements FileEditorProvider {
  // TODO: should this class implement "DumbAware" ?

  @NonNls
  private static final String EDITOR_TYPE_ID = "OPAL-HTML";

  @Override
  public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
    // for now restrict to .class files only
    String fileExtension = file.getExtension();
    return (fileExtension != null) && fileExtension.equals("class") ;// || fileExtension.equals("html"));
  }

  @NotNull
  @Override
  public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
    // this method here is only called if MyFileEditorProvider.accept() returns true
    // this method should NEVER return null
    return new MyHtmlEditor(project, file);
  }

  @NotNull
  @Override
  public String getEditorTypeId() {
    return EDITOR_TYPE_ID;
  }

  @NotNull
  @Override
  public FileEditorPolicy getPolicy() {
    // This keeps the default editor so that one can switch between the two
    return FileEditorPolicy.NONE;
  }
}
