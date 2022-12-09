package me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import org.jetbrains.annotations.NotNull;

public class ShortSetting extends NumSetting<Short> {
    public ShortSetting(String name, String desc, Short defVal, Short minVal, Short maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, mod);
    }

    @Override
    public @NotNull Short parseNum(String str) throws Exception {
        return Short.parseShort(str);
    }

    @Override
    public boolean setLong(long lon) {
        return set((short) lon);
    }
}
