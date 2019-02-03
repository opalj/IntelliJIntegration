package JavaByteCodeLanguage.LanguageAndFileType;

import com.intellij.openapi.fileTypes.LanguageFileType;
import globalData.GlobalData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class JavaByteCodeFileType extends LanguageFileType {
    public static final JavaByteCodeFileType INSTANCE = new JavaByteCodeFileType();

    protected JavaByteCodeFileType() {
        super(JavaByteCode.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Javabytecode file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "javabytecode file description";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return GlobalData.DISASSEMBLED_FILE_ENDING_JBC;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        //TODO http://www.jetbrains.org/intellij/sdk/docs/tutorials/custom_language_support/language_and_filetype.html
        return null;
    }
}
