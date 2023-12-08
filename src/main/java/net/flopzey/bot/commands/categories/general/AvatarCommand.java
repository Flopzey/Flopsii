package net.flopzey.bot.commands.categories.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
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


@Command(alias = "avatar",
        description = "Display the avatar of a user.",
        parameter = "user",
        parameterDescriptions = "Name from a user",
        category = Command.Category.GENERAL
)
public class AvatarCommand extends BaseCommand {

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

        builder.setColor(member.getColor())
                .setTitle("Avatar from " + member.getEffectiveName())
                .setImage(user.getEffectiveAvatarUrl())
                .setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setTimestamp(Instant.now());

        event.getHook().editOriginalEmbeds(builder.build()).queue();

    }

}

