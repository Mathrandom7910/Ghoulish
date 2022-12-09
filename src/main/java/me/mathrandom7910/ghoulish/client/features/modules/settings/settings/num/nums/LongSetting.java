package me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import org.jetbrains.annotations.NotNull;

public class LongSetting extends NumSetting<Long> {
    public LongSetting(String name, String desc, Long defVal, Long minVal, Long maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, mod);
    }

    @Override
    public @NotNull Long parseNum(String str) throws Exception {
        return Long.parseLong(str);
    }

    @Override
    public boolean setLong(long lon) {
        return set(lon);
    }
}
