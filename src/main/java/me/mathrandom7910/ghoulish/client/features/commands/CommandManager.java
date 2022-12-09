package me.mathrandom7910.ghoulish.client.features.commands;

import me.mathrandom7910.ghoulish.client.GhoulishClient;
import me.mathrandom7910.ghoulish.client.features.commands.commands.Command;
import me.mathrandom7910.ghoulish.client.features.commands.commands.commands.*;
import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.event.sub.Subscriptions;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubCPacket;
import me.mathrandom7910.ghoulish.client.util.ChatUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements ISubCPacket {
    public static final List<Command> COMMANDS = new ArrayList<>();

    public static void init() {
        addCommands();

        Subscriptions.addSub(new CommandManager());
    }

    private static void addCommands() {
        add(new ListCommand());
        add(new HelpCommand());
        add(new ToggleCommand());
        add(new SetCommand());
        add(new BindCommand());
        add(new GetCommand());
        add(new CrackedAuthCommand());
    }

    private static void add(Command cmd) {
        COMMANDS.add(cmd);
    }

    public static @Nullable Command getCommand(String cmdName) {
        for(Command cmd : COMMANDS) {
            if(cmd.getName().equalsIgnoreCase(cmdName)) {
                return cmd;
            }
        }
        return null;
    }

    @Override
    public void onCPacket(SubData<Packet<?>, CallbackInfo> subData) {
        if(subData.data() instanceof ChatMessageC2SPacket chat) {
            String msg = chat.chatMessage().toLowerCase();
            if(msg.startsWith(GhoulishClient.PREFIX)) {
                subData.ci().cancel();
                String[] spl = msg.substring(GhoulishClient.PREFIX.length()).split(" ");
                String cmdName = spl[0];
                String[] args = Arrays.copyOfRange(spl, 1, spl.length);
                
                for(Command cmd : COMMANDS) {
                    if(cmd.getName().equalsIgnoreCase(cmdName)) {
                        try {
                            if(cmd.commandIn(args)) {

                            } else {
                                ChatUtil.info("Issue with command");
                            }
                        } catch (Exception e) {
                            ChatUtil.info("Failed to handle command " + e.getMessage());
                            e.printStackTrace();
                        }

                        return;
                    }
                }
                ChatUtil.info("Unable to find command " + cmdName);
            }
        }
    }
}
