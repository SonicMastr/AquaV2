package com.jaylon.aqua;

import com.jaylon.aqua.commands.main.*;
import com.jaylon.aqua.commands.owner.*;
import com.jaylon.aqua.commands.util.*;
import com.jaylon.aqua.objects.BaseCommand;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandRegister {

    private final Logger logger = LoggerFactory.getLogger(CommandRegister.class);

    CommandRegister() {
        addCommand(new Ping());
        addCommand(new Help(this));
        addCommand(new Share());
        addCommand(new Update());
        addCommand(new Version());
    }

    private final Map<String, BaseCommand> commands = new HashMap<>();
    private final Map<String, BaseCommand> aliases = new HashMap<>();

    public Map<String, BaseCommand> com() { return commands; }

    public Map<String, BaseCommand> ali() { return aliases; }

    public Collection<BaseCommand> getCommands() {
        return commands.values();
    }

    public BaseCommand getCommand(@NotNull String name) {
        return commands.get(name);
    }

    public void addCommand(BaseCommand command) {
        // Check for Command Name
        if (!commands.containsKey(command.getName())); {
            commands.put(command.getName(), command);
            logger.info(String.format("Loaded %s", command.getName()));
        }
        // Check for Aliases
        if (!aliases.containsValue(command)) {
            String[] array = command.getAliases();
            if (array == null) return;
            int a;
            String alias;
            for (a = 0; a < array.length; a++) {
                alias = array[a];
                aliases.put(alias, command);
                logger.info(String.format("Loaded Alias %s for Command %s", alias, command.getName()));
            }
        }
    }

}
