package net.flopzey.bot.commands;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandRegistry {

    private static final String COMMAND_PATH = "net.flopzey.bot.commands.categories";
    private static Map<String, BaseCommand> commandMap;
    private static final Logger logger = LoggerFactory.getLogger(CommandRegistry.class);

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
                logger.error(ex.toString());
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

        registerCommand(command.getInfo().alias(), command);
    }

    private void registerCommand(String alias, BaseCommand command) {
        if (commandMap.containsKey(alias.toLowerCase())) {
            throw new IllegalStateException("Command is already registered!");
        }
        commandMap.put(alias, command);
    }

}
