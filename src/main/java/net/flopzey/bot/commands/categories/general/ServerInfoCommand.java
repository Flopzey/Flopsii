package net.flopzey.bot.commands.categories.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.utils.BotUtils;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Command(aliases = {"serverinfo"},
        usage = "serverinfo",
        description = "Get information about the server.",
        category = Command.Category.GENERAL
)

public class ServerInfoCommand extends BaseCommand {

    @Override
    public boolean preExecute(MessageReceivedEvent event) {
        return event.getMember().hasPermission(Permission.VIEW_AUDIT_LOGS);
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {

        final Guild guild = event.getGuild();
        final User owner = guild.getOwner().getUser();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(BotUtils.getBotColor(event))
                .setAuthor(guild.getName(), null, guild.getIconUrl())
                .addField("General Information", "Server Creation: " + guild.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME) + "\nRegion: " + guild.getRegion().getName(), false)
                .addField("Owner", owner.getAsTag(), false)
                .addField("Channels", getChannelInfo(guild), false)
                .addField("Members", getMemberInfo(guild), false)
                .addField(" Roles", guild.getRoles().size() + " roles", false)
                .setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setTimestamp(Instant.now());

        if (guild.getBoostCount() > 0) {
            builder.addField("Boost", guild.getBoostTier().name() + "\n" + guild.getBoostCount() + " boosts from " + guild.getBoosters().size() + " members", false);
        }

        event.getTextChannel().sendMessage(builder.build()).queue();

    }

    private String getChannelInfo(final Guild guild) {

        List<TextChannel> textChannels = guild.getTextChannels();
        List<VoiceChannel> voiceChannels = guild.getVoiceChannels();

        return new String((textChannels.size() + voiceChannels.size()) + " channels\n"
                + textChannels.size() + " text | " + voiceChannels.size() + " voice");
    }

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
