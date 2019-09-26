package net.flopzey.bot.commands;

import java.util.Map;

public class CommandInvoker {

    public static void invoke(CommandParser.CommandContainer container) {

        Map<String, BaseCommand> commandMap = CommandRegistry.getCommandMap();
        String command = container.getCommand();

        if (commandMap.containsKey(command)) {

            BaseCommand cmd = commandMap.get(command);
            boolean isAllowed = cmd.preExecute(container.getEvent());

            if (isAllowed) {
                cmd.execute(container.getArgs(), container.getEvent());
            }

        }

    }

}
