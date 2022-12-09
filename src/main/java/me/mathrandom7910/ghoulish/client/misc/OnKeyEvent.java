package me.mathrandom7910.ghoulish.client.misc;

public record OnKeyEvent(long window, int key, int scancode, int action, int modifier) {
    @Override
    public String toString() {
        return "KeyEvent{" +
                "window=" + window +
                ", key=" + key +
                ", scancode=" + scancode +
                ", action=" + action +
                ", modifier=" + modifier +
                '}';
    }
}
