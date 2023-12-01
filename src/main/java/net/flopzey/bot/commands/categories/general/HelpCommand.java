package net.flopzey.bot.commands.categories.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.commands.CommandRegistry;
import net.flopzey.bot.core.BotConfig;
import net.flopzey.bot.utils.BotUtils;
import net.flopzey.bot.utils.Colors;

import java.util.ArrayList;
import java.util.Map;

@Command(
        alias = "help",
        description = "Helps you...",
        category = Command.Category.GENERAL
)
public class HelpCommand extends BaseCommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {}

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        event.reply("Check your private messages.").setEphemeral(true).queue();
        event.getMember().getUser().openPrivateChannel().queue((channel) -> channel.sendMessageEmbeds(createHelpMessage(event)).queue());
    }

    private MessageEmbed createHelpMessage(SlashCommandInteractionEvent event) {

        Map<String, BaseCommand> commandMap = CommandRegistry.getCommandMap();
        ArrayList<BaseCommand> commandsGeneral = new ArrayList<BaseCommand>();
        ArrayList<BaseCommand> commandsFun = new ArrayList<BaseCommand>();
        ArrayList<BaseCommand> commandsAdmin = new ArrayList<BaseCommand>();
        ArrayList<BaseCommand> commandsHidden = new ArrayList<BaseCommand>();

        for(Map.Entry<String, BaseCommand> entry : commandMap.entrySet()){

            switch(entry.getValue().getInfo().category()) {
                case GENERAL:
                    commandsGeneral.add(entry.getValue());
                    break;
                case FUN:
                    commandsFun.add(entry.getValue());
                    break;
                case ADMIN:
                    commandsAdmin.add(entry.getValue());
                    break;
                case HIDDEN:
                    commandsHidden.add(entry.getValue());
                    break;
            }

        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Colors.DISCORD_BLUE)
                        .setTitle("Help!")
                        .setDescription("Command overview for **" + BotConfig.getBotName() + "** Version " + BotConfig.getVersion()
                        + "\nUse `/` to execute commands.")
                        .addField("⚙️ Category: General",buildCommandOverview(commandsGeneral),false)
                        .addField("🎉 Category: Fun",buildCommandOverview(commandsFun),false);
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR)){builder.addField("🚔 Category: Admin", buildCommandOverview(commandsAdmin),false);}
        if(BotConfig.getDevID().equals(event.getMember().getId())){builder.addField("🕵️‍♂️ Category: Hidden", buildCommandOverview(commandsHidden),false);}

        return builder.build();
    }

    private String buildCommandOverview(ArrayList<BaseCommand> commands) {

        StringBuilder builder = new StringBuilder();

        for(BaseCommand command : commands) { builder.append("`"+command.getInfo().alias()+"` - " + command.getInfo().description() + "\n"); }

        return builder.toString();
    }

}
