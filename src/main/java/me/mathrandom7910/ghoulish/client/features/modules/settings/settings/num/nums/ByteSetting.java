package me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import org.jetbrains.annotations.NotNull;

public class ByteSetting extends NumSetting<Byte> {
    public ByteSetting(String name, String desc, Byte defVal, Byte minVal, Byte maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, mod);
    }

    @Override
    public @NotNull Byte parseNum(String str) throws Exception {
        return Byte.parseByte(str);
    }

    @Override
    public boolean setLong(long lon) {
        return set((byte) lon);
    }
}
