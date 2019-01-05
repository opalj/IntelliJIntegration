package loadClassFile;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

public class LoadClassFileComponent implements ApplicationComponent {

  @NotNull
  public String getComponentName() {
    return "LoadClassFileComponent";
  }

  // If you register the MyPluginRegistration class in the <application-components> section of
  // the plugin.xml file, this method is called on IDEA start-up.
  public void initComponent() {
    ActionManager am = ActionManager.getInstance();
    LoadClassFileAction action = new LoadClassFileAction();
    OpenCorrespondingClassFileAction openCorrespondingClassFileAction = new OpenCorrespondingClassFileAction();

    // Passes an instance of your custom TextBoxes class to the registerAction method of the
    // ActionManager class.
    // am.registerAction("MyPluginAction", action);
    am.registerAction("LoadClassFileAction", action);
    am.registerAction("OpenCorrespondingClassFileAction", openCorrespondingClassFileAction);

    // ProjectViewPopupMenu --> Context menu of "Project"-View
    DefaultActionGroup windowM = (DefaultActionGroup) am.getAction("ProjectViewPopupMenu");

    // Adds a separator and a new menu command to the WindowMenu group on the main menu.
    windowM.addSeparator();
    windowM.add(action);
    windowM.add(openCorrespondingClassFileAction);
  }

  // Disposes system resources.
  public void disposeComponent() {}
}
