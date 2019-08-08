package com.jaylon.aqua.commands;

import com.jaylon.aqua.CommandRegister;
import com.jaylon.aqua.Settings;
import com.jaylon.aqua.objects.BaseCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class Help implements BaseCommand {

    private final CommandRegister register;

    public Help(CommandRegister register) {
        this.register = register;
    }

    @Override
    public void run(List<String> args, MessageReceivedEvent event) {

        if(args.isEmpty()) {
            Embed(event);
            return;
        }

        BaseCommand command = register.getCommand(String.join("", args));

        if(command == null) {
            event.getChannel().sendMessage("Not a Valid Command\n" +
                    "Use `" + Settings.PREFIX + getName() + "` for a list of commands").queue();
            return;
        }

        String message = "Command `" + command.getName() + "`\n" + command.getDesc();

        event.getChannel().sendMessage(message).queue();


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
    public Boolean getOwner() {
        return null;
    }

    private void Embed(MessageReceivedEvent event) {

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Commands")
                .setColor(new Color(0,255,255));

        StringBuilder description = builder.getDescriptionBuilder();

        register.getCommands().forEach(
                (command) -> description.append('`').append(command.getName()).append("`\n")
        );

        event.getChannel().sendMessage(builder.build()).queue();
    }
}
