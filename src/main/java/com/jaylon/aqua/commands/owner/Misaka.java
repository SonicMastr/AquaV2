package com.jaylon.aqua.commands.owner;

import com.jaylon.aqua.objects.CommandInterface;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class Misaka implements CommandInterface {
    @Override
    public void run(List<String> args, MessageReceivedEvent event) throws IOException, InterruptedException {
        MessageChannel channel = event.getChannel();
        if (!args.isEmpty()) {
            int count = args.size();
            StringBuilder misaka = new StringBuilder();
            for (int i = 0; i < count; i++) {
                misaka.append("Misaka ");
            }
            channel.sendMessage("Misaka " + misaka.toString()).queue();

        } else {
            channel.sendMessage("Misaka ").queue();
        }
    }

    @Override
    public String getName() {
        return "misaka";
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
        return null;
    }

    @Override
    public String getType() {
        return "owner";
    }
}
