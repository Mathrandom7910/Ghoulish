package me.mathrandom7910.ghoulish.client.features.commands.commands.commands;

import me.mathrandom7910.ghoulish.client.features.commands.commands.Command;
import me.mathrandom7910.ghoulish.client.misc.MCInst;
import net.minecraft.client.util.Session;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

public class CrackedAuthCommand extends Command implements MCInst {
    public CrackedAuthCommand() {
        super("crackedauth", "logs in as a cracked user");
    }

    @Override
    public boolean commandIn(String[] args) throws Exception {
        Field field = mc.getClass().getDeclaredField("session");
        field.setAccessible(true);
        field.set(mc, new Session(args[0], UUID.randomUUID().toString(), "0", Optional.empty(), Optional.empty(), Session.AccountType.LEGACY));
        info("Set cracked session, relog to take effect.");
        return true;
    }
}
