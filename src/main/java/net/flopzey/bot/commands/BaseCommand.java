package net.flopzey.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class BaseCommand {

    private final Command commandInfo = this.getClass().getAnnotation(Command.class);

    public boolean preExecute(MessageReceivedEvent event) {
        return true;
    }

    public abstract void execute(String[] args, MessageReceivedEvent event);

    public Command getInfo() {
        return this.commandInfo;
    }

}
