package net.flopzey.bot.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

    String[] alias();

    String description() default "No description provided.";

    boolean enableOptions() default false;

    OptionType optionType() default OptionType.STRING;

    String optionParameter() default "";

    String parameterDescriptions() default "";

    Permission requiredPermission() default Permission.MESSAGE_SEND;

    Category category();

    enum Category {
        ADMIN, HIDDEN, FUN, GENERAL
    }

}

