package net.flopzey.bot.commands.categories.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;

@Command(
        aliases = {"h", "help"},
        description = "",
        category = Command.Category.GENERAL
)
public class HelpCommand extends BaseCommand {

    private static final String REACTION_EMOTE = "U+1F4EC";

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {



        event.getMessage().addReaction(REACTION_EMOTE).queue();
        event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(createHelpMessage()).queue());

    }

    private MessageEmbed createHelpMessage() {

        EmbedBuilder builder = new EmbedBuilder();

        builder.setDescription("placeholder text");

        return builder.build();
    }

}
