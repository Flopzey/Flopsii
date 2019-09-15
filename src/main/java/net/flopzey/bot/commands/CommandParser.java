package net.flopzey.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.core.BotConfig;

import java.util.Arrays;
import java.util.List;

public class CommandParser {

    public CommandContainer parse(String rawCommand, MessageReceivedEvent event) {

        String decapitated = rawCommand.replaceFirst(BotConfig.getCommandPrefix(), "");
        String[] splitDecapitated = decapitated.split(" ");
        String command = splitDecapitated[0];

        List<String> split = Arrays.asList(splitDecapitated);
        String[] args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(args);

        return new CommandContainer(command, args, event);

    }

    public class CommandContainer {

        private final String command;
        private final String[] args;
        private final MessageReceivedEvent event;

        public CommandContainer(String command, String[] args, MessageReceivedEvent event) {
            this.command = command;
            this.args = args;
            this.event = event;
        }

        public String getCommand() {
            return this.command;
        }

        public String[] getArgs() {
            return this.args;
        }

        public MessageReceivedEvent getEvent() {
            return this.event;
        }

    }

}
