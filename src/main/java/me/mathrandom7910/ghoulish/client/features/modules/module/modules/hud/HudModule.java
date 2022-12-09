package me.mathrandom7910.ghoulish.client.features.modules.module.modules.hud;

import me.mathrandom7910.ConfigHandler.Config;
import me.mathrandom7910.ghoulish.client.GhoulishClient;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubHudRender;
import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.misc.Pos;
import org.jetbrains.annotations.NotNull;

public abstract class HudModule extends Module implements ISubHudRender {
    private Pos pos;
    private final Config cfg;
    public HudModule(String name, String desc) {
        super(name, desc, Category.HUD);

        cfg = GhoulishClient.CONFIG_HANDLER.addConfig("hud_" + name);
    }

    public abstract boolean doesMouseCollide(float mx, float my);

    public void setPos(@NotNull Pos pos) {
        this.pos = pos;

        if(!GhoulishClient.loaded) {
            return;
        }

        cfg.set("pos", GhoulishClient.GSON_INST.toJson(pos));
        try {
            cfg.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pos getPos() {
        return pos;
    }

    protected void defaultPos(Pos pos) {
        
    }
}
