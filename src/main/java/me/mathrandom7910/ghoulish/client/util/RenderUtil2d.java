package me.mathrandom7910.ghoulish.client.util;

import me.mathrandom7910.ghoulish.client.misc.Box2D;
import me.mathrandom7910.ghoulish.client.misc.MCInst;
import me.mathrandom7910.ghoulish.client.misc.Pos;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.Color;

public class RenderUtil2d implements MCInst {
    public static final int FONT_HEIGHT = mc.textRenderer.fontHeight;

    public static int measureText(String text) {
        if(mc.textRenderer == null) return 50;
        return mc.textRenderer.getWidth(text);
    }

    public static void drawText(MatrixStack stack, String text, float x, float y, Color color) {
        mc.textRenderer.draw(stack, text, x, y, color.hashCode());
    }

    public static Box2D drawBox(MatrixStack stack, int x, int y, int x1, int y1, Color color) {
        Screen.fill(stack, x, y, x1, y1, color.hashCode());
        return new Box2D(x, y, x1, y1);
    }

    public static Box2D drawBox(MatrixStack stack, Pos pos, int w, int h, Color color) {
        return drawBox(stack, pos.getX(), pos.getY(), pos.getX() + w, pos.getY() + h, color);
    }

    public static void drawText(MatrixStack stack, String text, Pos pos, Color color) {
        drawText(stack, text, pos.getX(), pos.getY(), color);
    }
}
