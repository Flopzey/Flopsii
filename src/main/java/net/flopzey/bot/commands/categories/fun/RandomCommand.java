package net.flopzey.bot.commands.categories.fun;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Command(
        aliases = {"random", "r"},
        description = "very nice description",
        usage = "`placeholder`"
)
public class RandomCommand extends BaseCommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {

        // todo refactor

        List<Member> memberList = event.getTextChannel().getMembers();
        List<Member> pickList = new ArrayList<>();

        for (Member m : memberList) {

            if (!m.getUser().isBot()) {
                pickList.add(m);
            }

        }

        Random rand = new Random();
        Member pick = pickList.get(rand.nextInt(pickList.size()));

        String msg = pick.getAsMention();

        event.getTextChannel().sendMessage(msg).queue();


    }

}
