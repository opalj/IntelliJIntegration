package syntaxHighlighting;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

public class TAC_elementFactory {

    public static TAC_file createFile(Project project, String text) {
        String name = "dummy.tac";
        return (TAC_file) PsiFileFactory.getInstance(project).
                createFileFromText(name, TAC_filetype.INSTANCE, text);
    }

    public static TACJType createType(Project project, String name) {
        final TAC_file file = createFile(project, name);
        return (TACJType) file.getFirstChild();
    }

    public static TACMethodName createMethodName(Project project, String name) {
        final TAC_file file = createFile(project, name);
        return (TACMethodName) file.getFirstChild();
    }

    public static TACMethodHead createMethodHead(Project project, String name) {
        final TAC_file file = createFile(project, name);
        return (TACMethodHead) file.getFirstChild();
    }
}
