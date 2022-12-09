package me.mathrandom7910.ghoulish.client.features.modules.settings.other.minmax;

import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class MinMaxSetting<T extends Number & Comparable<T>> {
    private final NumSetting<T> minSet;
    private final NumSetting<T> maxSet;

    protected final Random random = new Random();

    public MinMaxSetting(NumSetting<T> minSet, NumSetting<T> maxSet) {
        this.minSet = minSet;
        this.maxSet = maxSet;
    }

    public T getMax() {
        return maxSet.get();
    }

    public T getMin() {
        return minSet.get();
    }

    public abstract @NotNull T getRandom();
//
//    public int getRandom() {
//        return getMin().compareTo(getMax()) >= 0 ? getMin().intValue() :  random.nextInt(getMin().intValue(), getMax().intValue());
//    }
//
    public NumSetting<T> getMinSet() {
        return minSet;
    }

    public NumSetting<T> getMaxSet() {
        return maxSet;
    }

    public <K> MinMaxSetting<T> requires(Setting<K> setting, K toBe) {
        minSet.requires(setting, toBe);
        maxSet.requires(setting, toBe);
        return this;
    }
}
