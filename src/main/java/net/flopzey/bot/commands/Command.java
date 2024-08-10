package net.flopzey.bot.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

    String alias();

    String description() default "No description provided.";

    String parameter() default "";

    String parameterDescriptions() default "";

    Category category();

    enum Category {
        ADMIN, HIDDEN, FUN, GENERAL, MODERATION
    }

}

