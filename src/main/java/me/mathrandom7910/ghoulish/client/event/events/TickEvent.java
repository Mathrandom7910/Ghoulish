package me.mathrandom7910.ghoulish.client.event.events;

import me.mathrandom7910.ghoulish.client.misc.MCInst;
import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.event.sub.Subscriptions;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.IDispatch;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubTick;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.world.ClientWorld;


public class TickEvent implements MCInst {
    public static void init() {
        ClientTickEvents.START_WORLD_TICK.register(TickEvent::dispatch);
    }

    private static void dispatch(ClientWorld clientWorld) {
        SubData<ClientWorld, Void> sd = new SubData<>(clientWorld);
        for(ISubTick ist : Subscriptions.TICKS) {
            if(ist instanceof IDispatch d && !d.shouldDispatch()) continue;
            ist.onTick(sd);
        }
    }
}
