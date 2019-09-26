package net.flopzey.bot.commands.categories.general;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;

@Command(
        aliases = {"h", "help"},
        description = "",
        usage = ""
)
public class HelpCommand extends BaseCommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {

    }

}
