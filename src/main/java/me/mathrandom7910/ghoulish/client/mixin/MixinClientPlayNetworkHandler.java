package me.mathrandom7910.ghoulish.client.mixin;

import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.event.sub.Subscriptions;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.IDispatch;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubCPacket;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    public void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        SubData<Packet<?>, CallbackInfo> sd = new SubData<>(packet, ci);

        for(ISubCPacket scp : Subscriptions.C_PACKETS) {
            if(scp instanceof IDispatch d && !d.shouldDispatch()) continue;
            scp.onCPacket(sd);
        }
    }
}
