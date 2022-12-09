package me.mathrandom7910.ghoulish.client.features.commands.commands;

import me.mathrandom7910.ghoulish.client.misc.Named;
import me.mathrandom7910.ghoulish.client.util.ChatUtil;

public abstract class Command extends Named {
    public Command(String name, String desc) {
        super(name, desc);
    }

    public abstract boolean commandIn(String[] args) throws Exception;

    public void info(String msg) {
        ChatUtil.info(getName() + " : " + msg);
    }
}
