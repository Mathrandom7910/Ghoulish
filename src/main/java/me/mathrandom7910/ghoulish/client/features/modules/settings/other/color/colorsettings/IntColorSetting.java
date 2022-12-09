package me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.colorsettings;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.ColorCollection;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.RGBASettingCollection;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.IntSetting;
import org.jetbrains.annotations.NotNull;

public class IntColorSetting extends IntSetting implements ColorCollection {
    private RGBASettingCollection rgbaSettingCollection;

    public IntColorSetting(String name, String desc, int defVal, Module mod) {
        super(name, desc, defVal, 0, 255, mod);
    }

    @Override
    public void setCollection(RGBASettingCollection rgbaSettingCollection) {
        this.rgbaSettingCollection = rgbaSettingCollection;
    }

    @Override
    public boolean set(@NotNull Integer val) {
        boolean setV = super.set(val);
        rgbaSettingCollection.setColor();
        return setV;
    }
}
