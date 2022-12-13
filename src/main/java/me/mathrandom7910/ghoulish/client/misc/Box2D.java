package me.mathrandom7910.ghoulish.client.misc;

public class Box2D extends Pos {
    private final int endX;
    private final int endY;

    public Box2D(int x, int y, int endX, int endY) {
        super(x, y);

        this.endX = endX;
        this.endY = endY;
    }

    public int getWidth() {
        return endX - getX();
    }

    public int getHeight() {
        return endY - getY();
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }
}
