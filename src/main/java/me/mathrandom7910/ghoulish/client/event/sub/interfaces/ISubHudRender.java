package me.mathrandom7910.ghoulish.client.event.sub.interfaces;

import me.mathrandom7910.ghoulish.client.event.events.HudRenderEvent;
import me.mathrandom7910.ghoulish.client.event.sub.SubData;

public interface ISubHudRender extends ISubscription {
    void onRender(SubData<HudRenderEvent.HudRenderData, Void> subData);
}
