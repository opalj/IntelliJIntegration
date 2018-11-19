package loadClassFile;

import Compile.Compiler;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.vfs.VirtualFile;
import loadFile.LoadFile;
import runCommand.ErrorRunningCommandException;
import runCommand.RunCommand;
import toolWindows.WindowCommManager;

import javax.swing.*;

public class LoadClassFileAction extends AnAction {

    public LoadClassFileAction() {
        // Set the menu item name.
        super("Load Class-file into bytecode disassembler");
    }

    public void actionPerformed(AnActionEvent event) {
        // All files selected in the "Project"-View

        VirtualFile [] virtualFiles = event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);

        /*try {
            byte [] fileData = LoadFile.loadFile(virtualFiles[0].getPath());
            WindowCommManager.getInstance().setDisassemblerText(""+fileData.length);
        } catch (Exception e) {
            WindowCommManager.getInstance().setDisassemblerText(e.getMessage());
        }*/

        String dec = null;
        try {
            dec = RunCommand.runJavap(virtualFiles[0].getPath());
        } catch (ErrorRunningCommandException e0) {
            dec = e0.getMessage();
        }
        WindowCommManager.getInstance().setDisassemblerText(dec);

        Compiler comp = new Compiler();
        if(comp.make(event.getProject().getBasePath())){
            // TODO
            // COMPILE THAT OPAL .
        }
    }

}