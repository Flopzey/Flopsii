package net.flopzey.bot.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.CommandRegistry;
import net.flopzey.bot.listeners.CommandListener;

import java.util.ArrayList;
import java.util.Map;


public class Main {

    private static final String ACTIVITY = "Hello, World. Again!";

    public static void main(String args[]) throws InterruptedException {

        // init commands
        new CommandRegistry();

        JDA jda = JDABuilder.createDefault(BotConfig.getBotToken())
                .addEventListeners(new CommandListener())
                .setActivity(Activity.customStatus(ACTIVITY))
                .build();
        jda.awaitReady();

        Message.suppressContentIntentWarning();
        loadCommands(jda);
    }

    private static void loadCommands(JDA jda){

        Map<String, BaseCommand> commandMap = CommandRegistry.getCommandMap();
        ArrayList<SlashCommandData> commands = new ArrayList<SlashCommandData>();

        for(Map.Entry<String, BaseCommand> entry : commandMap.entrySet()){
            commands.add(entry.getValue().initCommand());
        }

        // add commands
        jda.updateCommands()
           .addCommands(commands)
           .queue();
    }

}
