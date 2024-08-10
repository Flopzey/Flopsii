package net.flopzey.bot.utils;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.TimeUnit;

public class BotUtils {


    public static int parseInt(String value) {

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {

            return 0;
        }
    }

    @Deprecated
    public static Color getBotColor( MessageReceivedEvent event ) {

        Color color = event.getGuild().getMemberById( event.getJDA().getSelfUser().getId() ).getColor();

        if( color == null ) {
            color = Colors.BOT_DEFAULT_COLOR;
        }

        return color;
    }

    public static Color getBotColor( SlashCommandInteractionEvent event ) {

        Color color = event.getGuild().getMemberById( event.getJDA().getSelfUser().getId() ).getColor();

        if( color == null ) {
            color = Colors.BOT_DEFAULT_COLOR;
        }

        return color;
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

    public static long getUptime(){
        return ManagementFactory.getRuntimeMXBean().getUptime();
    }

    public static String getUptimeFormatted() {

        long uptime = getUptime();
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(uptime),
                TimeUnit.MILLISECONDS.toMinutes(uptime) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(uptime) % TimeUnit.MINUTES.toSeconds(1));
    }

}
