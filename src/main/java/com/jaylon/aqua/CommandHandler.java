package com.jaylon.aqua;

import com.jaylon.aqua.objects.CommandInterface;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(CommandHandler.class);

    private CommandRegister reg;
    private final MessageReceivedEvent event;

    private final Map<String, CommandInterface> commands;
    private final Map<String, CommandInterface> aliases;
    private String prefix;

    public CommandHandler(MessageReceivedEvent eventParam, CommandRegister register, String prefix) {
        this.event = eventParam;
        this.reg = register;
        this.commands = this.reg.com();
        this.aliases = this.reg.ali();
        this.prefix = prefix;
    }

    @Override
    public void run() {
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(prefix), "").trim().split(" +");
        final String comName = split[0].toLowerCase();

        keyCheck(event, split, comName, commands);
        keyCheck(event, split, comName, aliases);
        logger.info("Command Thread Dead");
    }

    private void keyCheck(MessageReceivedEvent event, String[] split, String comName, Map<String, CommandInterface> aliases) {
        if(aliases.containsKey(comName)) {
            final List<String> args = new ArrayList<>(Arrays.asList(split).subList(1, split.length));
            if (aliases.get(comName).getType().equals("main")) {
                if (event.getAuthor().getIdLong() == Settings.OWNER) {
                    try {
                        logger.info("New Command " + comName + " Thread Spawned");
                        aliases.get(comName).run(args, event);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        event.getChannel().sendMessage(":x: Something went wrong, and I'm not smart enough to explain what it was.").queue();
                        logger.info("Command Thread Dead");
                    }
                } else {
                    event.getChannel().sendMessage(":x: Sorry. You're not allowed to do this").queue();
                }
            } else {
                try {
                    logger.info("New Command " + comName + " Thread Spawned");
                    aliases.get(comName).run(args, event);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    event.getChannel().sendMessage(":x: Something went wrong, and I'm not smart enough to explain what it was.").queue();
                    logger.info("Command Thread Dead");
                }
            }
        }
    }

}