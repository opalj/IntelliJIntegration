package syntaxHighlighting;

import com.intellij.navigation.*;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TAC_chooseByNameContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        List<TACProperty> properties = TAC_util.findProperties(project);
        List<String> names = new ArrayList<String>(properties.size());
        for (TACProperty property : properties) {
            if (property.getKey() != null && property.getKey().length() > 0) {
                names.add(property.getKey());
            }
        }
        return names.toArray(new String[names.size()]);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        // todo include non project items
        List<TACProperty> properties = TAC_util.findProperties(project, name);
        return properties.toArray(new NavigationItem[properties.size()]);
    }
}