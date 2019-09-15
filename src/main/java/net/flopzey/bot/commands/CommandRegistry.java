package net.flopzey.bot.commands;

import net.flopzey.bot.commands.placeholder.ClearCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    private static Map<String, BaseCommand> commandMap;

    public CommandRegistry() {

        commandMap = new HashMap<>();

        // commands
        register(new ClearCommand());

    }

    public static Map<String, BaseCommand> getCommandMap() {
        return commandMap;
    }

    private void register(BaseCommand command) {

        Class<? extends BaseCommand> cls = command.getClass();
        if (!cls.isAnnotationPresent(Command.class)) {
            throw new IllegalStateException("No command annotation found!");
        }

        for (String alias : command.getInfo().aliases()) {
            registerCommand(alias, command);
        }

    }

    private void registerCommand(String alias, BaseCommand command) {
        if (commandMap.containsKey(alias.toLowerCase())) {
            throw new IllegalStateException("Command is allready registerd!");
        }
        commandMap.put(alias, command);
    }

}
