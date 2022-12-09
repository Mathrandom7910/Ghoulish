package me.mathrandom7910.ghoulish.client.features.modules.settings.settings;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.EnumSettingType;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public class ColorSetting extends Setting<Color> {
    public ColorSetting(String name, String desc, Color defVal, Module mod) {
        super(name, desc, defVal, mod, EnumSettingType.COL);
    }

    @Override
    public @NotNull Color parse(String str) throws Exception {
        String[] spl = str.split(",");
        int r = Integer.parseInt(spl[0]);
        int g = Integer.parseInt(spl[1]);
        int b = Integer.parseInt(spl[2]);
        int a = Integer.parseInt(spl[3]);
        return new Color(r, g, b, a);
    }

    @Override
    public String toString() {
        return get().getRed() + "," + get().getGreen() + "," + get().getBlue() + "," + get().getAlpha();
    }
}
