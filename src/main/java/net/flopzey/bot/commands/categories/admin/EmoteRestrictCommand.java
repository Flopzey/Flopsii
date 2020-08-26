package net.flopzey.bot.commands.categories.admin;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.flopzey.bot.commands.BaseCommand;
import net.flopzey.bot.commands.Command;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Command(
        aliases = { "restrict", "restrictemote"},
        description = "Restrict a emote on your server to a specific role.",
        usage = "restrictemote [emote] [role1,role2,role3,...]",
        category = Command.Category.ADMIN
)
public class EmoteRestrictCommand extends BaseCommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {

        // proof of concept

        // todo parameter hinzufügen um emote und gruppe zu wählen
        // fyi wird ein nutzer zur Gruppe hinzuegügt muss wird das Emote erst nach einem Neustart/Reload(Strg+R) sichtbar

        // todo else
        if( args.length == 2 ) {

            // todo abfragen ob : vor und/oder hinter dem Emote-Namen stehen
            List<Role> roles = event.getGuild().getRoles();


            List<Emote> emotes = event.getJDA().getEmotesByName( "test", true );

            // todo für mehrere rollen erlauben
            Role role = roles.get(0);

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);


            for ( Emote emote : emotes ) {

                emote.getManager().setRoles( roleSet ).queue();

            }


        } else {

            // todo error message

        }


    }

}
