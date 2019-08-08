package com.jaylon.aqua.commands;

import com.jaylon.aqua.objects.BaseCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class Update implements BaseCommand {
    @Override
    public void run(List<String> args, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String[] getAliases() {
        return null;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDesc() {
        return "Updates Bot to Newer Version if Available";
    }

    @Override
    public Boolean getOwner() {
        return null;
    }
}
