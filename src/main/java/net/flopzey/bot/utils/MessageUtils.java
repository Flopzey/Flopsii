package net.flopzey.bot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class MessageUtils {

    private static MessageEmbed getEmbedMessage(String msg, Color c) {
        return new EmbedBuilder().setDescription(msg).setColor(c).build();
    }

    public static MessageEmbed getSuccessMessage(String message) {
        return getEmbedMessage((":white_check_mark: " + message), Colors.DISCORD_GREEN);
    }

    public static MessageEmbed getWarnMessage(String message) {
        return getEmbedMessage((":warning: " + message), Colors.DISCORD_YELLOW);
    }

}
