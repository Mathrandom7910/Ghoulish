package me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.colorsettings;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.ColorCollection;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.RGBASettingCollection;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.FloatSetting;
import org.jetbrains.annotations.NotNull;

public class FloatColorSetting extends FloatSetting implements ColorCollection {
    private RGBASettingCollection rgbaSettingCollection;

    public FloatColorSetting(String name, String desc, Float defVal, Module mod) {
        super(name, desc, defVal, 0f, 1f, mod);
    }

    public FloatColorSetting(String name, String desc, float defVal, float min, float max, Module module) {
        super(name, desc, defVal, min, max, module);
    }

    @Override
    public void setCollection(RGBASettingCollection rgbaSettingCollection) {
        this.rgbaSettingCollection = rgbaSettingCollection;
    }

    @Override
    public boolean set(@NotNull Float val) {
        boolean setV = super.set(val);
        rgbaSettingCollection.setColor();
        return setV;
    }
}
