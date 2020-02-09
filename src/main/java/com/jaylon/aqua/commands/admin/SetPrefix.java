package com.jaylon.aqua.commands.admin;

import com.jaylon.aqua.Settings;
import com.jaylon.aqua.objects.CommandInterface;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class SetPrefix implements CommandInterface {
    @Override
    public void run(List<String> args, MessageReceivedEvent event) throws IOException, InterruptedException {

        Member member = event.getMember();
        MessageChannel channel = event.getChannel();

        assert member != null;
        if (!member.hasPermission(Permission.MANAGE_SERVER) && member.getIdLong() != Settings.OWNER) {
            channel.sendMessage("Only users with Manage Server Permissions can change my prefix, but my current universal prefix is `" + Settings.PREFIX + "`").queue();
            return;
        }

        if (args.isEmpty()) {
            channel.sendMessage("My current prefix is `" + Settings.PREFIXES.get(event.getGuild().getIdLong()) + "`").queue();
            return;
        }

        String newPrefix = args.get(0);
        Settings.setPrefixes(event.getGuild().getIdLong(), newPrefix);
        channel.sendMessage("Prefix is now `" + Settings.PREFIXES.get(event.getGuild().getIdLong()) + "`").queue();
    }

    @Override
    public String getName() {
        return "prefix";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String getUsage() {
        return "`" + Settings.PREFIX + getName() + " [prefix]`";
    }

    @Override
    public String getDesc() {
        return "Sets custom prefix";
    }

    @Override
    public String getType() {
        return "util";
    }
}
