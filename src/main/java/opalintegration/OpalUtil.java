/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package opalintegration;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import config.BytecodeConfig;
import globalData.GlobalData;
import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.opalj.br.*;

/**
 * A utility class that serves a couple of purposes:
 *
 * <p>1. it is a gathering point for the output preparation of each editor (includes creation of the
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

  private static BytecodeConfig BCConfig = BytecodeConfig.getInstance();
  private static String projectPath; //
  private static String fqClassName; // the qualified name of the class that is to be decompiled
  private static File projectFile; //
  private static VirtualFile currentWorkingVF; //
  private static File
      tempDirectory; // The (temporary) directory in which our files are to be stored in

  private static final Logger LOGGER = Logger.getLogger(OpalUtil.class.getName());

  /**
   * Creates the decompiled file
   *
   * @param prepareID indicates what type the output is going to be (e.g. JBC or TAC)
   * @param virtualFile the underlying class file that is to be decompiled
   * @param olderFile if not null, then use this instead of creating a new file (content is still
   *     updated)
   * @return a VirtualFile which contains the desired content (the bytecode or TAC representation)
   */
  public static VirtualFile prepare(
      @NotNull Project project,
      @NotNull String prepareID,
      VirtualFile virtualFile,
      @Nullable VirtualFile olderFile) {

    // if a file exists already, use it to write to instead of creating a new file
    updateStateIfNewClassFile(virtualFile, project, olderFile != null);

    // if something went wrong during the opal project creation, just return the passed in class
    // file
    if (classFile == null) {
      return virtualFile;
    }

    // name of file to write to
    String fileName = virtualFile.getNameWithoutExtension();

    // create the output string
    String representableForm = null;
    switch (prepareID) {
        // note: HTML editor is deprecated and currently not displayed
      case GlobalData.DISASSEMBLED_FILE_ENDING_HTML:
        fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_HTML);
        representableForm = HtmlProducer.JavaClassToHtmlForm(virtualFile);
        break;
      case GlobalData.DISASSEMBLED_FILE_ENDING_TAC:
        fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_TAC);
        representableForm = new TacProducer(virtualFile.getPath()).decompiledText(classFile);
        break;
      case GlobalData.DISASSEMBLED_FILE_ENDING_JBC:
        fileName = fileName.concat(".").concat(GlobalData.DISASSEMBLED_FILE_ENDING_JBC);
        representableForm = new JbcProducer().decompiledText(classFile);
        break;
    }

    if (olderFile != null) {
      fileName = olderFile.getPath();
    }

    File preparedFile = writeContentToFile(fileName, representableForm, olderFile != null);

    // if something went wrong during de-compilation, just return the passed in class file
    if (preparedFile == null) {
      return virtualFile;
    }

    return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(preparedFile);
  }

  /**
   * An auxiliary method that writes 'content' to a 'file' (main purpose of this method is to
   * encapsulate the try/catch block)
   *
   * @param filename the Name of the temporal file
   * @param content content to write into the file
   */
  private static File writeContentToFile(String filename, String content, boolean olderFile) {
    try {
      File fileToWriteTo = null;

      // create the temporary directory to store files in, if non-existent
      if (tempDirectory == null || !tempDirectory.exists()) {
        tempDirectory = FileUtil.createTempDirectory("tempJbcDirectory", "", true);
      }

      if (!olderFile) {
        // to check if the temporary directory already contains a file with the same name
        boolean fileContained = false;

        File[] filesInTempDirectory = tempDirectory.listFiles();
        if (filesInTempDirectory != null) {
          for (File fileInTemp : filesInTempDirectory) {
            if (fileInTemp.getName().equals(filename)) {
              fileContained = true;
              fileToWriteTo = fileInTemp;
              break;
            }
          }
        }

        if (!fileContained) {
          fileToWriteTo = new File(tempDirectory.getAbsolutePath() + File.separator + filename);
        }
      } else {
        // if a file already exists, just take it
        fileToWriteTo = new File(filename);
      }

      FileUtil.writeToFile(fileToWriteTo, content, false);
      return fileToWriteTo;
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.toString(), e);
    }
    return null;
  }

  /**
   * Updates the state when the (class) file has been changed (or is new)
   *
   * @param virtualClassFile The (class) file to check
   * @param project The project the file belongs to
   */
  private static void updateStateIfNewClassFile(
      @NotNull VirtualFile virtualClassFile, @NotNull Project project, boolean VFL) {
    if (virtualClassFile.getExtension() == null) {
      return;
    }
    if (VFL
        || (!virtualClassFile.equals(currentWorkingVF)
            && virtualClassFile
                .getExtension()
                .equals(
                    com.intellij.ide.highlighter.JavaClassFileType.INSTANCE
                        .getDefaultExtension()))) {
      currentWorkingVF = virtualClassFile;
      if (virtualClassFile.getCanonicalPath() != null
          && !virtualClassFile.getCanonicalPath().contains("!")) {
        projectPath = virtualClassFile.getPath();
      } else {
        String[] jarPath = getJarFileRootAndFileName(virtualClassFile);
        projectPath = jarPath[0];
        fqClassName = jarPath[1];
      }

      projectFile = new File(projectPath);
      uriProject = org.opalj.br.analyses.Project.apply(projectFile);
      if (BCConfig.getProjectConfigString().length() == 0) {
        ConfigRenderOptions configRenderOptions =
            ConfigRenderOptions.defaults()
                .setOriginComments(true)
                .setComments(true)
                .setFormatted(true)
                .setJson(false);
        String render =
            BCConfig.isProjectConfigJustOpal()
                ? uriProject.config().atKey("org.opalj").root().render(configRenderOptions)
                : uriProject.config().root().render(configRenderOptions);
        BCConfig.setProjectConfigString(render);
      } else {
        Config mergedConfig =
            uriProject
                .config()
                .withFallback(ConfigFactory.parseString(BCConfig.getProjectConfigString()));
        uriProject = uriProject.apply(projectFile, uriProject.logContext(), mergedConfig);
      }
      classFile = getClassFile(virtualClassFile);
    }
  }

  /**
   * Returns an OPAL ClassFile for a given class file. The ClassFile from OPAL contains necessary
   * information to produce the desired bytecode and TAC representations for a class file.
   *
   * @param virtualClassFile a standard ("java") class file
   * @return an OPAL ClassFile
   */
  @Nullable
  private static ClassFile getClassFile(@NotNull VirtualFile virtualClassFile) {
    // (might be) a JAR
    if (virtualClassFile.getCanonicalPath() != null
        && virtualClassFile.getCanonicalPath().contains("!")) {
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

    return null;
  }

  /**
   * Retrieves the necessary path information of a class from within a JAR, so that we can create an
   * OPAL ClassFile from it.
   *
   * @param virtualClassFile - a class file (assumed to be located in a JAR)
   * @return a 2D String array with a jar/zip path & a fully qualified class name
   */
  public static String[] getJarFileRootAndFileName(VirtualFile virtualClassFile) {
    String jarPathWithoutClassExtension =
        virtualClassFile.getParent().getPath() + File.separator + virtualClassFile.getName();
    // this\is\the\jarPath -> this/is/the/jarPath
    jarPathWithoutClassExtension = jarPathWithoutClassExtension.replaceAll("\\\\", "/");
    return jarPathWithoutClassExtension.split("!/", 2);
  }

  /**
   * Creates an OPAL ClassFile from a class that is contained in a JAR
   *
   * @param jarFileRoot - path to a JAR
   * @param className the fully qualified class name with extension
   * @return OPAL ClassFile that is used to retrieve information about bytecode/TAC
   */
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
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, e.toString(), e);
    }
    return classFileFromJar;
  }
}
