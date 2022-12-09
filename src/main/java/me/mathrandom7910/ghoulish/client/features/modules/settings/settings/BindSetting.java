package me.mathrandom7910.ghoulish.client.features.modules.settings.settings;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.EnumSettingType;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;
import org.jetbrains.annotations.NotNull;

public class BindSetting extends Setting<Integer> {
    public BindSetting(String name, String desc, Integer defVal, Module mod) {
        super(name, desc, defVal, mod, EnumSettingType.BIND);
    }

    @Override
    public @NotNull Integer parse(String str) throws Exception {
        return Integer.parseInt(str);
    }
}
