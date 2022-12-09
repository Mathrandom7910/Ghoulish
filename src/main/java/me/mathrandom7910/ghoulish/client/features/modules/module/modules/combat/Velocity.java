package me.mathrandom7910.ghoulish.client.features.modules.module.modules.combat;

import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubCPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Velocity extends Module implements ISubCPacket {
    public Velocity() {
        super("velocity", "take less kb ig", Category.COMBAT);
    }

    @Override
    public void onCPacket(SubData<Packet<?>, CallbackInfo> subData) {
        if(subData.data() instanceof EntityVelocityUpdateS2CPacket velPack && velPack.getId() == mc.player.getId()) {
            velPack.getVelocityX();
        }
    }
}
