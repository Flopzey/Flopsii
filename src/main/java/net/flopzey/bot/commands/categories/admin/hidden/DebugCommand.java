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
import net.flopzey.bot.utils.BotUtils;

@Command(
        alias = "debug",
        description = "Internal Command, for Testing only! Returns Ping and Uptime.",
        category = Command.Category.HIDDEN
)
public class DebugCommand extends BaseCommand {

    @Override
    public boolean preExecute(SlashCommandInteractionEvent event) {
        return !BotConfig.isProductionMode();
    }

    @Override
    public SlashCommandData initCommand() {

        logger.debug("Initialize command " + getInfo().alias());
        return Commands.slash(getInfo().alias(),getInfo().description())
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {}

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        logger.info(event.getMember().getEffectiveName() + "Command called " + getInfo().alias() + " on server " + event.getGuild().getName() );

        event.getJDA().getRestPing().queue( (time) ->
                event.getChannel().sendMessageFormat("Ping: %d ms", time).queue()
        );

        event.reply("Uptime: " + BotUtils.getUptimeFormatted() + " h").queue();

    }

}
