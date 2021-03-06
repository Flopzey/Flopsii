package net.flopzey.bot.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

    String[] aliases();

    String description() default "No description provided.";

    String usage() default "";

    Category category();

    enum Category {
        ADMIN, HIDDEN, FUN, GENERAL
    }

}

