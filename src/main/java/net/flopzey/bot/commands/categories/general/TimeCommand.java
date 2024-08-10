package net.flopzey.bot.commands.categories.general;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;


@Command(
        alias = "time",
        description = "Shows the current time in different time zones",
        category = Command.Category.GENERAL
)
public class TimeCommand  extends BaseCommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {}

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        logger.info("Command called " + getInfo().alias());

        DateTime now = DateTime.now( DateTimeZone.forID( "Europe/Berlin" ) );

        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss");

        //todo - More TimeZones

        String formattedTime = now.toString(formatter);

        event.reply( formattedTime ).queue();



    }


}
