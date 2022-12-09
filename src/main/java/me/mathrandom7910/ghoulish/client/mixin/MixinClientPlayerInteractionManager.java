package me.mathrandom7910.ghoulish.client.mixin;

import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.combat.Reach;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {

    @Inject(method = "getReachDistance", at = @At("HEAD"), cancellable = true)
    public void getReachDistance(CallbackInfoReturnable<Float> cir) {
        Reach reach = ModuleManager.getModule(Reach.class);
        if(reach.isEnabled()) {
            cir.setReturnValue(ModuleManager.getModule(Reach.class).REACH.get());
        }
    }
}
