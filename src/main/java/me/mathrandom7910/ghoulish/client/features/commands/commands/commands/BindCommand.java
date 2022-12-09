package me.mathrandom7910.ghoulish.client.features.commands.commands.commands;

import me.mathrandom7910.ghoulish.client.features.commands.commands.Command;
import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind", "binds modules");
    }

    @Override
    public boolean commandIn(String[] args) throws Exception {
        Module m = ModuleManager.getModule(args[0]);

        if(m == null) {
            info("Unable to find module");
            return true;
        }

        ModuleManager.setBindingModule(m);
        info("binding... press a key");

        return true;
    }
}
