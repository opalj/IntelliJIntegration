package org.opalj.intellijintegration.Actions.openclass;

import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.ide.util.JavaAnonymousClassesHelper;
import com.intellij.lang.Language;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompileStatusNotification;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.compiled.ClsFileImpl;
import com.intellij.psi.util.ClassUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import java.io.File;
import org.jetbrains.annotations.NotNull;
import org.opalj.intellijintegration.Actions.DecompileFromJar;
import org.opalj.intellijintegration.Compile.Compiler;

public class PsiClassAction extends AnAction {
  private final String editorName;

  /**
   * Instantiates a new Open class file action.
   *
   * @param editorName the editor name witch will be opened
   */
  PsiClassAction(String editorName) {
    super();
    this.editorName = editorName;
  }

  public static PsiClass getContainingClass(@NotNull PsiElement psiElement) {

    PsiClass containingClass = PsiTreeUtil.getParentOfType(psiElement, PsiClass.class, false);
    while (containingClass instanceof PsiTypeParameter) {
      containingClass = PsiTreeUtil.getParentOfType(containingClass, PsiClass.class);
    }

    if (containingClass == null) {
      PsiFile containingFile = psiElement.getContainingFile();
      if (containingFile instanceof PsiClassOwner) {
        PsiClass[] classes = ((PsiClassOwner) containingFile).getClasses();
        if (classes.length == 1) return classes[0];

        TextRange textRange = psiElement.getTextRange();
        PsiClass largestClass = classes[0];
        int largestSize = 0;
        if (textRange != null) {
          for (PsiClass aClass : classes) {
            PsiElement navigationElement = aClass.getNavigationElement();
            TextRange classRange =
                navigationElement != null ? navigationElement.getTextRange() : null;
            if (classRange != null) {
              if (classRange.contains(textRange)) return aClass;
              int size = classRange.getEndOffset() - classRange.getStartOffset();
              if (size > largestSize) {
                largestClass = aClass;
                largestSize = size;
              }
            }
          }
        }

        // If there are multiple classes in a file, return the largest class of the file
        return largestClass;
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
        if (FileTypeRegistry.getInstance().isFileOfType(file, JavaClassFileType.INSTANCE)) {
          // compiled class; looking for the right .class file (inner class 'A.B' is "contained" in
          // 'A.class', but we need 'A$B.class')
          String classFileName = StringUtil.getShortName(jvmClassName) + ".class";
          if (index.isInLibraryClasses(file)) {
            VirtualFile classFile = file.getParent().findChild(classFileName);
            return classFile;
          } else {
            File classFile = new File(file.getParent().getPath(), classFileName);
            if (classFile.isFile()) {
              return LocalFileSystem.getInstance().findFileByIoFile(classFile);
            }
          }
        } else {
          // source code; looking for a .class file in compiler output
          Module module = index.getModuleForFile(file);
          if (module != null) {
            CompilerModuleExtension extension = CompilerModuleExtension.getInstance(module);
            if (extension != null) {
              boolean inTests = index.isInTestSourceContent(file);
              VirtualFile classRoot =
                  inTests
                      ? extension.getCompilerOutputPathForTests()
                      : extension.getCompilerOutputPath();
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
    if (aClass != null && !(aClass instanceof PsiAnonymousClass)) {
      return ClassUtil.getJVMClassName(aClass);
    }

    PsiClass containingClass = PsiTreeUtil.getParentOfType(aClass, PsiClass.class);
    if (containingClass != null) {
      return getJVMClassName(containingClass)
          + JavaAnonymousClassesHelper.getName((PsiAnonymousClass) aClass);
    }

    return null;
  }

  @Override
  public void update(@NotNull AnActionEvent e) {
    PsiElement element = e.getData(CommonDataKeys.PSI_ELEMENT);
    if (element != null) {
      boolean isValidFile = element.getLanguage() != Language.ANY || isSupportedBinaryFile(element);
      e.getPresentation()
          .setEnabledAndVisible(isValidFile && e.getData(CommonDataKeys.PROJECT) != null);
    }
  }

  private boolean isSupportedBinaryFile(PsiElement element) {
    if (!(element instanceof PsiBinaryFile)) return false;
    String name = ((PsiBinaryFile) element).getOriginalFile().getName();
    return name.endsWith(".class") || name.endsWith(".jar");
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);
    Project project = e.getData(CommonDataKeys.PROJECT);
    VirtualFile classFile = null;
    PsiClass containingClass = null;
    if (psiElement instanceof PsiBinaryFile) {
      String name = ((PsiBinaryFile) psiElement).getOriginalFile().getName().toUpperCase();
      if (name.endsWith(".CLASS")) {
        PsiFile newFile = new ClsFileImpl(((PsiBinaryFile) psiElement).getViewProvider());
        classFile = newFile.getVirtualFile();
        ((SingleRootFileViewProvider) ((PsiBinaryFile) psiElement).getViewProvider())
            .forceCachedPsi(newFile);
      } else if (name.endsWith(".JAR")) {
        DecompileFromJar.openDialog(
            project, ((PsiBinaryFile) psiElement).getVirtualFile(), editorName);
        return;
      }
    } else {
      containingClass = getContainingClass(psiElement);
      classFile = LoadClassFileBytes(containingClass);
    }
    if (classFile != null) {
      FileEditorManager.getInstance(project).openFile(classFile, true);
      FileEditorManager.getInstance(project).setSelectedEditor(classFile, editorName);
    } else {
      VirtualFile virtualFile = containingClass.getContainingFile().getVirtualFile();
      CompilerManager compilerManager = CompilerManager.getInstance(project);
      CompileScope filesCompileScope =
          compilerManager.createFilesCompileScope(new VirtualFile[] {virtualFile});
      final PsiClass containingClassFinal = containingClass;
      CompileStatusNotification compilingNotifaction =
          (aborted, errors, warnings, compileContext) -> {
            if (aborted) { // do nothing if manually channeled
              return;
            }
            if (errors == 0) {
              VirtualFile lclassFile = LoadClassFileBytes(containingClassFinal);
              // ApplicationManager.getApplication().invokeLater(() ->
              // FileEditorManager.getInstance(compileContext.getProject()).openFile(classFile,
              // true), ModalityState.NON_MODAL);
              FileEditorManager.getInstance(compileContext.getProject())
                  .setSelectedEditor(lclassFile, editorName);
            } else {
              Notifications.Bus.notify(
                  NotificationGroupManager.getInstance()
                      .getNotificationGroup("OpalPlugin")
                      .createNotification()
                      .setContent(
                          "cant find classfile for"
                              + psiElement.getContainingFile().getName()
                              + " \n YOU COULD BUILD THE WHOLE PROJECT AND RETRY IT"));
            }
          };
      new Compiler().make(e.getProject(), filesCompileScope, compilingNotifaction);
    }
  }
}
