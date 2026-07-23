package net.flopzey.bot.gui;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.flopzey.bot.utils.BotUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConsolePanel {

    private final JDA jda;
    private final JFrame owner;
    private final JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
    private final JLabel connectionValue = new JLabel();
    private final JLabel accountValue = new JLabel();
    private final JLabel guildValue = new JLabel();
    private final JLabel uptimeValue = new JLabel();
    private final JLabel presenceValue = new JLabel();
    private final JTextArea logArea = new JTextArea();
    private final JCheckBox autoRefreshLogs = new JCheckBox("Automatisch aktualisieren", true);
    private final Timer refreshTimer;

    public ConsolePanel(JDA jda, JFrame owner) {
        this.jda = jda;
        this.owner = owner;

        buildUi();
        refreshDashboard();
        refreshLogs();

        refreshTimer = new Timer(2000, event -> {
            refreshDashboard();
            if (autoRefreshLogs.isSelected()) {
                refreshLogs();
            }
        });
        owner.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent event) {
                refreshTimer.stop();
            }
        });
        refreshTimer.start();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void buildUi() {
        mainPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Flopsii Control Center");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        JLabel subtitle = new JLabel("Discord-Bot überwachen und steuern");

        JPanel heading = new JPanel();
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.add(title);
        heading.add(Box.createVerticalStrut(3));
        heading.add(subtitle);
        mainPanel.add(heading, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Übersicht", createDashboardTab());
        tabs.addTab("Logs", createLogTab());
        mainPanel.add(tabs, BorderLayout.CENTER);
    }

    private JPanel createDashboardTab() {
        JPanel dashboard = new JPanel(new BorderLayout(12, 12));
        dashboard.setBorder(new EmptyBorder(14, 0, 0, 0));

        JPanel information = new JPanel(new GridLayout(0, 2, 8, 8));
        addInformationRow(information, "Verbindung", connectionValue);
        addInformationRow(information, "Bot-Account", accountValue);
        addInformationRow(information, "Server", guildValue);
        addInformationRow(information, "Laufzeit", uptimeValue);
        addInformationRow(information, "Präsenz", presenceValue);
        dashboard.add(information, BorderLayout.NORTH);

        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        controls.setBorder(BorderFactory.createTitledBorder("Bot-Steuerung"));

        JPanel statusRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<StatusOption> statusBox = new JComboBox<StatusOption>(StatusOption.values());
        JButton applyStatus = new JButton("Status übernehmen");
        applyStatus.addActionListener(event -> {
            StatusOption selected = (StatusOption) statusBox.getSelectedItem();
            if (selected != null) {
                jda.getPresence().setStatus(selected.getStatus());
                refreshDashboard();
            }
        });
        statusRow.add(new JLabel("Online-Status:"));
        statusRow.add(statusBox);
        statusRow.add(applyStatus);

        JPanel activityRow = new JPanel(new BorderLayout(8, 0));
        activityRow.setBorder(new EmptyBorder(4, 5, 4, 5));
        JTextField activityField = new JTextField(30);
        activityField.setToolTipText("Beispiel: /help");
        JButton applyActivity = new JButton("Aktivität übernehmen");
        applyActivity.addActionListener(event -> {
            String activity = activityField.getText().trim();
            if (activity.isEmpty()) {
                JOptionPane.showMessageDialog(owner, "Bitte eine Aktivität eingeben.",
                        "Eingabe fehlt", JOptionPane.WARNING_MESSAGE);
                return;
            }
            jda.getPresence().setActivity(Activity.customStatus(activity));
            refreshDashboard();
        });
        activityField.addActionListener(event -> applyActivity.doClick());
        activityRow.add(new JLabel("Aktivität:"), BorderLayout.WEST);
        activityRow.add(activityField, BorderLayout.CENTER);
        activityRow.add(applyActivity, BorderLayout.EAST);

        JPanel shutdownRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton shutdown = new JButton("Bot herunterfahren");
        shutdown.addActionListener(event -> shutdownBot());
        shutdownRow.add(shutdown);

        controls.add(statusRow);
        controls.add(activityRow);
        controls.add(Box.createVerticalGlue());
        controls.add(shutdownRow);
        dashboard.add(controls, BorderLayout.CENTER);
        return dashboard;
    }

    private JPanel createLogTab() {
        JPanel logs = new JPanel(new BorderLayout(8, 8));
        logs.setBorder(new EmptyBorder(14, 0, 0, 0));

        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        logArea.setLineWrap(false);

        JButton refresh = new JButton("Jetzt aktualisieren");
        refresh.addActionListener(event -> refreshLogs());
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(autoRefreshLogs);
        actions.add(refresh);

        logs.add(new JScrollPane(logArea), BorderLayout.CENTER);
        logs.add(actions, BorderLayout.SOUTH);
        return logs;
    }

    private void addInformationRow(JPanel panel, String name, JLabel value) {
        JLabel label = new JLabel(name + ":");
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        panel.add(label);
        panel.add(value);
    }

    private void refreshDashboard() {
        connectionValue.setText(jda.getStatus().toString());
        accountValue.setText(jda.getSelfUser().getName());
        guildValue.setText(String.valueOf(jda.getGuilds().size()));
        uptimeValue.setText(BotUtils.getUptimeFormatted());

        Activity activity = jda.getPresence().getActivity();
        String activityText = activity == null ? "keine Aktivität" : activity.getName();
        presenceValue.setText(jda.getPresence().getStatus() + " · " + activityText);
    }

    private void refreshLogs() {
        Path logFile = Paths.get("logs", "application.log");
        if (!Files.exists(logFile)) {
            logArea.setText("Noch keine Logdatei vorhanden.");
            return;
        }

        try (RandomAccessFile file = new RandomAccessFile(logFile.toFile(), "r")) {
            long start = Math.max(0, file.length() - 64 * 1024);
            file.seek(start);
            if (start > 0) {
                file.readLine();
            }

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = file.readLine()) != null) {
                content.append(new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8))
                        .append(System.lineSeparator());
            }
            logArea.setText(content.toString());
            logArea.setCaretPosition(logArea.getDocument().getLength());
        } catch (IOException exception) {
            logArea.setText("Logdatei konnte nicht gelesen werden:\n" + exception.getMessage());
        }
    }

    private void shutdownBot() {
        int choice = JOptionPane.showConfirmDialog(owner,
                "Soll der Bot wirklich heruntergefahren werden?",
                "Bot herunterfahren",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            refreshTimer.stop();
            jda.shutdown();
            owner.dispose();
        }
    }

    private enum StatusOption {
        ONLINE("Online", OnlineStatus.ONLINE),
        IDLE("Abwesend", OnlineStatus.IDLE),
        DO_NOT_DISTURB("Nicht stören", OnlineStatus.DO_NOT_DISTURB),
        INVISIBLE("Unsichtbar", OnlineStatus.INVISIBLE);

        private final String label;
        private final OnlineStatus status;

        StatusOption(String label, OnlineStatus status) {
            this.label = label;
            this.status = status;
        }

        public OnlineStatus getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
