package loadClassFile;

import Compile.Compiler;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import org.jetbrains.annotations.NotNull;

public class OpenCorrespondingClassFileAction extends AnAction {

    public OpenCorrespondingClassFileAction() {
        // Set the menu item name.
        super("Compile And Open .class-File");
    }

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getProject();
        final VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);

        // show Action only for java files
        e.getPresentation().setEnabledAndVisible(project != null && virtualFile.getExtension().equals("java"));
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        final Project project = event.getProject();

        if (project != null && Compiler.make(event.getProject())) {
            // currently selected file in the project view
            VirtualFile virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE);

            VirtualFile classFile = getCorrespondingClassFile(project, virtualFile);
            FileEditorManager.getInstance(project).openFile(classFile, true);
        } // if
    } // actionPerformed

    private VirtualFile getCorrespondingClassFile(Project project, VirtualFile virtualFile) {
        ProjectFileIndex projectFileIndex = ProjectFileIndex.getInstance(project);
        Module module = projectFileIndex.getModuleForFile(virtualFile);

        VirtualFile outputPath = CompilerModuleExtension.getInstance(module).getCompilerOutputPath();
        // recursively iterate over its children
        // TODO: VfsUtilCore.visitChildrenRecursively()
        return findClassFile(outputPath, virtualFile.getNameWithoutExtension() + ".class");
    }

    private VirtualFile findClassFile(VirtualFile path, String classFileName) {
        if(path.getName().equals(classFileName) || path.getChildren().length == 0) {
            return path;
        }
        else {
            VirtualFile[] vfs = path.getChildren();
            for(VirtualFile vf : vfs) {
                VirtualFile result = findClassFile(vf, classFileName);
                if(result.getName().equals(classFileName)) {
                    return result;
                }
            } // for()
        }

        return path;
    } // findClassFile()
}
