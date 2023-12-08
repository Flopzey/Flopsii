package net.flopzey.bot.commands.categories.admin;

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
        alias = "activity",
        description = "Changes the displayed activity.",
        parameter = "value",
        parameterDescriptions = "New activity",
        category = Command.Category.ADMIN
)
public class ActivityCommand extends BaseCommand {

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
        String newActivity = interaction.getOption(getInfo().parameter()).getAsString();
        String oldActivity = event.getJDA().getPresence().getActivity().toString();

        event.getJDA().getPresence().setActivity(Activity.customStatus(newActivity));
        logger.info("Changed displayed activity from " + oldActivity + " to " + newActivity);

        event.replyEmbeds(MessageUtils.getSuccessMessage("Activity changed!")).setEphemeral(true).queue();

    }

}
