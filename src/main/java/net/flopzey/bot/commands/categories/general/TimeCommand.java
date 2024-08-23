package net.flopzey.bot.commands.categories.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;
import net.flopzey.bot.utils.Colors;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


@Command(
        alias = "time",
        description = "Shows the current time in different time zones",
        category = Command.Category.GENERAL
)
public class TimeCommand  extends BaseCommand {

    private static final DateTimeZone LOSANGELES = DateTimeZone.forID( "America/Los_Angeles" );
    private static final DateTimeZone NEWYORK = DateTimeZone.forID( "America/New_York" );
    private static final DateTimeZone LONDON = DateTimeZone.forID( "Europe/London" );
    private static final DateTimeZone BERLIN = DateTimeZone.forID( "Europe/Berlin" );
    private static final DateTimeZone MOSCOW = DateTimeZone.forID( "Europe/Moscow" );
    private static final DateTimeZone SINGAPORE = DateTimeZone.forID( "Asia/Singapore" );
    private static final DateTimeZone TOKYO = DateTimeZone.forID( "Asia/Tokyo" );
    private static final DateTimeZone SYDNEY = DateTimeZone.forID( "Australia/Sydney" );
    private static final DateTimeZone AUCKLAND = DateTimeZone.forID( "Pacific/Auckland" );

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {}

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        logger.info("Command called " + getInfo().alias());
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");

        //what's the use of this command?

        DateTime now = DateTime.now( BERLIN );
        EmbedBuilder builder = new EmbedBuilder();

        String cities = "Los Angeles\n New York\n London\n Berlin\n Moscow\n Singapore\n Tokyo\n Sydney\n Auckland";
        String times = now.withZone(LOSANGELES).toString(formatter) + "\n" + now.withZone(NEWYORK).toString(formatter) + "\n" + now.withZone(LONDON).toString(formatter) + "\n"
                + now.toString(formatter) + "\n" + now.withZone(MOSCOW).toString(formatter) + "\n" + now.withZone(SINGAPORE).toString(formatter) + "\n"
                + now.withZone(TOKYO).toString(formatter) + "\n" + now.withZone(SYDNEY).toString(formatter) + "\n" + now.withZone(AUCKLAND).toString(formatter);

        builder.setColor(Colors.DISCORD_BLUE)
                .setTitle("Current Time")
                .setDescription("World Time....")
                .addField("City", cities, true)
                .addField("Time", times, true);

        event.replyEmbeds( builder.build() ).queue();

    }


}
