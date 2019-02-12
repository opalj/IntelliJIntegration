package JavaByteCodeLanguage.psi;

import JavaByteCodeLanguage.LanguageAndFileType.JavaByteCodeFileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;

public class JavaByteCodeElementFactory {
    public static JavaByteCodeProperty createProperty(Project project, String name) {
        final JavaByteCodeFile file = createFile(project, name);
        return (JavaByteCodeProperty) file.getFirstChild();
    }

    public static JavaByteCodeFile createFile(Project project, String text) {
        String name = "dummy.simple";
        return (JavaByteCodeFile) PsiFileFactory.getInstance(project).
                createFileFromText(name, JavaByteCodeFileType.INSTANCE, text);
    }
}