package loadClassFile;

import Compile.Compiler;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import globalData.GlobalData;
import opalintegration.Opal;
import runCommand.ErrorRunningCommandException;
import runCommand.RunCommand;
import saveFile.SaveFile;
import saveFile.exceptions.ErrorWritingFileException;
import saveFile.exceptions.InputNullException;
import saveFile.exceptions.IsNotAFileException;
import saveFile.exceptions.NotEnoughRightsException;
import toolWindows.WindowCommManager;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoadClassFileAction extends AnAction {

    public LoadClassFileAction() {
        // Set the menu item name.
        super("Load Class-file into bytecode disassembler");
    }

    public static final void openClassFile(File classFile) {

        String classFileEnding = getEnding(classFile.getName());
        if (classFileEnding == null || !classFileEnding.toUpperCase().equals("CLASS")) {
            JOptionPane.showMessageDialog(
                    null,
                    "The given file is not a class-file.\nCan only decompile " + "class-files.",
                    "Error",
                    JOptionPane.OK_OPTION);
            return;
        }

        String classPath = classFile.getPath();

        // Decompile class-file
        String dec = null;
        try {
            dec = RunCommand.runJavap(classPath);
        } catch (ErrorRunningCommandException e0) {
            dec = e0.getMessage();
        }

        // Give the decompiled code to the disassembler-window
        WindowCommManager.getInstance().setDisassemblerText(dec);

        // Save the decompiled code to a file
        Project currentProject = ProjectManager.getInstance().getOpenProjects()[0];
        String basePath = currentProject.getBasePath();

        File baseDir = new File(basePath);
        File temp = (new File(classPath)).getParentFile();
        ArrayList<String> dirNames = new ArrayList<String>();
        while (!temp.getAbsolutePath().equals(baseDir.getAbsolutePath())) {
            dirNames.add(temp.getName());
            temp = temp.getParentFile();
        }

        File disassembledDir = new File(basePath + File.separator + GlobalData.DISASSEMBLED_FILES_DIR);
        if (!disassembledDir.exists()) {
            disassembledDir.mkdir();
        }

        temp = new File(disassembledDir.getAbsolutePath());
        for (int i = 0; i < dirNames.size(); i++) {
            temp =
                    new File(
                            temp.getAbsolutePath() + File.separator + dirNames.get(dirNames.size() - (i + 1)));
            if (!temp.exists()) {
                temp.mkdir();
            }
        }

        // File classFile = new File(classPath);
        // ##########################################################################################
        String noEnding = classFile.getName();
        if (noEnding.contains(".")) {
            String[] parts = noEnding.split("\\.");
            String tempNoEnding = null;
            for (int i = 0; i < (parts.length - 1); i++) {
                if (i == 0) {
                    tempNoEnding = parts[0];
                } else {
                    tempNoEnding = (tempNoEnding + "." + parts[i]);
                }
            }
            noEnding = tempNoEnding;
        }
        File disassembledFile =
                new File(
                        temp.getAbsolutePath()
                                + File.separator
                                + noEnding
                                + "."
                                + GlobalData.DISASSEMBLED_FILE_ENDING_TAC);

        if (!disassembledFile.exists()) {
            try {
                disassembledFile.createNewFile();
            } catch (IOException e) {
                // empty
            }
        }

        String tac = Opal.ThreeWayDisAssemblerString(classFile.getAbsolutePath());

        try {
            SaveFile.saveFile(tac, disassembledFile.getAbsolutePath());
        } catch (InputNullException e0) {
            // empty
        } catch (NotEnoughRightsException e1) {
            // empty
        } catch (IsNotAFileException e2) {
            // empty
        } catch (ErrorWritingFileException e3) {
            // empty
        }

        // Open the just saved file in an editor
        FileEditorManager.getInstance(currentProject)
                .openFile(LocalFileSystem.getInstance().refreshAndFindFileByIoFile(disassembledFile), true);
    }

    private static final String getEnding(String fileName) {
        if (fileName.contains(".")) {
            String[] parts = fileName.split("\\.");
            return parts[(parts.length - 1)];
        } else {
            return null;
        }
    }

    public void actionPerformed(AnActionEvent event) {
        if (event.getProject() != null && Compiler.make(event.getProject())) {
            // All files selected in the "Project"-View
            VirtualFile[] virtualFiles = event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);

            // Load the file
      /*try {
          byte [] fileData = LoadFile.loadFile(virtualFiles[0].getPath());
          WindowCommManager.getInstance().setDisassemblerText(""+fileData.length);
      } catch (Exception e) {
          WindowCommManager.getInstance().setDisassemblerText(e.getMessage());
      }*/

            openClassFile(new File(virtualFiles[0].getPath()));
        }
    }
}
