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
        description = "Selects a random user",
        category = Command.Category.FUN
)
public class RandomCommand extends BaseCommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {

        // proof of concept

        //List<Member> memberList = event.getTextChannel().getMembers();
        List<Member> memberList = event.getChannel().asTextChannel().getMembers();
        List<Member> pickList = new ArrayList<>();

        for (Member m : memberList) {

            if (!m.getUser().isBot()) {
                pickList.add(m);
            }

        }

        Random rand = new Random();
        Member pick = pickList.get(rand.nextInt(pickList.size()));

        String msg = pick.getAsMention();

        //event.getTextChannel().sendMessage(msg).queue();
        event.getChannel().sendMessage(msg).queue();


    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

    }

}
