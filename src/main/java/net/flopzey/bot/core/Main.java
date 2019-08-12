package net.flopzey.bot.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.ini4j.Ini;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String args[]) {

        try {
            Ini ini = new Ini(new File("config.ini"));

            // todo - Constants
            JDA jda = new JDABuilder(ini.get("BotConfig", "Token")).build();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (LoginException ex) {
            ex.printStackTrace();
        }

    }

}
