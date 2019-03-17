package opalintegration;

import Compile.Compiler;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.*;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import globalData.GlobalData;
import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.opalj.br.*;
import org.opalj.collection.immutable.ConstArray;

/**
 * A utility class that serves a couple of purposes
 *
 * <p>1. it is a gathering point for the output preparation of each editor (includes creating the
 * file) 2. it handles updates (e.g. re-compiles if needed) 3. it provides a way to access files in
 * a JAR, as well as the JAR itself
 */
public class OpalUtil {

  /** the uriProject is needed to link the opal frameworks with our project */
  private static org.opalj.br.analyses.Project<URL> uriProject;

  /**
   * this class file contains the information needed to create the desired outputs for JBC and TAC
   * (e.g. fields, methods, access flags, FQN of the class, etc.)
   */
  private static ClassFile classFile;

  private static String projectPath;
  private static String fqClassName;
  private static File projectFile;
  private static VirtualFile currentWorkingVF;
  private static File tempDirectory;

  private static final Logger LOGGER = Logger.getLogger(OpalUtil.class.getName());

  /**
   * @param prepareID indicates what type the output is going to be (e.g. JBC or TAC)
   * @param virtualFile the underlying class file
   * @param olderFile if not null, then use this instead of creating a new file (content is still
   *     updated)
   * @return a VirtualFile which contains the desired content (the bytecode or TAC representation)
   */
  public static VirtualFile prepare(
      @NotNull Project project,
      @NotNull String prepareID,
      VirtualFile virtualFile,
      @Nullable VirtualFile olderFile) {
    updateStateIfNewClassFile(virtualFile, project);
    String fileName = virtualFile.getNameWithoutExtension();
    String representableForm = null;
    switch (prepareID) {
        // note: HTML editor is deprecated and currently not displayed
      case GlobalData.DISASSEMBLED_FILE_ENDING_HTML:
        fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_HTML);
        representableForm = HtmlProducer.JavaClassToHtmlForm(virtualFile);
        break;
      case GlobalData.DISASSEMBLED_FILE_ENDING_TAC:
        fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_TAC);
        representableForm = TacProducer.createTacString(classFile, virtualFile.getPath());
        break;
      case GlobalData.DISASSEMBLED_FILE_ENDING_JBC:
        fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_JBC);
        representableForm = JbcProducer.createBytecodeString(classFile);
        break;
    }

    if (olderFile != null) {
      fileName = olderFile.getPath();
    }

    File preparedFile =
        writeContentToFile(fileName, representableForm, olderFile == null ? false : true);
    return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(preparedFile);
  }

  /**
   * TODO: currently fails to update the content on a change
   *
   * <p>an auxiliary method that writes 'content' to a 'file' (main purpose of this method is to
   * encapsulate the try/catch block)
   *
   * @param filename the Name of the temporal file
   * @param content content to write into the file
   */
  private static File writeContentToFile(String filename, String content, boolean olderFile) {
    try {
      File fileToWriteTo = null;

      if (tempDirectory == null || !tempDirectory.exists()) {
        tempDirectory = FileUtil.createTempDirectory("tempJbcDirectory", "", true);
      }

      if (!olderFile) {
        boolean fileContained = false;
        for (File fileInTemp : tempDirectory.listFiles()) {
          if (fileInTemp.getName().equals(filename)) {
            fileContained = true;
            fileToWriteTo = fileInTemp;
            break;
          }
        }

        if (!fileContained) {
          fileToWriteTo = new File(tempDirectory.getAbsolutePath() + File.separator + filename);
        }
      } else {
        fileToWriteTo = new File(filename);
      }

      FileUtil.writeToFile(fileToWriteTo, content, false);
      return fileToWriteTo;
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.toString(), e);
    }

    // TODO not null
    return null;
  }

  /**
   * Updates the state when the (class) file has been changed (or is new)
   *
   * @param virtualClassFile The (class) file to check
   * @param project The project the file belongs to
   */
  private static void updateStateIfNewClassFile(
      @NotNull VirtualFile virtualClassFile, @NotNull Project project) {
    if (!virtualClassFile.equals(currentWorkingVF)
        && virtualClassFile.getExtension().equals(StdFileTypes.CLASS.getDefaultExtension())) {
      currentWorkingVF = virtualClassFile;
      Compiler.make(project);
      if (!virtualClassFile.getCanonicalPath().contains("!")) {
        projectPath = virtualClassFile.getPath();
        PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualClassFile);
        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
        final PsiClass[] classes = psiJavaFile.getClasses();
        fqClassName = classes[0].getQualifiedName().replace('.', '/');
      } else {
        String[] jarPath = getJarFileRootAndFileName(virtualClassFile);
        projectPath = jarPath[0];
        fqClassName = jarPath[1];
      }
      projectFile = new File(projectPath);
      uriProject = org.opalj.br.analyses.Project.apply(projectFile);
      classFile = getClassFile(virtualClassFile);
    }
  }

  /**
   * Returns an OPAL ClassFile for a given class file. The ClassFile from OPAL contains necessary
   * information to produce the desired bytecode and TAC representations for a class file.
   *
   * @param virtualClassFile
   * @return an OPAL ClassFile
   */
  @Nullable
  private static ClassFile getClassFile(@NotNull VirtualFile virtualClassFile) {
    ConstArray<ClassFile> classFileConstArray = uriProject.allProjectClassFiles();
    for (int i = 0; i < classFileConstArray.length(); i++) {
      ClassFile cf = classFileConstArray.apply(i);
      if (cf.fqn().equals(fqClassName.replace(".class", ""))) {
        return cf;
      }
    }
    // (might be) JAR
    if (virtualClassFile.getCanonicalPath().contains("!")) {
      return createClassFileFromJar(projectPath, fqClassName);
    }
    // use the input stream instead
    else {
      try {
        FileInputStream inputStream = new FileInputStream(projectFile);
        Object classFileObj =
            org.opalj.br.analyses.Project.JavaClassFileReader(
                    uriProject.logContext(), uriProject.config())
                .ClassFile(() -> inputStream)
                .head();
        if (classFileObj instanceof ClassFile) {
          return (ClassFile) classFileObj;
        }
      } catch (FileNotFoundException e) {
        LOGGER.log(Level.SEVERE, e.toString(), e);
      }
    }
    // TODO: what to do in this case?
    return null;
  }

  /**
   * @param virtualClassFile - a class file (assumed to be located in a JAR)
   * @return a string array with a jar/zip path & full qualified class name
   */
  public static String[] getJarFileRootAndFileName(VirtualFile virtualClassFile) {
    String jarPathWithoutClassExtension =
        virtualClassFile.getParent().getPath() + File.separator + virtualClassFile.getName();
    // this\is\the\jarPath -> this/is/the/jarPath
    jarPathWithoutClassExtension = jarPathWithoutClassExtension.replaceAll("\\\\", "/");
    String[] jarFileRoot = jarPathWithoutClassExtension.split("!/", 2);
    return jarFileRoot;
  }

  /**
   * @param jarFileRoot - path to a JAR
   * @param className the fully qualified class name with extension
   * @return ClassFile
   */
  // TODO: this gets called for classes we haven't clicked on as well?
  private static ClassFile createClassFileFromJar(String jarFileRoot, String className) {
    ClassFile classFileFromJar = null;
    try {
      scala.collection.immutable.List classFileList =
          org.opalj.br.analyses.Project.JavaClassFileReader(
                  uriProject.logContext(), uriProject.config())
              .ClassFile(jarFileRoot, className);
      if (classFileList.size() == 1) {
        classFileFromJar = (ClassFile) classFileList.head();
      }
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.toString(), e);
    }
    return classFileFromJar;
  }
}
