package me.mathrandom7910.ghoulish.client.features.modules.settings.settings;

import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.EnumSettingType;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;
import org.jetbrains.annotations.NotNull;

public class EnumSetting<T extends Enum<?>> extends Setting<T> {
    private int enumOn = 0;
    public EnumSetting(String name, String desc, T defVal, Module mod) {
        super(name, desc, defVal, mod, EnumSettingType.ENUM);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull T parse(String str) throws Exception {
        for(T e : getEnums()) {
            if(e.name().equalsIgnoreCase(str)) {
                return e;
            }
        }
        throw new SettingParseException(getName(), str);
    }

    @SuppressWarnings("unchecked")
    public T next() {
        Enum<?>[] consts = getEnums();
        enumOn++;
        if(enumOn >= consts.length) {
            enumOn = 0;
        }
        set((T) consts[enumOn]);
        return get();
    }

    @SuppressWarnings("unchecked")
    private T[] getEnums() {
        return (T[]) get().getClass().getEnumConstants();
    }

    @SuppressWarnings("unchecked")
    public T prev() {
        enumOn--;
        Enum<?>[] consts = getEnums();
        if(enumOn < 0) {
            enumOn = consts.length - 1;
        }
        set((T) consts[enumOn]);
        return get();
    }

    @Override
    public <K> EnumSetting<T> requires(Setting<K> setting, K toBe) {
        return (EnumSetting<T>) super.requires(setting, toBe);
    }

    @Override
    public boolean set(@NotNull T val) {
        for(int i = 0; i < getEnums().length; i++) {
            if(getEnums()[i].equals(val)) {
                enumOn = i;
                break;
            }
        }
        return super.set(val);
    }

    @Override
    public EnumSetting<T> onSet(Runnable r) {
        return (EnumSetting<T>) super.onSet(r);
    }
}
