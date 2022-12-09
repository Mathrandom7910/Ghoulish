package me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.colorsettings;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.ColorCollection;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.RGBASettingCollection;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.BoolSetting;
import org.jetbrains.annotations.NotNull;

public class BoolColorSetting extends BoolSetting implements ColorCollection {
    private RGBASettingCollection rgbaSettingCollection;
    public BoolColorSetting(String name, String desc, Boolean defVal, Module mod) {
        super(name, desc, defVal, mod);
    }

    @Override
    public void setCollection(RGBASettingCollection rgbaSettingCollection) {
        this.rgbaSettingCollection = rgbaSettingCollection;
    }

    @Override
    public boolean set(@NotNull Boolean val) {
        boolean setV = super.set(val);
        rgbaSettingCollection.setColor();
        return setV;
    }
}
