package com.jaylon.aqua;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jaylon.aqua.commands.admin.SetPrefix;
import com.jaylon.aqua.commands.main.*;
import com.jaylon.aqua.commands.owner.*;
import com.jaylon.aqua.commands.util.*;
import com.jaylon.aqua.objects.CommandInterface;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandRegister {

    private final Logger logger = LoggerFactory.getLogger(CommandRegister.class);

    CommandRegister(EventWaiter waiter) {
        addCommand(new Ping());
        addCommand(new Help(this));
        addCommand(new Share());
        addCommand(new Update());
        addCommand(new Version());
        addCommand(new Execute());
        addCommand(new YouTubetoMP3());
        addCommand(new Anime(waiter));
        addCommand(new Manga(waiter));
        addCommand(new Characters(waiter));
        addCommand(new Eval());
        addCommand(new Stats());
        addCommand(new SetPrefix());
    }

    private final Map<String, CommandInterface> commands = new HashMap<>();
    private final Map<String, CommandInterface> aliases = new HashMap<>();

    public Map<String, CommandInterface> com() { return commands; }
    public Map<String, CommandInterface> ali() { return aliases; }

    public Collection<CommandInterface> getCommands() {
        return commands.values();
    }

    public CommandInterface getCommand(@NotNull String name) {
        return commands.get(name);
    }

    public void addCommand(CommandInterface command) {
        // Check for Command Name
        if (!commands.containsKey(command.getName())); {
            commands.put(command.getName(), command);
            logger.info(String.format("Loaded %s", command.getName()));
        }
        // Check for Aliases
        if (!aliases.containsValue(command)) {
            String[] array = command.getAliases();
            if (array == null) return;
            String alias;
            for (int a = 0; a < array.length; a++) {
                alias = array[a];
                aliases.put(alias, command);
                logger.info(String.format("Loaded Alias %s for Command %s", alias, command.getName()));
            }
        }
    }

}
