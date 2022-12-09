package me.mathrandom7910.ghoulish.client.event.events;

import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.event.sub.Subscriptions;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.IDispatch;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubHudRender;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.util.math.MatrixStack;

public class HudRenderEvent {
    public static void init() {
        HudRenderCallback.EVENT.register(HudRenderEvent::dispatch);
    }

    public static void dispatch(MatrixStack matrixStack, float tickDelta) {
        SubData<HudRenderData, Void> sd = new SubData<>(new HudRenderData(matrixStack, tickDelta));
        for(ISubHudRender ist : Subscriptions.HUD_RENDER) {
            if(ist instanceof IDispatch d && !d.shouldDispatch()) continue;
            ist.onRender(sd);
        }
    }

    public record HudRenderData(MatrixStack stack, float tickDelta) {

    }
}
