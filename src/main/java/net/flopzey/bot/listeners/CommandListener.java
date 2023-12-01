package net.flopzey.bot.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.flopzey.bot.commands.*;
import net.flopzey.bot.core.BotConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CommandListener extends ListenerAdapter {

    @Deprecated
    public void onMessageReceived(MessageReceivedEvent event) {

        CommandParser parser = new CommandParser();
        Message msg = event.getMessage();

        if (!event.getAuthor().isBot()
                && msg.getContentRaw().startsWith(BotConfig.getCommandPrefix())) {
            CommandInvoker.invoke(parser.parse(event.getMessage().getContentRaw(), event));
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        CommandInvoker.invoke(event);
    }
}
