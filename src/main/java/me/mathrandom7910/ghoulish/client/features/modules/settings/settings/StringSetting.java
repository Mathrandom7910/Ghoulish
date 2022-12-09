package me.mathrandom7910.ghoulish.client.features.modules.settings.settings;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.EnumSettingType;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;
import org.jetbrains.annotations.NotNull;

public class StringSetting extends Setting<String> {
    public StringSetting(String name, String desc, String defVal, Module mod) {
        super(name, desc, defVal, mod, EnumSettingType.STR);
    }

    @Override
    public @NotNull String parse(String str) throws Exception {
        return str;
    }

    public boolean set(@NotNull String val) {
        forceSet(val);
        return true;
    }
}
