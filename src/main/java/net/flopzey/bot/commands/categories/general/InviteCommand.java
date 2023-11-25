package net.flopzey.bot.commands.categories.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.core.BotConfig;
import net.flopzey.bot.utils.BotUtils;
import net.flopzey.bot.utils.Colors;

@Command(aliases = {"inv", "invite"},
        usage = "invite",
        description = "Receive an invite-link for this bot.",
        category = Command.Category.GENERAL
)
public class InviteCommand extends BaseCommand {


    @Override
    public void execute(String[] args, MessageReceivedEvent event) {

        //event.getTextChannel().sendMessage(
        event.getChannel().sendMessageEmbeds(
                new EmbedBuilder().setColor(BotUtils.getBotColor(event))
                        .setTitle("Get " + BotConfig.getBotName() + " on your server!")
                        .setDescription("__**[Click to invite " + BotConfig.getBotName() + " to your server.](" + BotConfig.getInviteLink() + ")**__")
                        .build()
        ).queue();

    }

}
