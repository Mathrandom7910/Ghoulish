package me.mathrandom7910.ghoulish.client.features.modules.settings.other.color;

import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.colorsettings.BoolColorSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.colorsettings.FloatColorSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.colorsettings.IntColorSetting;

import java.awt.Color;

public class RGBASettingCollection {
    private final IntColorSetting r;
    private final IntColorSetting g;
    private final IntColorSetting b;
    private final IntColorSetting a;
    private final BoolColorSetting rainBow;
    private final FloatColorSetting speed;
    private final FloatColorSetting saturation;
    private final FloatColorSetting brightness;

    private Color color;
    private float rainBowHSB = 0;

    private Setting<?>[] colorSettings;

    public RGBASettingCollection(IntColorSetting r, IntColorSetting g, IntColorSetting b, IntColorSetting a, BoolColorSetting rainBow, FloatColorSetting speed, FloatColorSetting saturation, FloatColorSetting brightness) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.rainBow = rainBow;
        this.speed = speed;
        this.saturation = saturation;
        this.brightness = brightness;

        r.setCollection(this);
        g.setCollection(this);
        b.setCollection(this);
        a.setCollection(this);
        rainBow.setCollection(this);
        speed.setCollection(this);
        saturation.setCollection(this);
        brightness.setCollection(this);

        rainBow.onSet(this::setColor);

        speed.requires(rainBow, true);
        saturation.requires(rainBow, true);
        brightness.requires(rainBow, true);


        r.requires(rainBow, false);
        g.requires(rainBow, false);
        b.requires(rainBow, false);
//        a.requires(rainBow, false);

        setColor();

        colorSettings = new Setting<?>[]{r, g, b, a, rainBow, speed, saturation, brightness};
    }

    public void setColor() {
        color = new Color(r.get(), g.get(), b.get(), a.get());
    }

    public Color getColor() {
        if(rainBow.get()) {
            rainBowHSB += speed.get();

            var tmpCol = Color.getHSBColor(rainBowHSB, saturation.get(), brightness.get());
            color = new Color(tmpCol.getRed(), tmpCol.getGreen(), tmpCol.getBlue(), a.get());


            if(rainBowHSB >= 1) rainBowHSB = 0;
        }
        return color;
    }

    public <K> RGBASettingCollection requires(Setting<K> setting, K toBe) {
        for(Setting<?> set : colorSettings) {
            set.requires(setting, toBe);
        }
        return this;
    }
}
