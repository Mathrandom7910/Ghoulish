package me.mathrandom7910.ghoulish.client.features.modules.module.modules.player;

import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubTick;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.EnumSetting;
import me.mathrandom7910.ghoulish.client.util.BlockUtil;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;

public class Eagle extends Module implements ISubTick {
    private boolean onG = false;
    private final EnumSetting<EagleMode> eagleMode = addEnum("eaglemode", "mode at which to automatically sneak", EagleMode.BASIC);

    public Eagle() {
        super("eagle", "sneaks at the edge of blocks", Category.PLAYER);
    }

    @Override
    public void onTick(SubData<ClientWorld, Void> subData) {
        if(eagleMode.get() == EagleMode.BASIC) {
            if (!mc.player.isOnGround()) {
                if (!onG) {
                    mc.options.sneakKey.setPressed(false);
                }
                onG = true;
                return;
            } else {
                onG = false;
            }

            mc.options.sneakKey.setPressed(BlockUtil.getBlock(mc.player.getBlockPos().down()).equals(Blocks.AIR));
        } else {
            eagleMode.set(EagleMode.BASIC);
        }
    }

    @Override
    public void onDisable() {
        mc.options.sneakKey.setPressed(false);
    }

    private enum EagleMode {
        BASIC,
        CUSTOM
    }
}
