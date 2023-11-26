package net.flopzey.bot.commands.categories.admin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.core.BotConfig;
import net.flopzey.bot.utils.BotUtils;
import net.flopzey.bot.utils.MessageUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Command(
        alias = {"clear"},
        description = "Delete messages in the current text channel.",
        enableOptions = true,
        optionType = OptionType.INTEGER,
        optionParameter = "value",
        parameterDescriptions = "Number of messages to delete",
        requiredPermission = Permission.MESSAGE_MANAGE,
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

        // delete call message
        event.getMessage().delete().queue();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            // todo - Logger
        }

        //MessageHistory history = event.getTextChannel().getHistory();
        MessageHistory history = event.getChannel().asTextChannel().getHistory();
        List<Message> messageHistory;

        if (count > 0 && count < 100) {
            messageHistory = history.retrievePast(count).complete();

            if (messageHistory.size() == 1) {
                messageHistory.get(0).delete().queue();
            } else {
                //event.getTextChannel().deleteMessages(messageHistory).queue();
                event.getChannel().asTextChannel().deleteMessages(messageHistory).queue();
            }

            //Message message = event.getTextChannel().sendMessage(
            Message message = event.getChannel().sendMessageEmbeds(
                            MessageUtils.getSuccessMessage("Deleted messages: " + count))
                    .complete();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    message.delete().queue();
                }
            }, 3000);

        } else {

            String warnMessageText = "**Syntax Error:**\n" + BotConfig.getCommandPrefix() + getInfo().optionParameter();
            //event.getTextChannel().sendMessage(MessageUtils.getWarnMessage(warnMessageText)).queue();
            event.getChannel().sendMessageEmbeds(MessageUtils.getWarnMessage((warnMessageText))).queue();

        }

    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        System.out.println("success");

        // visable for all users
        event.reply("Test").queue();

    }

}

