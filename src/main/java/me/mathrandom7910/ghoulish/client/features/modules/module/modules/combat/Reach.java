package me.mathrandom7910.ghoulish.client.features.modules.module.modules.combat;

import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.FloatSetting;

public class Reach extends Module {
    public FloatSetting REACH = addFloat("reach", "the amount of time it takes to fly (obviously)", 4.5F, 3, 8);

    public Reach() {
        super("reach", "reach further", Category.COMBAT);
    }
}
