package net.flopzey.bot.commands.categories.admin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.utils.MessageUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Command(
        alias = "clear",
        description = "Delete messages in the current text channel.",
        parameter = "value",
        parameterDescriptions = "Number of messages to delete",
        category = Command.Category.ADMIN
)
public class ClearCommand extends BaseCommand {

    @Override
    public SlashCommandData initCommand() {
        logger.debug("Initialize command " + getInfo().alias());
        return Commands.slash(getInfo().alias(),getInfo().description())
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                .addOption(OptionType.INTEGER, getInfo().parameter(), getInfo().parameterDescriptions(),true);
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {}

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        logger.info("Command called " + getInfo().alias());

        //Enables "Bot is thinking..."
        //ephemeral = true | Only visible to command author
        event.deferReply(true).queue();

        SlashCommandInteraction interaction = event.getInteraction();
        int count = interaction.getOption(getInfo().parameter()).getAsInt();

        if (count >= 2 && count < 100){

            List<Message> messageHistory = event.getMessageChannel().getHistory().retrievePast(count).complete();
            event.getChannel().asTextChannel().deleteMessages(messageHistory).queue();

            event.getHook().editOriginalEmbeds(MessageUtils.getSuccessMessage("Deleted messages: " + count)).queue();

        } else {

            event.getHook().editOriginalEmbeds(MessageUtils.getWarnMessage("The value must be between 2 and 99!")).queue();

        }

        //Deletes message after 4 seconds.
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                event.getHook().deleteOriginal().queue();
//            }
//        }, 4000);
    }

}
