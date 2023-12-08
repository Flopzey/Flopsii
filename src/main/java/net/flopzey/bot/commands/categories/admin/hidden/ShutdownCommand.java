package net.flopzey.bot.commands.categories.admin.hidden;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.core.BotConfig;
import net.flopzey.bot.utils.Colors;

import java.util.Timer;
import java.util.TimerTask;

@Command(
        alias = "shutdown",
        description = "Shuts down the bot.",
        category = Command.Category.HIDDEN
)
public class ShutdownCommand extends BaseCommand {

    private static final String SHUTDOWN_MESSAGE = "Bot will shut down...";

    @Override
    public boolean preExecute(SlashCommandInteractionEvent event) {
        return BotConfig.getDevID().equals(event.getMember().getId());
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

        logger.info("Command called " + getInfo().alias());
        event.replyEmbeds(
                new EmbedBuilder().setColor(Colors.DISCORD_RED).setDescription(SHUTDOWN_MESSAGE).build()
        ).setEphemeral(true).queue();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                event.getHook().deleteOriginal().queue();
                event.getJDA().shutdown();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    logger.error(ex.toString());
                }
                System.exit(0);
            }
        }, 5000);
    }

}
