package net.flopzey.bot.commands.categories.admin.hidden;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.core.BotConfig;

@Command(
        alias = "debug",
        description = "Internal Command, for Testing only!",
        category = Command.Category.HIDDEN
)

/**
 * This command is only for testing and should be only used in dev-mode
 */
public class DebugCommand extends BaseCommand {

    @Override
    public boolean preExecute(SlashCommandInteractionEvent event) {
        //return !BotConfig.isProductionMode() && BotConfig.getDevID().equals(event.getMessage().getAuthor().getId());
        return true;
    }

    @Override
    public SlashCommandData initCommand() {

        return Commands.slash(getInfo().alias(),getInfo().description())
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {}

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        System.out.println("Debug");

    }

}
