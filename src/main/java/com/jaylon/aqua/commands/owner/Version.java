package com.jaylon.aqua.commands.owner;

import com.jaylon.aqua.objects.CommandInterface;
import com.jaylon.aqua.updater.VersionManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class Version implements CommandInterface {
    @Override
    public void run(List<String> args, MessageReceivedEvent event) throws IOException, InterruptedException {
        double ver = new VersionManager().getVersion();
        event.getChannel().sendMessage("Current Version: " + ver).queue();
    }

    @Override
    public String getName() { return "version"; }

    @Override
    public String[] getAliases() { return new String[]{"v", "ver"}; }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDesc() {
        return "Returns Bot Version";
    }

    @Override
    public String getType() {
        return "owner";
    }
}
