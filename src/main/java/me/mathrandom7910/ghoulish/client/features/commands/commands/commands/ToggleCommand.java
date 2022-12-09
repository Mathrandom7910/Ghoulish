package me.mathrandom7910.ghoulish.client.features.commands.commands.commands;

import me.mathrandom7910.ghoulish.client.features.commands.commands.Command;
import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", "toggles modules");
    }

    @Override
    public boolean commandIn(String[] args) {
        Module m = ModuleManager.getModule(args[0]);
        if(m != null) {
            m.toggle();
            return true;
        }

        info("Unable to find module");
        return false;
    }
}
