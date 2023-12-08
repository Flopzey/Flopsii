package net.flopzey.bot.commands.categories.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.utils.BotUtils;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Command(alias = "serverinfo",
        description = "Get information about the server.",
        category = Command.Category.GENERAL
)

public class ServerInfoCommand extends BaseCommand {

    @Override
    public SlashCommandData initCommand() {

        logger.debug("Initialize command " + getInfo().alias());
        return Commands.slash(getInfo().alias(),getInfo().description())
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_SEND));
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {}

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        logger.info("Command called " + getInfo().alias());
        final Guild guild = event.getGuild();
        final User owner = guild.getOwner().getUser();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(BotUtils.getBotColor(event))
                .setAuthor(guild.getName(), null, guild.getIconUrl())
                .addField("General Information", "Server Creation: " + guild.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), false)
                .addField("Owner", owner.getGlobalName() + " ("+owner.getName()+")", false)
                .addField("Channels", getChannelInfo(guild), false)
                .addField("Members", guild.getMemberCount() + " members", false)
                .addField(" Roles", guild.getRoles().size() + " roles", false)
                .setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setTimestamp(Instant.now());

        if (guild.getBoostCount() > 0) {
            builder.addField("Boost", guild.getBoostTier().name() + "\n" + guild.getBoostCount() + " boosts from " + guild.getBoosters().size() + " members", false);
        }

        if (guild.getVanityUrl() != null) {
            builder.addField("Vanity URL", guild.getVanityUrl(), false);
        }

        event.replyEmbeds(builder.build()).queue();
    }

    private String getChannelInfo(final Guild guild) {

        List<TextChannel> textChannels = guild.getTextChannels();
        List<VoiceChannel> voiceChannels = guild.getVoiceChannels();

        return new String((textChannels.size() + voiceChannels.size()) + " channels\n"
                + textChannels.size() + " text | " + voiceChannels.size() + " voice");
    }

    @Deprecated
    private String getMemberInfo(final Guild guild) {

        List<Member> memberList = guild.getMembers();

        int onlineMembers = 0;
        int botMembers = 0;

        for (Member m : memberList) {

            if (m.getOnlineStatus().equals(OnlineStatus.ONLINE)
                    || m.getOnlineStatus().equals(OnlineStatus.IDLE)
                    || m.getOnlineStatus().equals(OnlineStatus.DO_NOT_DISTURB)) {
                onlineMembers++;
            }

            if (m.getUser().isBot()) {
                botMembers++;
            }
        }

        return new String(memberList.size() + " members\n"
                + onlineMembers + " online" + " (" + botMembers + " bots)");
    }

}
