package me.mathrandom7910.ghoulish.client.features.modules.settings.other.minmax.minmax;

import me.mathrandom7910.ghoulish.client.features.modules.settings.other.minmax.MinMaxSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import org.jetbrains.annotations.NotNull;

public class MinMaxInt extends MinMaxSetting<Integer> {
    public MinMaxInt(NumSetting<Integer> minSet, NumSetting<Integer> maxSet) {
        super(minSet, maxSet);
    }

    @Override
    public @NotNull Integer getRandom() {
        return getMin() >= getMax() ? getMin() : random.nextInt(getMin(), getMax());
    }
}
