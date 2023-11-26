package net.flopzey.bot.commands.categories.admin.hidden;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.core.BotConfig;

@Command(
        alias = {"debug"},
        description = "For testing!",
        requiredPermission = Permission.ADMINISTRATOR,
        category = Command.Category.HIDDEN
)

/**
 * This command is only for testing and should be only used in dev-mode
 */
public class DebugCommand extends BaseCommand {

    @Override
    public boolean preExecute(MessageReceivedEvent event) {
        return !BotConfig.isProductionMode() && BotConfig.getDevID().equals(event.getMessage().getAuthor().getId());
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {

        System.out.println("debug");

    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

    }

}
