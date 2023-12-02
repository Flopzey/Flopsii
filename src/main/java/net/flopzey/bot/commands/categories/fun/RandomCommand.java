package net.flopzey.bot.commands.categories.fun;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Command(
        alias = "random",
        description = "Selects a random user.",
        category = Command.Category.FUN
)
public class RandomCommand extends BaseCommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {}

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        logger.info("Command called " + getInfo().alias());
        List<Member> memberList = event.getChannel().asTextChannel().getMembers();
        List<Member> pickList = new ArrayList<>();

        for (Member m : memberList) {
            if (!m.getUser().isBot()) { pickList.add(m); }
        }

        Random rand = new Random();
        Member pick = pickList.get(rand.nextInt(pickList.size()));

        event.reply(pick.getAsMention()).queue();
    }

}
