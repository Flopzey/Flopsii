package net.flopzey.bot.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.flopzey.bot.commands.CommandRegistry;
import net.flopzey.bot.listeners.CommandListener;

import javax.security.auth.login.LoginException;

public class Main {

    private static final String ACTIVITY = "Hello, World. Again!";

    public static void main(String args[]) {

        // init command
        new CommandRegistry();

        JDA jda = JDABuilder.createDefault(BotConfig.getBotToken())
                .addEventListeners(new CommandListener())
                .setActivity(Activity.customStatus(ACTIVITY))
                .build();

    }

}
