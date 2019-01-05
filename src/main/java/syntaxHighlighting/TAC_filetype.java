package syntaxHighlighting;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import globalData.GlobalData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TAC_filetype extends LanguageFileType {

    public static final Icon ICON = new ImageIcon("C:/Users/Oskar/Desktop/red.png");
    public static final TAC_filetype INSTANCE = new TAC_filetype();

    private TAC_filetype() {
        super(TAC.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "TAC file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Threaa-address-code";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return GlobalData.DISASSEMBLED_FILE_ENDING_TAC;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return ICON;
    }

}
