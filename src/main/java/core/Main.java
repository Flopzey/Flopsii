package core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.ini4j.Ini;

import javax.security.auth.login.LoginException;
import java.io.File;

public class Main {

    public static void main(String args[]) {

        try {
            JDA jda = new JDABuilder("TOKEN").build();
        } catch (LoginException ex) {
            ex.printStackTrace();
        }

    }

}
