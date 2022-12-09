package me.mathrandom7910.ghoulish.client.features.commands.commands.commands;

import me.mathrandom7910.ghoulish.client.features.commands.commands.Command;
import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;

public class SetCommand extends Command {
    public SetCommand() {
        super("set", "sets module settings");
    }

    @Override
    public boolean commandIn(String[] args) throws Exception {
        Module m = ModuleManager.getModule(args[0]);

        if(m == null) {
            info("unable to find module");
            return true;
        }

        Setting<?> s = m.getSetting(args[1]);

        if(s == null) {
            info("unable to find setting");
            return true;
        }

        s.set(args[2]);

        info("Set setting " + args[1] + " to " + args[2]);
        return true;
    }
}
