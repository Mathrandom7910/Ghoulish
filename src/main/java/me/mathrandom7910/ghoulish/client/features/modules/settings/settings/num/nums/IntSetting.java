package me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import org.jetbrains.annotations.NotNull;

public class IntSetting extends NumSetting<Integer> {
    public IntSetting(String name, String desc, Integer defVal, Integer minVal, Integer maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, mod);
    }

    @Override
    public @NotNull Integer parseNum(String str) throws Exception {
        return Integer.parseInt(str);
    }

    @Override
    public boolean setLong(long lon) {
        return set((int) lon);
    }
}
