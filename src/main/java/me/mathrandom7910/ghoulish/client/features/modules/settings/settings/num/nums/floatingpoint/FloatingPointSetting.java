package me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.NumSetting;

public abstract class FloatingPointSetting<T extends Number & Comparable<T>> extends NumSetting<T> {
    public FloatingPointSetting(String name, String desc, T defVal, T minVal, T maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, mod);
    }

    public abstract boolean setDouble(double dub);
}
