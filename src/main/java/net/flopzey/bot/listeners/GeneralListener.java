package net.flopzey.bot.listeners;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(GeneralListener.class);

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        logger.info("Shutdown initiated...");
        super.onShutdown(event);
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        logger.info("Joined Server " + event.getGuild().getName());
        super.onGuildJoin(event);
    }

}
