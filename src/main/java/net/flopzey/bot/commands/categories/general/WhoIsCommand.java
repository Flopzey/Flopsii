package net.flopzey.bot.commands.categories.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.utils.BotUtils;
import net.flopzey.bot.utils.MessageUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;


@Command(aliases = {"who", "whois"},
        usage = "whois [name/id]",
        description = "Get information about a user.",
        category = Command.Category.GENERAL
)
public class WhoIsCommand extends BaseCommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {

        final Member member;

        // retrieve object from the member
        if (args.length == 0) {
            member = event.getMember();
        } else {
            member = BotUtils.getMember(event, String.join(" ", args));
//            member = BotUtils.getMember(event, args[0]);
        }

        EmbedBuilder builder = new EmbedBuilder();
        MessageEmbed message = null;

        if (member != null) {

            User user = member.getUser();

            String userInfo = "Account Creation: " + member.getUser().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME)
                    + "\nStatus: " + StringUtils.capitalize(member.getOnlineStatus().getKey());

            String memberInfo = "Joined Server: " + member.getTimeJoined().format(DateTimeFormatter.RFC_1123_DATE_TIME)
                    + "\nNickname: " + (member.getNickname() != null ? member.getNickname() : "N/A");

            String activityInfo = getActivity(member);

            StringJoiner roles = new StringJoiner(", ");
            for (Role role : member.getRoles()) {
                roles.add(role.getName());
            }

            builder.setColor(member.getColor())
                    .setAuthor(member.getEffectiveName() + "#" + user.getDiscriminator() + "(" + user.getId() + ")",
                            null, user.getEffectiveAvatarUrl())
                    .setThumbnail(user.getEffectiveAvatarUrl())
                    .addField("User Information", userInfo, false)
                    .addField("Member Information", memberInfo, false)
                    .addField("Activities", activityInfo, false)
                    .addField("Roles", roles.toString(), false)
                    .setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                    .setTimestamp(Instant.now());

            message = builder.build();

        } else {

            message = MessageUtils.getWarnMessage("The searched user could not be found.");

        }

        event.getTextChannel().sendMessage(message).queue();

    }

    private String getActivity(Member member) {

        String result = "No Activities!";
        List<Activity> activities = member.getActivities();

        if (activities != null && !activities.isEmpty()) {

            StringBuilder builder = new StringBuilder();

            for (Activity activity : activities) {

                switch (activity.getType()) {
                    case DEFAULT:
                        builder.append("Playing ");
                        builder.append("**" + activity.getName() + "**");
                        break;
                    case LISTENING:
                        builder.append("Listening to ");
                        builder.append("**" + activity.getName() + "**");
                        break;
                    case STREAMING:
                        builder.append("Streaming ");
                        builder.append("**" + activity.getName() + "**");
                        break;
                    case WATCHING:
                        builder.append("Watching ");
                        builder.append("**" + activity.getName() + "**");
                        break;
                }

                builder.append("\n");
            }

            result = builder.toString();
        }

        return result;

    }

}
