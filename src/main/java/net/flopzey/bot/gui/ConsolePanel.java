package net.flopzey.bot.gui;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsolePanel {

    private final JDA jda;

    private JPanel mainPanel;
    private JButton button1;


    public ConsolePanel(JDA jda) {

        this.jda = jda;

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);

                JOptionPane.showMessageDialog(null, "Status changed!");



            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
