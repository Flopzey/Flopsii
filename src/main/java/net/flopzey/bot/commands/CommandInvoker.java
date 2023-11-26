package net.flopzey.bot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Map;

public class CommandInvoker {

    @Deprecated
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

    public static void invoke(SlashCommandInteractionEvent event) {

        Map<String, BaseCommand> commandMap = CommandRegistry.getCommandMap();
        if (commandMap.containsKey(event.getName())) {

            BaseCommand cmd = commandMap.get(event.getName());
            boolean isAllowed = cmd.preExecute(event);

            if (isAllowed) {
                cmd.execute(event);
            }

        }

    }

}
