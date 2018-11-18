package loadClassFile;

import Compile.Compiler;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.vfs.VirtualFile;
import loadFile.LoadFile;
import loadFile.exceptions.*;
import toolWindows.WindowCommManager;

public class LoadClassFileAction extends AnAction {

    public LoadClassFileAction() {
        // Set the menu item name.
        super("Load Class-file into bytecode disassembler");
    }

    public void actionPerformed(AnActionEvent event) {
        // All files selected in the "Project"-View
        VirtualFile [] virtualFiles = event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);

        try {
            byte [] fileData = LoadFile.loadFile(virtualFiles[0].getPath());
            WindowCommManager.getInstance().setDissasemblerText(""+fileData.length);
        } catch (Exception e) {
            WindowCommManager.getInstance().setDissasemblerText(e.getMessage());
        }


        /*Compiler comp = new Compiler();
        if(comp.make(event.getProject().getBasePath())){
            // TODO
            // COMPILE THAT OPAL .
        }*/
    }

}