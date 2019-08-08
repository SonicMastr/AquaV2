package com.jaylon.aqua.events;

import com.jaylon.aqua.CommandHandler;
import com.jaylon.aqua.Settings;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReceived extends ListenerAdapter {

    private final Logger logger = LoggerFactory.getLogger(MessageReceived.class);

    private final CommandHandler manager;

    public MessageReceived(CommandHandler manager) {
        this.manager = manager;
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
                manager.handleCommand(event);
            }
        } else if (event.isFromType(ChannelType.PRIVATE)) {
            logger.debug(String.format("[PRIVATE]<%#s>: %s", author, content));
        }
    }

    private void shutdown(JDA jda) {
        jda.shutdown();
    }
}
