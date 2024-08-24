package net.flopzey.bot.gui;

import net.dv8tion.jda.api.JDA;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.CommandRegistry;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainFrame {

    private final JFrame frame;
    private final JDA jda;

    public MainFrame(JDA jda){

        this.jda = jda;

        this.frame = initFrame();
        this.frame.setContentPane(new ConsolePanel(jda).getMainPanel());

        this.frame.setVisible(true);

        Map<String, BaseCommand> commandMap = CommandRegistry.getCommandMap();


    }

    private JFrame initFrame(){

        JFrame frame = new JFrame();
        frame.setTitle("Console");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(512, 64);

        Dimension d = new Dimension(512, 64);

        frame.setMinimumSize(d);
        frame.setMaximumSize(d);
        frame.setPreferredSize(d);

        frame.setLocationRelativeTo(null);

        return frame;
    }

}
