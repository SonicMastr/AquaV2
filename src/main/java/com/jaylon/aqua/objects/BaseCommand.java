package com.jaylon.aqua.objects;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.Map;

public interface BaseCommand {

    void run(List<String> args, MessageReceivedEvent event);
    String getName();
    String[] getAliases();
    String getUsage();
    String getDesc();
    Boolean getOwner();
}
