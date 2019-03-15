package syntaxHighlighting;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import syntaxHighlighting.psi.TAC_file;

import java.util.*;

public class TAC_util {
    public static List<TACProperty> findProperties(Project project, String key) {
        List<TACProperty> result = null;
        Collection<VirtualFile> virtualFiles =
                FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, TAC_filetype.INSTANCE,
                        GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            TAC_file simpleFile = (TAC_file) PsiManager.getInstance(project).findFile(virtualFile);
            if (simpleFile != null) {
                TACProperty[] properties = PsiTreeUtil.getChildrenOfType(simpleFile, TACProperty.class);
                if (properties != null) {
                    for (TACProperty property : properties) {
                        if (key.equals(property.getKey())) {
                            if (result == null) {
                                result = new ArrayList<TACProperty>();
                            }
                            result.add(property);
                        }
                    }
                }
            }
        }
        return result != null ? result : Collections.<TACProperty>emptyList();
    }

    public static List<TACProperty> findProperties(Project project) {
        List<TACProperty> result = new ArrayList<TACProperty>();
        Collection<VirtualFile> virtualFiles =
                FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, TAC_filetype.INSTANCE,
                        GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            TAC_file simpleFile = (TAC_file) PsiManager.getInstance(project).findFile(virtualFile);
            if (simpleFile != null) {
                TACProperty[] properties = PsiTreeUtil.getChildrenOfType(simpleFile, TACProperty.class);
                if (properties != null) {
                    Collections.addAll(result, properties);
                }
            }
        }
        return result;
    }
}