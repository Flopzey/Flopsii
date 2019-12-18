package net.flopzey.bot.commands.categories.admin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.utils.BotUtils;
import net.flopzey.bot.utils.MessageUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Command(
        aliases = {"clear", "c"},
        description = "Delete messages in the current text channel.",
        usage = "clear [1-99]",
        category = Command.Category.ADMIN
)
public class ClearCommand extends BaseCommand {

    @Override
    public boolean preExecute(MessageReceivedEvent event) {
        return event.getMember().hasPermission(Permission.ADMINISTRATOR);
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {

        int count = (args.length > 0) ? BotUtils.parseInt(args[0]) : 0;

        // delete initial message
        event.getMessage().delete().queue();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            // todo - Logger
        }

        MessageHistory history = event.getTextChannel().getHistory();
        List<Message> messageHistory;

        if (count > 0 && count < 100) {
            messageHistory = history.retrievePast(count).complete();

            if (messageHistory.size() == 1) {
                messageHistory.get(0).delete().queue();
            } else {
                event.getTextChannel().deleteMessages(messageHistory).queue();
            }

            Message message = event.getTextChannel().sendMessage(
                    MessageUtils.getSuccessMessage("Deleted messages: " + count))
                    .complete();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    message.delete().queue();
                }
            }, 3000);

        }

        // todo - handling of wrong usage

    }

}
