package com.jaylon.aqua.objects;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BaseCommand {

    void run(List<String> args, MessageReceivedEvent event) throws IOException, InterruptedException;
    String getName();
    String[] getAliases();
    String getUsage();
    String getDesc();
    Boolean getOwner();
}
