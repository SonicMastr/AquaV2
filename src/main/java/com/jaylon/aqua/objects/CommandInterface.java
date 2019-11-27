package com.jaylon.aqua.objects;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.List;

public interface CommandInterface {

    void run(List<String> args, MessageReceivedEvent event) throws IOException, InterruptedException;
    String getName();
    String[] getAliases();
    String getUsage();
    String getDesc();
    String getType();
}
