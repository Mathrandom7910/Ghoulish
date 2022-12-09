package me.mathrandom7910.ghoulish.client.event.sub.interfaces;

import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import net.minecraft.client.world.ClientWorld;

public interface ISubTick extends ISubscription{
    void onTick(SubData<ClientWorld, Void> subData);
}
