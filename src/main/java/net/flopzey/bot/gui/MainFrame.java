package net.flopzey.bot.gui;

import net.dv8tion.jda.api.JDA;

import javax.swing.*;
import java.awt.*;

public class MainFrame {

    private final JFrame frame;

    public MainFrame(JDA jda) {
        this.frame = initFrame();
        this.frame.setContentPane(new ConsolePanel(jda, frame).getMainPanel());
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    private JFrame initFrame() {
        JFrame frame = new JFrame("Flopsii Control Center");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(760, 520));
        return frame;
    }
}
