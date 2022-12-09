package me.mathrandom7910.ghoulish.client.util;

import me.mathrandom7910.ghoulish.client.GhoulishClient;
import me.mathrandom7910.ghoulish.client.misc.MCInst;
import net.minecraft.text.Text;

public class ChatUtil implements MCInst {
    private static void sendMsg(String msg) {
        if(mc.player == null) return;
        mc.player.sendMessage(Text.of(msg));
    }

    public static void info(String msg) {
        sendMsg("[" + GhoulishClient.NAME + "] -> " + msg);
    }
}
