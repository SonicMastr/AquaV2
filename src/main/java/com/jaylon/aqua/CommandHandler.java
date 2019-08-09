package com.jaylon.aqua;

import com.jaylon.aqua.objects.BaseCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommandHandler {

    private CommandRegister reg = new CommandRegister();

    private final Map<String, BaseCommand> commands = reg.com();
    private final Map<String, BaseCommand> aliases = reg.ali();

    public void handleCommand(MessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(Settings.PREFIX), "").split("\\s+");
        final String comName = split[0].toLowerCase();

        keyCheck(event, split, comName, commands);
        keyCheck(event, split, comName, aliases);
    }

    private void keyCheck(MessageReceivedEvent event, String[] split, String comName, Map<String, BaseCommand> aliases) {
        if(aliases.containsKey(comName)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            try {
                aliases.get(comName).run(args, event);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}