package com.jaylon.aqua.commands.owner;

import com.jaylon.aqua.objects.CommandInterface;
import com.jaylon.aqua.utils.Memory;
import com.jaylon.aqua.utils.Uptime;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class Stats implements CommandInterface {

    @Override
    public void run(List<String> args, MessageReceivedEvent event) throws IOException, InterruptedException {
        Uptime uptime = new Uptime();

        event.getChannel().sendMessageFormat("%s%n%s", uptime.getUptime(), getMemory()).queue();
    }

    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDesc() {
        return "Gets stats of the bot";
    }

    @Override
    public String getType() {
        return "owner";
    }

    private String getMemory() {
        return Memory.getMemoryInfo().toString();
    }

    private Runtime getRuntime() {
        return Runtime.getRuntime();
    }
}
