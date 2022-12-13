package me.mathrandom7910.ghoulish.client.features.modules.module.modules.hud;

import me.mathrandom7910.ConfigHandler.Config;
import me.mathrandom7910.ghoulish.client.GhoulishClient;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubHudRender;
import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.misc.Pos;
import org.jetbrains.annotations.Range;

public abstract class HudModule extends Module implements ISubHudRender {
    private float percX;
    private float percY;
    private final Config cfg;

    public HudModule(String name, String desc, @Range(from = 0, to = 1) float pX, @Range(from = 0, to = 1) float pY) {
        super(name, desc, Category.HUD);

        cfg = GhoulishClient.CONFIG_HANDLER.addConfig("hud_" + name);

        cfg.initVal("x", String.valueOf(pX));
        cfg.initVal("y", String.valueOf(pY));

        percX = Float.parseFloat(cfg.get("x"));
        percY = Float.parseFloat(cfg.get("y"));

        if(percX > 1) {
            percX = .5f;
        }

        if (percY > 1) {
            percY = .5f;
        }

        savePos();
    }

    public abstract boolean doesMouseCollide(double mx, double my);

    public void setPos(int x, int y) {
        percX = x / (float) (mc.getWindow().getWidth() / 2);
        percY = y / (float) (mc.getWindow().getHeight() / 2);

        if(!GhoulishClient.loaded) {
            return;
        }

        savePos();
    }

    private void savePos() {
        cfg.set("x", String.valueOf(percX));
        cfg.set("y", String.valueOf(percY));
        try {
            cfg.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pos getPos() {
        return new Pos((int) (percX * (mc.getWindow().getWidth() / 2)), (int) (percY * (mc.getWindow().getHeight() / 2)));
    }

    protected Quadrant getQuadrant() {
        int x = getPos().getX();
        int y = getPos().getY();
        int hWidth = mc.getWindow().getWidth() / 4;
        int hHeight = mc.getWindow().getHeight() / 4;

        if (x < hWidth) {
            if(y < hHeight) {
                return Quadrant.LEFT_UP;
            } else {
                return Quadrant.LEFT_DOWN;
            }
        } else {
            if(y < hHeight) {
                return  Quadrant.RIGHT_UP;
            } else {
                return Quadrant.RIGHT_DOWN;
            }
        }
    }

    public void snap() {
    }

    protected enum Quadrant {
        LEFT_UP,
        RIGHT_UP,
        LEFT_DOWN,
        RIGHT_DOWN
    }
}
