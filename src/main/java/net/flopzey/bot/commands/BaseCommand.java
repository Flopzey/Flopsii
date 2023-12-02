package net.flopzey.bot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseCommand {

    private final Command commandInfo = this.getClass().getAnnotation(Command.class);
    protected static final Logger logger = LoggerFactory.getLogger(BaseCommand.class);

    @Deprecated
    public boolean preExecute(MessageReceivedEvent event) {
        return true;
    }

    public boolean preExecute(SlashCommandInteractionEvent event) {
        return true;
    }

    public SlashCommandData initCommand() {

        logger.debug("Initialize command " + commandInfo.alias());
        return Commands.slash(commandInfo.alias(),commandInfo.description())
                .setGuildOnly(true);
    }

    @Deprecated
    public abstract void execute(String[] args, MessageReceivedEvent event);

    public abstract void execute(SlashCommandInteractionEvent event);

    public Command getInfo() {
        return this.commandInfo;
    }

}
