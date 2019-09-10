package com.jaylon.aqua.commands.util;

import com.jaylon.aqua.CommandRegister;
import com.jaylon.aqua.Settings;
import com.jaylon.aqua.objects.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class Help implements BaseCommand {

    private final CommandRegister register;

    public Help(CommandRegister register) {
        this.register = register;
    }

    @Override
    public void run(List<String> args, MessageReceivedEvent event) {
        User author = event.getAuthor();

        if(args.isEmpty()) {
            defaultEmbed(event, author);
            return;
        }

        BaseCommand command = register.getCommand(String.join("", args));

        if(command == null) {
            event.getChannel().sendMessage("Not a Valid Command\n" +
                    "Use `" + Settings.PREFIX + getName() + "` for a list of commands").queue();
            return;
        }

        String message = "Command `" + command.getName() + "`\n" + command.getDesc();

        sendMessageString(author, message, event);


    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String[] getAliases() {
        return null;
    }

    @Override
    public String getUsage() {
        return "help ``[command]``";
    }

    @Override
    public String getDesc() {
        return "Shows List of All Commands";
    }

    @Override
    public String getType() {
        return "util";
    }

    private void defaultEmbed(MessageReceivedEvent event, User author) {

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Commands")
                .setColor(new Color(0,255,255));

        StringBuilder description = builder.getDescriptionBuilder();

        register.getCommands().forEach(
                (command) -> {
                    if (!command.getType().equals("owner")) description.append('`').append(command.getName()).append("`\n");
                }
        );

        sendMessage(author, builder.build(), event);
    }

    private void commandEmbed(MessageReceivedEvent event) {

    }

    private void sendMessage(User user, MessageEmbed content, MessageReceivedEvent event) {
        user.openPrivateChannel().queue(channel ->
                channel.sendMessage(content).queue(success -> {
                    if (!event.isFromType(ChannelType.PRIVATE)) event.getChannel().sendMessage("I've sent you a DM with my Commands! ||It's not much||").queue();
                }, error -> {
                    event.getChannel().sendMessage(content).queue();
                }));
    }
    private void sendMessageString(User user, String content, MessageReceivedEvent event) {
        user.openPrivateChannel().queue(channel ->
                channel.sendMessage(content).queue(success -> {
                    if (!event.isFromType(ChannelType.PRIVATE)) event.getChannel().sendMessage("I've sent you a DM for this Command").queue();
                }, error -> {
                    event.getChannel().sendMessage(content).queue();
                }));
    }
}
