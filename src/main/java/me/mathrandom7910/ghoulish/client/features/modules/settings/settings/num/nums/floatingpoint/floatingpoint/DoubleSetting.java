package me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.FloatingPointSetting;
import org.jetbrains.annotations.NotNull;

public class DoubleSetting extends FloatingPointSetting<Double> {
    public DoubleSetting(String name, String desc, Double defVal, Double minVal, Double maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, mod);
    }

    @Override
    public @NotNull Double parseNum(String str) throws Exception {
        return Double.parseDouble(str);
    }

    @Override
    public boolean setLong(long lon) {
        return set((double) lon);
    }

    @Override
    public boolean setDouble(double dub) {
        return set(dub);
    }
}
