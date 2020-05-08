package net.flopzey.bot.core;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.IOException;

public class BotConfig {

    private static final String SECTION_BOT_CONFIG = "BotConfig";

    private static Profile.Section botConfig;

    static {

        try {
            Ini ini = new Ini(new File("config.ini"));
            botConfig = ini.get(SECTION_BOT_CONFIG);
        } catch (IOException ex) {
            // todo - Logger
        }

    }

    protected static String getBotToken() {
        return botConfig.get("Token");
    }

    public static String getCommandPrefix() {
        return botConfig.get("Command_Prefix");
    }

    public static String getBotName() {
        return botConfig.get("Bot_Name");
    }

    public static String getInviteLink() {
        return botConfig.get("Invite_Link");
    }

    public static String getDevID() {
        return botConfig.get("Dev_ID");
    }

}
