package me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.FloatingPointSetting;
import org.jetbrains.annotations.NotNull;

public class FloatSetting extends FloatingPointSetting<Float> {
    public FloatSetting(String name, String desc, Float defVal, Float minVal, Float maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, mod);
    }

    @Override
    public boolean setDouble(double dub) {
        return set((float) dub);
    }

    @Override
    public @NotNull Float parseNum(String str) throws Exception {
        return Float.parseFloat(str);
    }

    @Override
    public boolean setLong(long lon) {
        return set((float) lon);
    }

//    @Override
//    public <K> FloatSetting requires(Setting<K> setting, K toBe) {
//        return (FloatSetting) super.requires(setting, toBe);
//    }
}
