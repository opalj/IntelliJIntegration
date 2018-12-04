package HTMLEditor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/*
 * @example: https://github.com/JetBrains/intellij-community/blob/master/images/src/org/intellij/images/editor/impl/ImageFileEditorProvider.java
 */
public class MyFileEditorProvider implements FileEditorProvider {
    @NonNls private static final String EDITOR_TYPE_ID = "html type";

    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
        // for now restrict to .class files only
        String fileExtension = file.getExtension();
        return (fileExtension != null) ? fileExtension.equals("class") : false;
    }

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file)  {
        // this method here is only called if MyFileEditorProvider.accept() returns true
        // this method should NEVER return null



        // TODO: this seems to be a bit harder ... will have to return "MyHtmlEditor"
        try {
            MyHtmlEditor htmlEditor = new MyHtmlEditor(project, file);
            return htmlEditor;
        } catch(IOException e) {
            return null;
        }

//        return htmlEditor;
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return EDITOR_TYPE_ID;
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        // "Do not create default IDEA's editor (if any) for the file"
        return FileEditorPolicy.PLACE_BEFORE_DEFAULT_EDITOR;
    }


}
