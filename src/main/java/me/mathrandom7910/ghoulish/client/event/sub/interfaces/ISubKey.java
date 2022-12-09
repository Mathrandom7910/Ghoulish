package me.mathrandom7910.ghoulish.client.event.sub.interfaces;

import me.mathrandom7910.ghoulish.client.misc.OnKeyEvent;
import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface ISubKey extends ISubscription {
    void onKey(SubData<OnKeyEvent, CallbackInfo> subData);
}
