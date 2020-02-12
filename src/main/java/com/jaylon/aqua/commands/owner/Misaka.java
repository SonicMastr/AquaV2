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
            if (args.get(0) != null && args.get(0).toLowerCase().contentEquals("misaka")) {
                if (args.get(1) != null && args.get(1).toLowerCase().contentEquals("misaka")) {
                    if (args.get(2) != null && args.get(2).toLowerCase().contentEquals("misaka")) {
                        if (args.get(3) != null && args.get(3).toLowerCase().contentEquals("misaka")) {
                            if (args.get(4) != null && args.get(4).toLowerCase().contentEquals("misaka")) {
                                channel.sendMessage("<@182245310024777728> You need to call the FBI").queue();
                            } else {
                                channel.sendMessage("Dude. You need to stop").queue();
                            }
                        } else {
                            channel.sendMessage("Calm down, man.").queue();
                        }
                    } else {
                        channel.sendMessage("That's a little too much Misaka").queue();
                    }
                } else {
                    channel.sendMessage("Misaka Misaka").queue();
                }
            } else {
                channel.sendMessage("Misaka").queue();
            }
        } else {
            channel.sendMessage("Misaka").queue();
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
