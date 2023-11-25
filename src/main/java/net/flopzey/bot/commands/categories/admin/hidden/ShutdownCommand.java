package net.flopzey.bot.commands.categories.admin.hidden;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.core.BotConfig;
import net.flopzey.bot.utils.Colors;

import java.util.Timer;
import java.util.TimerTask;

@Command(
        aliases = {"shutdown"},
        category = Command.Category.HIDDEN
)
public class ShutdownCommand extends BaseCommand {

    private static final String SHUTDOWN_MESSAGE = "Bot will shutdown in 5 seconds!";

    @Override
    public boolean preExecute(MessageReceivedEvent event) {
        return BotConfig.getDevID().equals(event.getMessage().getAuthor().getId());
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {

        // delete initial message
        event.getMessage().delete().queue();

        EmbedBuilder builder = new EmbedBuilder().setColor(Colors.DISCORD_RED).setDescription(SHUTDOWN_MESSAGE);
        //Message message = event.getTextChannel().sendMessage(builder.build()).complete();
        Message message = event.getChannel().sendMessageEmbeds(builder.build()).complete();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                message.delete().queue();
                event.getJDA().shutdownNow();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    // todo - Logger
                }
                System.exit(0);
            }
        }, 5000);

    }

}
