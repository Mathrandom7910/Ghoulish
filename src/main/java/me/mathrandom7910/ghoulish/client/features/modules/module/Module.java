package me.mathrandom7910.ghoulish.client.features.modules.module;

import me.mathrandom7910.ghoulish.client.event.sub.interfaces.IDispatch;
import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.RGBASettingCollection;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.colorsettings.BoolColorSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.colorsettings.FloatColorSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.colorsettings.IntColorSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.minmax.MinMaxSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.minmax.minmax.MinMaxFloat;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.minmax.minmax.MinMaxInt;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.BindSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.BoolSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.EnumSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.StringSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.ByteSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.IntSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.LongSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.ShortSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.DoubleSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.FloatSetting;
import me.mathrandom7910.ghoulish.client.misc.MCInst;
import me.mathrandom7910.ghoulish.client.misc.Named;
import me.mathrandom7910.ghoulish.client.misc.ThreadHolder;
import me.mathrandom7910.ghoulish.client.util.ChatUtil;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Module extends Named implements MCInst, IDispatch {
    private final List<Setting<?>> settings = new ArrayList<>();
    private final Category category;
    private final BoolSetting enabled = addBool("enabled", "", false);
    private final BindSetting bind = addBind("bind", "");
    private final BoolSetting notifs = addBool("notifications", "", true);
    private final BoolSetting renderOnList = addBool("renderonlist", "renders this module on the arraylist", true);

    public boolean shouldRender() {
        return renderOnList.get();
    }

    public @Nullable Setting<?> getSetting(String name) {
        for (Setting<?> s : getSettings()) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    public Module(String name, String desc, Category cat) {
        super(name, desc);
        category = cat;
    }
    
    public final void enable() {
        onEnable();
        for (var th : intervals) {
            th.setCanceled(false);
            th.start();
        }
        enabled.set(true);
        if (notifs.get()) info("enabled");
    }

    public final void disable() {
        onDisable();
        for (var th : intervals) {
            th.setCanceled(true);
        }
        enabled.set(false);
        if (notifs.get()) info("disabled");
    }

    public final void toggle() {
        if (enabled.get()) {
            disable();
        } else enable();
    }

    public final void postInit() {
        onPostInit();
    }

    protected void info(String msg) {
        ChatUtil.info(getName() + " : " + msg);
    }

    private <T extends Setting<?>> T addSetting(T setting) {
        settings.add(setting);
        return setting;
    }

    protected BoolSetting addBool(String name, String desc, boolean defaultVal) {
        return addSetting(new BoolSetting(name, desc, defaultVal, this));
    }

    protected BindSetting addBind(String name, String desc) {
        return addSetting(new BindSetting(name, desc, -1, this));
    }

    protected ByteSetting addByte(String name, String desc, @Range(from = Byte.MIN_VALUE, to = Byte.MAX_VALUE) int defVal, @Range(from = Byte.MIN_VALUE, to = Byte.MAX_VALUE) int min, @Range(from = Byte.MIN_VALUE, to = Byte.MAX_VALUE) int max) {
        return addSetting(new ByteSetting(name, desc, (byte) defVal, (byte) min, (byte) max, this));
    }

    protected ShortSetting addShort(String name, String desc, @Range(from = Short.MIN_VALUE, to = Short.MAX_VALUE) int defVal, @Range(from = Short.MIN_VALUE, to = Short.MAX_VALUE) int min, @Range(from = Short.MIN_VALUE, to = Short.MAX_VALUE) int max) {
        return addSetting(new ShortSetting(name, desc, (short) defVal, (short) min, (short) max, this));
    }

    private IntColorSetting addIntCol(String name, String desc, int defVal) {
        return addSetting(new IntColorSetting(name, desc, defVal, this));
    }

    private FloatColorSetting addFloatCol(String name, String desc, float defVal) {
        return addSetting(new FloatColorSetting(name, desc, defVal, this));
    }

    private FloatColorSetting addFloatCol(String name, String desc, float defVal, float min, float max) {
        return addSetting(new FloatColorSetting(name, desc, defVal, min, max, this));
    }

    protected RGBASettingCollection addCol(String name, String desc, Color defCol) {
        var r = addIntCol(name + "_red", desc, defCol.getRed());
        var g = addIntCol(name + "_green", desc, defCol.getGreen());
        var b = addIntCol(name + "_blue", desc, defCol.getBlue());
        var a = addIntCol(name + "_alpha", desc, defCol.getAlpha());

        var rainBow = addSetting(new BoolColorSetting(name + "_rainbow", desc, false, this));
        var speed = addFloatCol(name + "_rainbowspeed", desc, .001f, 0, .001f);
        var saturation = addFloatCol(name + "_rainbowsaturation", desc, 1);
        var brightness = addFloatCol(name + "_rainbowbrightness", desc, 1);
        return new RGBASettingCollection(r, g, b, a, rainBow, speed, saturation, brightness);
    }

    protected IntSetting addInt(String name, String desc, int defVal, int min, int max) {
        return addSetting(new IntSetting(name, desc, defVal, min, max, this));
    }

    protected DoubleSetting addDub(String name, String desc, double defVal, double min, double max) {
        return addSetting(new DoubleSetting(name, desc, defVal, min, max, this));
    }

    protected FloatSetting addFloat(String name, String desc, float defVal, float min, float max) {
        return addSetting(new FloatSetting(name, desc, defVal, min, max, this));
    }

    protected MinMaxInt addMinMaxInt(String name, String desc, int defVal, int min, int max) {
        IntSetting intMin = addInt(name + "min", desc, defVal, min, max);
        IntSetting intMax = addInt(name + "max", desc, defVal, min, max);
        return new MinMaxInt(intMin, intMax);
    }

    protected MinMaxSetting<Float> addMinMaxFloat(String name, String desc, float defVal, float min, float max) {
        FloatSetting fMin = addFloat(name + "min", desc, defVal, min, max);
        FloatSetting fMax = addFloat(name + "max", desc, defVal, min, max);
        return new MinMaxFloat(fMin, fMax);
    }

    protected LongSetting addLong(String name, String desc, long defVal, long min, long max) {
        return addSetting(new LongSetting(name, desc, defVal, min, max, this));
    }

    protected <T extends Enum<?>> EnumSetting<T> addEnum(String name, String desc, T defVal) {
        return addSetting(new EnumSetting<>(name, desc, defVal, this));
    }

    protected StringSetting addStr(String name, String desc, String defVal) {
        return addSetting(new StringSetting(name, desc, defVal, this));
    }

    public Category getCategory() {
        return category;
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }

    public BoolSetting getEnabled() {
        return enabled;
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public BindSetting getBind() {
        return bind;
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void onPostInit() {

    }

    private final List<ThreadHolder> intervals = new ArrayList<>();

    protected ThreadHolder createInterval(Runnable r, int interval) {
        ThreadHolder th = new ThreadHolder(r, interval);
//        th.setThread(new Thread(() -> {
//            while (true) {
//                if(mc.world == null) continue;
//
//                if(th.getCanceled()) break;
//
//                r.run();
//
//                try {
//                    Thread.sleep(th.getInt());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    break;
//                }
//            }
//        }));
//        th.setInt(interval);
        intervals.add(th);
        return th;
    }

    @Override
    public boolean shouldDispatch() {
        return enabled.get();
    }
}
