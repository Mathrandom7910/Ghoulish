package me.mathrandom7910.ghoulish.client.mixin;

import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.client.VersionSpoof;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.ClientBrandRetriever.class)
public class MxinClientBrandRetriever {
    @Inject(at = @At("HEAD"), method = "getClientModName", cancellable = true, remap = false)
    private static void getClientModName(CallbackInfoReturnable<String> callback) {
        VersionSpoof vSpoof = ModuleManager.getModule(VersionSpoof.class);
        if(vSpoof != null && vSpoof.isEnabled()) {
            callback.setReturnValue("vanilla");
        }
    }
}
