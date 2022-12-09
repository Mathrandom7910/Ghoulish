package me.mathrandom7910.ghoulish.client.mixin;

import me.mathrandom7910.ghoulish.client.misc.OnKeyEvent;
import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.event.sub.Subscriptions;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubKey;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void onKeyEvent(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        SubData<OnKeyEvent, CallbackInfo> sd = new SubData<>(new OnKeyEvent(window, key, scancode, action, modifiers), ci);
        for(ISubKey sk : Subscriptions.KEYS) {
            sk.onKey(sd);
        }
    }
}
