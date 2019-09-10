package com.jaylon.aqua.commands.owner;

import com.jaylon.aqua.objects.BaseCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Ping implements BaseCommand {
    @Override
    public void run(List<String> args, MessageReceivedEvent event) {
        event.getChannel().sendMessage("Pinging...").queue((message ->
                message.editMessageFormat("Ping is %sms", event.getJDA().getGatewayPing()).queue())
        );
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String[] getAliases() { return new String[]{"p", "pg"}; }

    @Override
    public String getUsage() { return null; }

    @Override
    public String getDesc() { return "Gives the Ping of the Websocket"; }

    @Override
    public String getType() { return "owner"; }
}
