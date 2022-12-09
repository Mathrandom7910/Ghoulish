package me.mathrandom7910.ghoulish.client.event.sub.interfaces;

import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface ISubCPacket extends ISubscription {
   void onCPacket(SubData<Packet<?>, CallbackInfo> subData);
}
