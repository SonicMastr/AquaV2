package com.jaylon.aqua.events;

import com.jaylon.aqua.CommandHandler;
import com.jaylon.aqua.CommandRegister;
import com.jaylon.aqua.Settings;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReceived extends ListenerAdapter {

    private final Logger logger = LoggerFactory.getLogger(MessageReceived.class);

    private Runnable manager;
    private CommandRegister register;

    public MessageReceived(CommandRegister register) {
        this.register = register;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        /* Logging Parameters */
        User author = event.getAuthor();
        Message message = event.getMessage();
        String content = message.getContentDisplay();

        if (event.isFromType(ChannelType.TEXT)) {

            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();

            logger.debug(String.format("(%s)[%s]<%#s>: %s", guild.getName(), textChannel.getName(), author, content));

            /* Check for Command */
            if(event.getMessage().getContentRaw().equalsIgnoreCase(Settings.PREFIX + "shutdown") &&
                    event.getAuthor().getIdLong() == Settings.OWNER) {
                shutdown(event.getJDA());
                return;
            }
            if(event.getMessage().getContentRaw().startsWith(Settings.PREFIX) && !event.getMessage().isWebhookMessage() &&
                    !event.getAuthor().isBot()) {
                manager = new CommandHandler(event, register);
                new Thread(manager).start();
                logger.info("Nope");
            }
        } else if (event.isFromType(ChannelType.PRIVATE)) {
            if (event.getMessage().getContentRaw().toLowerCase().startsWith(Settings.PREFIX + "help")) {
                manager = new CommandHandler(event, register);
                new Thread(manager).start();
                logger.info("Nope");
                //manager.run(event);
            }
            logger.debug(String.format("[PRIVATE]<%#s>: %s", author, content));
        }
    }

    private void shutdown(JDA jda) {
        jda.shutdown();
        System.exit(1);
    }
}
