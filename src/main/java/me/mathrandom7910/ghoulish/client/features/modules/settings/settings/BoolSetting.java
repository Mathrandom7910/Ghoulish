package me.mathrandom7910.ghoulish.client.features.modules.settings.settings;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.EnumSettingType;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;
import org.jetbrains.annotations.NotNull;

public class BoolSetting extends Setting<Boolean> {
    public BoolSetting(String name, String desc, Boolean defVal, Module mod) {
        super(name, desc, defVal, mod, EnumSettingType.BOOL);
    }

    @Override
    public @NotNull Boolean parse(String str) throws Exception {
        if(!str.equals("true") && !str.equals("false")) throw new SettingParseException(getName(), str);
        return Boolean.parseBoolean(str);
    }

    @Override
    public boolean set(@NotNull Boolean val) {
        return super.set(val);
    }

    @Override
    public BoolSetting onSet(Runnable r) {
        return (BoolSetting) super.onSet(r);
    }
}
