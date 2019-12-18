package net.flopzey.bot.commands;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandRegistry {

    private static final String COMMAND_PATH = "net.flopzey.bot.commands.categories";
    private static Map<String, BaseCommand> commandMap;

    public CommandRegistry() {

        commandMap = new HashMap<>();

        // registering commands via reflections
        Reflections reflections = new Reflections(COMMAND_PATH);
        Set<Class<? extends BaseCommand>> classes = reflections.getSubTypesOf(BaseCommand.class);
        for (Class<? extends BaseCommand> cmdClass : classes) {
            try {
                Constructor<? extends BaseCommand> constructor = cmdClass.getConstructor();
                register(constructor.newInstance());
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
                // todo - Logger
            }
        }

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
            throw new IllegalStateException("Command is already registered!");
        }
        commandMap.put(alias, command);
    }

}
