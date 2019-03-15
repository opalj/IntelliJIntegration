package syntaxHighlighting;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import syntaxHighlighting.psi.TAC_file;

public class TAC_elementFactory {
    public static TACProperty createProperty(Project project, String name) {
        final TAC_file file = createFile(project, name);
        return (TACProperty) file.getFirstChild();
    }

    public static TAC_file createFile(Project project, String text) {
        String name = "dummy.tac";
        return (TAC_file) PsiFileFactory.getInstance(project).
                createFileFromText(name, TAC_filetype.INSTANCE, text);
    }
}
