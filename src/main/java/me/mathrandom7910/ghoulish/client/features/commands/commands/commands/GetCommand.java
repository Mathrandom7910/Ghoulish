package me.mathrandom7910.ghoulish.client.features.commands.commands.commands;

import me.mathrandom7910.ghoulish.client.features.commands.commands.Command;
import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;

public class GetCommand extends Command {
    public GetCommand() {
        super("get", "gets module & setting info");
    }

    @Override
    public boolean commandIn(String[] args) throws Exception {
        Module module = ModuleManager.getModule(args[0]);

        if(module == null) {
            info("No module exists with that name");
            return true;
        }

        if(args.length == 1) {
            info(module.getDesc());
            return true;
        }

        Setting<?> setting = module.getSetting(args[1]);

        if(setting == null) {
            info("No setting exists in " + args[0] + " with that name");
            return true;
        }

        info(setting.getDesc());
        return true;
    }
}
