package net.flopzey.bot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class BaseCommand {

    private final Command commandInfo = this.getClass().getAnnotation(Command.class);

    @Deprecated
    public boolean preExecute(MessageReceivedEvent event) {
        return true;
    }

    public boolean preExecute(SlashCommandInteractionEvent event) {
        return true;
    }

    @Deprecated
    public abstract void execute(String[] args, MessageReceivedEvent event);

    public abstract void execute(SlashCommandInteractionEvent event);

    public Command getInfo() {
        return this.commandInfo;
    }

}
