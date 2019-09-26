package net.flopzey.bot.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.flopzey.bot.commands.CommandInvoker;
import net.flopzey.bot.commands.CommandParser;
import net.flopzey.bot.core.BotConfig;

public class CommandListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        CommandParser parser = new CommandParser();
        Message msg = event.getMessage();

        if (!event.getAuthor().isBot()
                && msg.getContentRaw().startsWith(BotConfig.getCommandPrefix())) {
            CommandInvoker.invoke(parser.parse(event.getMessage().getContentRaw(), event));
        }

    }

}
