package me.mathrandom7910.ghoulish.client.event;

import me.mathrandom7910.ghoulish.client.event.events.HudRenderEvent;
import me.mathrandom7910.ghoulish.client.event.events.TickEvent;

public class EventInitializer {
    public static void init() {
        TickEvent.init();
        HudRenderEvent.init();
    }
}
