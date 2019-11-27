package com.jaylon.aqua;

import com.jaylon.aqua.objects.CommandInterface;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommandHandler {

    private CommandRegister reg = new CommandRegister();

    private final Map<String, CommandInterface> commands = reg.com();
    private final Map<String, CommandInterface> aliases = reg.ali();

    public void handleCommand(MessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(Settings.PREFIX), "").split("\\s+");
        final String comName = split[0].toLowerCase();

        keyCheck(event, split, comName, commands);
        keyCheck(event, split, comName, aliases);
    }

    private void keyCheck(MessageReceivedEvent event, String[] split, String comName, Map<String, CommandInterface> aliases) {
        if(aliases.containsKey(comName)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            try {
                aliases.get(comName).run(args, event);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                event.getChannel().sendMessage(":x: Something went wrong, and I'm not smart enough to explain what it was.").queue();
            }
        }
    }
}