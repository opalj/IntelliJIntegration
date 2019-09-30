/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import globalData.TACKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
@State(
        name="BytecodeConfig",
        storages = {@Storage("bytecodeconfig.xml")}
)
public class BytecodeConfig implements PersistentStateComponent<BytecodeConfig> {
    //fields to save into xml should be public
    // http://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html
    // TAC-Configs
    public TACKey tacKey = TACKey.ONE;
    public TACKey getTacKey() {
        return tacKey;
    }
    public void setTacKey(TACKey tacKey) {
        this.tacKey = tacKey;
    }
    // Project-Config
    public String ProjectConfigString = "";
    public String getProjectConfigString() {
        return ProjectConfigString;
    }

    public void setProjectConfigString(String projectConfigString) {
        ProjectConfigString = projectConfigString;
    }

    @Nullable
    @Override
    public BytecodeConfig getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull BytecodeConfig state) {
        XmlSerializerUtil.copyBean(state,this);
    }

    @Override
    public void noStateLoaded() {
        tacKey = TACKey.ONE;
    }
    @Nullable
    public static BytecodeConfig getInstance() {
        return ServiceManager.getService(BytecodeConfig.class);
    }
}
