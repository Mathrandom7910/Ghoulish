package me.mathrandom7910.ghoulish.client.features.modules.module.modules.combat;

import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.minmax.minmax.MinMaxInt;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.BoolSetting;
import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubTick;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.Hand;

public class AutoAttack extends Module implements ISubTick {
    private final MinMaxInt hitDelay = addMinMaxInt("hitdelay", "random delay for how long an ememy should be on your cursor before attacking", 2, 0, 10);
    private final BoolSetting playersOnly = addBool("playersonly", "only attacks players", true);
    private final BoolSetting stopSprint = addBool("stopsprint", "sends stop sprint packet", true);
    private int tick;
    private int nextTick = -1;
    public AutoAttack() {
        super("autoattack", "auto attacks enemies on your cursor", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        nextTick = hitDelay.getRandom();
        tick = 0;
    }

    @Override
    public void onTick(SubData<ClientWorld, Void> subData) {
        if(mc.interactionManager != null && (playersOnly.get() ? mc.targetedEntity instanceof PlayerEntity : mc.targetedEntity != null) && mc.player != null && mc.player.getAttackCooldownProgress(.5f) == 1F && mc.targetedEntity.isAlive() && !mc.player.isUsingItem()) {
            tick++;

            if(tick > nextTick) {
                tick = 0;
                nextTick = hitDelay.getRandom();

                boolean spr = false;
                if(stopSprint.get() && mc.player.isSprinting()) {
                    spr = true;
                    mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
                }

                mc.interactionManager.attackEntity(mc.player, mc.targetedEntity);
                mc.player.swingHand(Hand.MAIN_HAND);

                if(spr) {
                    mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
                }
            }
        }
    }
}
