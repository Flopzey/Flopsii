package net.flopzey.bot.commands;

import java.util.Map;

public class CommandInvoker {

    public static void invoke(CommandParser.CommandContainer container) {

        Map<String, BaseCommand> commandMap = CommandRegistry.getCommandMap();
        String command = container.getCommand();

        if (commandMap.containsKey(command)) {
            commandMap.get(command).execute(container.getArgs(), container.getEvent());
        }

    }

}
