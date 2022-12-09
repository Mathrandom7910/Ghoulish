package me.mathrandom7910.ghoulish.client.features.commands.commands.commands;

import me.mathrandom7910.ghoulish.client.features.commands.commands.Command;
import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;

public class ListCommand extends Command {

    public ListCommand() {
        super("list", "lists modules");
    }

    @Override
    public boolean commandIn(String[] args) {
        var sb = new StringBuilder();

        for(int i = 0; i < ModuleManager.MODULES.size(); i++) {
            var module = ModuleManager.MODULES.get(i);
            sb.append(module.getName()).append(i == ModuleManager.MODULES.size() - 1 ? "" : ", ");
        }

        info(sb.toString());
        return true;
    }
}
