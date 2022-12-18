package me.mathrandom7910.ghoulish.client.features.modules.module.modules.hud;

import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubHudRender;
import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.FloatSetting;
import me.mathrandom7910.ghoulish.client.misc.Pos;
import org.jetbrains.annotations.Range;

public abstract class HudModule extends Module implements ISubHudRender {
    private final FloatSetting xPerc;
    private final FloatSetting yPerc;

    public HudModule(String name, String desc, @Range(from = 0, to = 1) float pX, @Range(from = 0, to = 1) float pY) {
        super(name, desc, Category.HUD);

        xPerc = addFloat("x", "x percentage of this module when rendered", pX, 0, 1);
        yPerc = addFloat("y", "y percentage of this module when rendered", pY, 0, 1);
    }

    public abstract boolean doesMouseCollide(double mx, double my);

    public void setPos(int x, int y) {
        xPerc.set(x / (float) (mc.getWindow().getWidth() / 2));
        yPerc.set(y / (float) (mc.getWindow().getHeight() / 2));
    }

    public Pos getPos() {
        return new Pos((int) (xPerc.get() * (mc.getWindow().getWidth() / 2)), (int) (yPerc.get() * (mc.getWindow().getHeight() / 2)));
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
