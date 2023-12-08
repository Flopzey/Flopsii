package net.flopzey.bot.commands.categories.admin;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
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

@Command(
        alias = "onlinestatus",
        description = "Changes the displayed onlinestatus.",
        parameter = "value",
        parameterDescriptions = "New onlinestatus",
        category = Command.Category.ADMIN
)
public class OnlineStatusCommand extends BaseCommand {

    @Override
    public SlashCommandData initCommand() {

        logger.debug("Initialize command " + getInfo().alias());
        return Commands.slash(getInfo().alias(),getInfo().description())
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                .addOption(OptionType.STRING, getInfo().parameter(), getInfo().parameterDescriptions(),true);
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {}

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        logger.info("Command called " + getInfo().alias());

        SlashCommandInteraction interaction = event.getInteraction();
        String userInput = interaction.getOption(getInfo().parameter()).getAsString().toLowerCase();
        String oldStatus = event.getJDA().getPresence().getStatus().toString();

        OnlineStatus newStatus = null;

        switch (userInput){
            case "online":
                newStatus = OnlineStatus.ONLINE;
                break;
            case "idle":
                newStatus = OnlineStatus.IDLE;
                break;
            case "dnd":
                newStatus = OnlineStatus.DO_NOT_DISTURB;
                break;
            case "invisible":
                newStatus = OnlineStatus.INVISIBLE;
                break;
        }

        if (newStatus != null) {

            event.getJDA().getPresence().setStatus(newStatus);
            logger.info("Changed onlinestatus from " + oldStatus + " to " + newStatus.toString());
            event.replyEmbeds(MessageUtils.getSuccessMessage("Online status changed!")).setEphemeral(true).queue();

        } else {

            event.replyEmbeds(MessageUtils.getWarnMessage("Invalid onlinestatus!\nChoose between `online`, `idle`, `dnd` or `invisible`")).setEphemeral(true).queue();

        }

    }

}
