package JavaByteCodeLanguage.LanguageAndFileType;

import com.intellij.lang.Language;
import globalData.GlobalData;

public class JavaByteCode extends Language {
    private static final String ID = GlobalData.DISASSEMBLED_FILE_ENDING_JBC;
    public static final JavaByteCode INSTANCE = new JavaByteCode();
    protected JavaByteCode() {
        super(ID);
    }
}
