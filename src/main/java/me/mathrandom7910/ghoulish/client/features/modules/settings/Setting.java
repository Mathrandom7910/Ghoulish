package me.mathrandom7910.ghoulish.client.features.modules.settings;

import me.mathrandom7910.ghoulish.client.GhoulishClient;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.misc.Named;
import me.mathrandom7910.ghoulish.client.storage.StorageHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Setting<T> extends Named {
    private T val;
    private final Module mod;
    private final EnumSettingType enumSettingType;

    @Nullable
    private List<Requirement<?>> requirements;

    public T get() {
        return val;
    }

    public Setting(String name, String desc, T defVal, Module mod, EnumSettingType es) {
        super(name, desc);
        val = defVal;
        this.mod = mod;
        enumSettingType = es;
    }

    public abstract @NotNull T parse(String str) throws Exception;

    public boolean set(String str) throws Exception {
        return set(parse(str));
    }

    public boolean set(@NotNull T val) {
        if(val == null) {
            return false;
        }

        forceSet(val);
        return true;
    }

    public void forceSet(T val) {
        this.val = val;

        if(onRun != null) {
            onRun.run();
        }

        if (!GhoulishClient.loaded) return;

        var modulesMap = StorageHandler.DATA.getCategories()
                .get(mod.getCategory().name()).getModulesMap();
        modulesMap.computeIfAbsent(mod.getName(), e -> new HashMap<>())
                .put(getName(), toString());
        StorageHandler.save();
    }

    public EnumSettingType geteSettingType() {
        return enumSettingType;
    }

    @Override
    public String toString() {
        return get().toString();
    }

    public Module getMod() {
        return mod;
    }

    public <K> Setting<T> requires(Setting<K> setting, K toBe) {
        if(requirements == null) requirements = new ArrayList<>();
        requirements.add(new Requirement<>(setting, toBe));
        return this;
    }

    public record Requirement<T>(Setting<T> setting, T val) {
    }

    public @Nullable List<Requirement<?>> getRequirements() {
        return requirements;
    }

    private Runnable onRun = null;

    public Setting<T> onSet(Runnable r) {
        onRun = r;
        return this;
    }
}
