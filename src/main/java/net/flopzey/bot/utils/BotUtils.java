package net.flopzey.bot.utils;

public class BotUtils {


    public static int parseInt(String value) {

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            // todo - Logger
            return 0;
        }
    }


}
