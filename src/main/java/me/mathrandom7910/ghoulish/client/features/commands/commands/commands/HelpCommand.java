package me.mathrandom7910.ghoulish.client.features.commands.commands.commands;

import me.mathrandom7910.ghoulish.client.features.commands.CommandManager;
import me.mathrandom7910.ghoulish.client.features.commands.commands.Command;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "lists commands");
    }

    private void infoCmds() {
        var sb = new StringBuilder();

        for(int i = 0; i < CommandManager.COMMANDS.size(); i++) {
            var module = CommandManager.COMMANDS.get(i);
            sb.append(module.getName()).append(i == CommandManager.COMMANDS.size() - 1 ? "" : ", ");
        }

        info(sb.toString());
    }

    @Override
    public boolean commandIn(String[] args) {
        Command cmd;
        if(args.length == 0) {
            infoCmds();
        } else if ((cmd = CommandManager.getCommand(args[0])) != null) {
//            Command cmd = CommandManager.getCommand(args[0]);
            cmd.info(cmd.getDesc());
//            info(CommandManager.getCommand(args[0]).getDesc());
        } else {
            infoCmds();
        }
        return true;
    }
}
