package me.mathrandom7910.ghoulish.client.features.modules.settings.other.minmax.minmax;

import me.mathrandom7910.ghoulish.client.features.modules.settings.other.minmax.MinMaxSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import org.jetbrains.annotations.NotNull;

public class MinMaxFloat extends MinMaxSetting<Float> {
    public MinMaxFloat(NumSetting<Float> minSet, NumSetting<Float> maxSet) {
        super(minSet, maxSet);
    }

    @Override
    public @NotNull Float getRandom() {
        return getMin() >= getMax() ? getMin() : random.nextFloat(getMin(), getMax());
    }
}
