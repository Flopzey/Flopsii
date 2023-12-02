package net.flopzey.bot.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.flopzey.bot.commands.*;
import net.flopzey.bot.core.BotConfig;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CommandListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CommandListener.class);

    @Deprecated
    public void onMessageReceived(MessageReceivedEvent event) {

        logger.debug("MessageReceivedEvent detected");
        CommandParser parser = new CommandParser();
        Message msg = event.getMessage();

        if (!event.getAuthor().isBot()
                && msg.getContentRaw().startsWith(BotConfig.getCommandPrefix())) {
            CommandInvoker.invoke(parser.parse(event.getMessage().getContentRaw(), event));
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        logger.debug("SlashCommandInteractionEvent detected");
        CommandInvoker.invoke(event);
    }
}
