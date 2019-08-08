package com.jaylon.aqua.commands;

import com.jaylon.aqua.objects.BaseCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class Ping implements BaseCommand {
    @Override
    public void run(List<String> args, MessageReceivedEvent event) {
        event.getChannel().sendMessage("Pinging...").queue((message ->
                message.editMessageFormat("Ping is %sms", event.getJDA().getPing()).queue())
        );
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String[] getAliases() { return new String[]{"p", "pg"}; }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDesc() {
        return "Gives the Ping of the Websocket";
    }

    @Override
    public Boolean getOwner() {
        return (boolean) false;
    }
}
