package net.flopzey.bot.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class BotUtils {


    public static int parseInt(String value) {

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            // todo - Logger
            return 0;
        }
    }

    // todo - check if improvement needed
    public static Member getMember(MessageReceivedEvent event, String user) {

        Member member;
        user = removeMention(user);

        if (user.matches("\\d+")) {

            member = event.getGuild().getMemberById(user);

        } else {

            List<Member> list = event.getGuild().getMembersByName(user, true);

            if (list.isEmpty()) {
                list = event.getGuild().getMembersByNickname(user, true);
            }

            member = list.isEmpty() ? null : list.get(0);

        }

        return member;
    }

    private static String removeMention(String name) {

        String result;

        if (name.startsWith("<@!")) {
            result = name.substring(3, (name.length()) - 1);
        } else {
            result = name;
        }

        return result;
    }

}
