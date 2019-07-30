
package Actions.openclass;

import Actions.ActionUtil;
import Compile.Compiler;
import com.intellij.ide.util.JavaAnonymousClassesHelper;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompileStatusNotification;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.ClassUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import globalData.GlobalData;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class PsiClassAction extends AnAction {
    @Override
    public void update(@NotNull AnActionEvent e) {
        String extension = ActionUtil.ExtString(e);
        // need to be changed
        e.getPresentation().setEnabledAndVisible(e.getData(DataKeys.PSI_ELEMENT) != null && e.getData(DataKeys.PROJECT) != null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiElement psiElement = e.getData(DataKeys.PSI_ELEMENT);
        PsiClass containingClass = getContainingClass(psiElement);
        VirtualFile classFile = LoadClassFileBytes(containingClass);
        Project project = e.getData(DataKeys.PROJECT);
        VirtualFile virtualFile = containingClass.getContainingFile().getVirtualFile();
        if(classFile != null){
            FileEditorManager.getInstance(project).openFile(classFile, true);
            FileEditorManager.getInstance(project).setSelectedEditor(classFile, globalData.GlobalData.TAC_EDITOR_ID);
        }else{
            CompilerManager compilerManager = CompilerManager.getInstance(project);
            CompileScope filesCompileScope = compilerManager.createFilesCompileScope(new VirtualFile[]{virtualFile});
            CompileStatusNotification compilingNotifaction = (aborted, errors, warnings, compileContext) -> {
                if (aborted) { // do nothing if manually channeled
                    return;
                }
                if (errors == 0) {
                    VirtualFile lclassFile = LoadClassFileBytes(containingClass);
                   // ApplicationManager.getApplication().invokeLater(() -> FileEditorManager.getInstance(compileContext.getProject()).openFile(classFile, true), ModalityState.NON_MODAL);
                    FileEditorManager.getInstance(compileContext.getProject()).setSelectedEditor(lclassFile, GlobalData.BYTECODE_EDITOR_ID);
                }
            };
            new Compiler().make(e.getProject(),filesCompileScope,compilingNotifaction);
        }
        Notifications.Bus.notify(
                new NotificationGroup("OpalPlugin", NotificationDisplayType.BALLOON, false)
                        .createNotification()
                        .setContent("cant find classfile for"+psiElement.getContainingFile().getName()));
    }
    public static PsiClass getContainingClass(@NotNull PsiElement psiElement) {

        PsiClass containingClass = PsiTreeUtil.getParentOfType(psiElement, PsiClass.class, false);
        while (containingClass instanceof PsiTypeParameter) {
            containingClass = PsiTreeUtil.getParentOfType(containingClass, PsiClass.class);
        }

        if (containingClass == null) {
            PsiFile containingFile = psiElement.getContainingFile();
            if (containingFile instanceof PsiClassOwner) {
                PsiClass[] classes = ((PsiClassOwner)containingFile).getClasses();
                if (classes.length == 1) return classes[0];

                TextRange textRange = psiElement.getTextRange();
                if (textRange != null) {
                    for (PsiClass aClass : classes) {
                        PsiElement navigationElement = aClass.getNavigationElement();
                        TextRange classRange = navigationElement != null ? navigationElement.getTextRange() : null;
                        if (classRange != null && classRange.contains(textRange)) return aClass;
                    }
                }
            }
            return null;
        }

        return containingClass;
    }
    private static VirtualFile LoadClassFileBytes(PsiClass aClass) {
        String jvmClassName = getJVMClassName(aClass);
        if (jvmClassName != null) {
            PsiClass fileClass = aClass;
            while (PsiUtil.isLocalOrAnonymousClass(fileClass)) {
                PsiClass containingClass = PsiTreeUtil.getParentOfType(fileClass, PsiClass.class);
                if (containingClass != null) {
                    fileClass = containingClass;
                }
            }
            VirtualFile file = fileClass.getOriginalElement().getContainingFile().getVirtualFile();
            if (file != null) {
                ProjectFileIndex index = ProjectFileIndex.SERVICE.getInstance(aClass.getProject());
                if (FileTypeRegistry.getInstance().isFileOfType(file, StdFileTypes.CLASS)) {
                    // compiled class; looking for the right .class file (inner class 'A.B' is "contained" in 'A.class', but we need 'A$B.class')
                    String classFileName = StringUtil.getShortName(jvmClassName) + ".class";
                    if (index.isInLibraryClasses(file)) {
                        VirtualFile classFile = file.getParent().findChild(classFileName);
                        if (classFile != null) {
                            return classFile;
                        }
                    }
                    else {
                        File classFile = new File(file.getParent().getPath(), classFileName);
                        if (classFile.isFile()) {
                            return LocalFileSystem.getInstance().findFileByIoFile(classFile);
                        }
                    }
                }
                else {
                    // source code; looking for a .class file in compiler output
                    Module module = index.getModuleForFile(file);
                    if (module != null) {
                        CompilerModuleExtension extension = CompilerModuleExtension.getInstance(module);
                        if (extension != null) {
                            boolean inTests = index.isInTestSourceContent(file);
                            VirtualFile classRoot = inTests ? extension.getCompilerOutputPathForTests() : extension.getCompilerOutputPath();
                            if (classRoot != null) {
                                String relativePath = jvmClassName.replace('.', '/') + ".class";
                                File classFile = new File(classRoot.getPath(), relativePath);
                                if (classFile.exists()) {
                                    return LocalFileSystem.getInstance().findFileByIoFile(classFile);
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
    private static String getJVMClassName(PsiClass aClass) {
        if (!(aClass instanceof PsiAnonymousClass)) {
            return ClassUtil.getJVMClassName(aClass);
        }

        PsiClass containingClass = PsiTreeUtil.getParentOfType(aClass, PsiClass.class);
        if (containingClass != null) {
            return getJVMClassName(containingClass) + JavaAnonymousClassesHelper.getName((PsiAnonymousClass)aClass);
        }

        return null;
    }
}
