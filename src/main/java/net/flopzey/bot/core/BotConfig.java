package net.flopzey.bot.core;

import org.ini4j.Ini;
import org.ini4j.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class BotConfig {

    private static final String SECTION_SETUP = "Setup";
    private static final String SECTION_BOT_CONFIG = "BotConfig";

    private static Profile.Section setup;
    private static Profile.Section botConfig;
    private static String mode;

    private static final Logger logger = LoggerFactory.getLogger(BotConfig.class);

    static {

        try {
            Ini ini = new Ini(new File("config.ini"));
            setup = ini.get(SECTION_SETUP);
            botConfig = ini.get(SECTION_BOT_CONFIG);

            mode = Boolean.parseBoolean( setup.get("ProductionMode") ) ? "_Prod" : "_Dev";
        } catch (IOException ex) {
            logger.error(ex.toString());
        }

    }

    protected static String getBotToken() {
        return botConfig.get("Token"+mode);
    }

    public static String getVersion() { return setup.get("Version"); }

    @Deprecated
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

    public static Boolean isProductionMode() { return Boolean.parseBoolean(setup.get("ProductionMode")); }

    public static Boolean isGuiEnabled() { return Boolean.parseBoolean(setup.get("UseGui")); }

}
