package net.flopzey.bot.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.flopzey.bot.commands.CommandRegistry;
import net.flopzey.bot.listeners.CommandListener;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String args[]) {

        try {

            // init command
            new CommandRegistry();

            JDA jda = JDABuilder.createDefault(BotConfig.getBotToken()).addEventListeners(new CommandListener()).build();

        } catch (LoginException ex) {
            ex.printStackTrace();
            // todo Logger
        }

    }

}
