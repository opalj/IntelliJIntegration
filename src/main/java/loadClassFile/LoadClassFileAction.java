package loadClassFile;

import Compile.Compiler;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class LoadClassFileAction extends AnAction {

    public LoadClassFileAction() {
        // Set the menu item name.
        super("Load Class-file into bytecode disassembler");
    }

    public void actionPerformed(AnActionEvent event) {
        Compiler comp = new Compiler();
        if(comp.make(event.getProject().getBasePath())){
            // TODO
            // COMPILE THAT OPAL .
        }
    }
}