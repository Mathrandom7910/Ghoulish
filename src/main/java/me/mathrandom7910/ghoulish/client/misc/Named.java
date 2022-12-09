package me.mathrandom7910.ghoulish.client.misc;

public class Named {
    private final String name;
    private final String desc;

    public Named(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
