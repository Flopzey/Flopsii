package net.flopzey.bot.commands.categories.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;


@Command(alias = "whois",
        description = "Get information about a user.",
        parameter = "user",
        parameterDescriptions = "Name from a user",
        category = Command.Category.GENERAL
)
public class WhoIsCommand extends BaseCommand {

    @Override
    public SlashCommandData initCommand() {

        logger.debug("Initialize command " + getInfo().alias());
        return Commands.slash(getInfo().alias(),getInfo().description())
                .setGuildOnly(true)
                .addOption(OptionType.USER, getInfo().parameter(), getInfo().parameterDescriptions(),true);
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {}

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        logger.info("Command called " + getInfo().alias());
        event.deferReply().queue();

        SlashCommandInteraction interaction = event.getInteraction();
        User user = interaction.getOption(getInfo().parameter()).getAsUser();
        Member member = interaction.getOption(getInfo().parameter()).getAsMember();

        EmbedBuilder builder = new EmbedBuilder();

        String userInfo = "Account Creation: " + user.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME)
                + "\nStatus: " + StringUtils.capitalize(member.getOnlineStatus().getKey());

        String memberInfo = "Joined Server: " + member.getTimeJoined().format(DateTimeFormatter.RFC_1123_DATE_TIME)
                + "\nNickname: " + (member.getNickname() != null ? member.getNickname() : "N/A");

//        String activityInfo = getActivity(member);

        StringJoiner roles = new StringJoiner(", ");
        for (Role role : member.getRoles()) {
            roles.add(role.getName());
        }

        builder.setColor(member.getColor())
                .setAuthor(user.isBot() ? user.getEffectiveName() + "\n(" + user.getId() + ")" :
                                user.getGlobalName() + " ("+ user.getName() +")\n(" + user.getId() + ")",
                        null, user.getEffectiveAvatarUrl())
                .setThumbnail(user.getEffectiveAvatarUrl())
                .addField("User Information", userInfo, false)
                .addField("Member Information", memberInfo, false)
//                .addField("Activities", activityInfo, false)
                .addField("Roles", roles.toString(), false)
                .setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setTimestamp(Instant.now());

        event.getHook().editOriginalEmbeds(builder.build()).queue();

    }

    private String getActivity(Member member) {

        //doesn't work due lack of privilege

        String result = "No Activities!";
        List<Activity> activities = member.getActivities();

        if (activities != null && !activities.isEmpty()) {

            StringBuilder builder = new StringBuilder();

            for (Activity activity : activities) {

                switch (activity.getType()) {
//                    case CUSTOM_STATUS: //case not tested
//                        builder.append("Status ");
//                        builder.append("**" + activity.getName() + "**");
//                        break;
                    case PLAYING:
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

