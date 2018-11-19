package toolWindows;

public class WindowCommManager {

    private static WindowCommManager instance = null;

    private DisassemblerToolWindowFactory dissasemblerWindow = null;
    private OutlineToolWindowFactory outlineWindow = null;

    private WindowCommManager() {}

    public final synchronized static WindowCommManager getInstance() {
        if( instance == null ) {
            instance = new WindowCommManager();
        }
        return instance;
    }

    public final void setDisassemblerInstance(DisassemblerToolWindowFactory i) {
        dissasemblerWindow = i;
    }

    public final void setOutlineInstance(OutlineToolWindowFactory i) {
        outlineWindow = i;
    }

    public final void setDisassemblerText(String text) {
        if( dissasemblerWindow != null ) {
            dissasemblerWindow.setText(text);
        }
    }

}
