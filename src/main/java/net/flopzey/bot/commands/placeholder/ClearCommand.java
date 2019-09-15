package net.flopzey.bot.commands.placeholder;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;

@Command(
        aliases = {"clear", "c"},
        description = "very nice description"
)
public class ClearCommand extends BaseCommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {

//        event.getTextChannel().sendMessage("Test").queue();

    }

}
