package com.jaylon.aqua.commands.main;

import com.jaylon.aqua.objects.CommandInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class Share implements CommandInterface {
    @Override
    public void run(List<String> args, MessageReceivedEvent event) {

        TextChannel channel = (TextChannel) event.getChannel();
        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You're not in a Voice Channel. Please join one to get a link").queue();
            return;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        long guildID = event.getGuild().getIdLong();
        long vcID = voiceChannel.getIdLong();
        String vcName = voiceChannel.getName();
        String guildIcon = event.getGuild().getIconUrl();
        String guildName = event.getGuild().getName();

        Embed(vcID, guildID, guildName, vcName, guildIcon, event);
    }

    @Override
    public String getName() {
        return "share";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"s"};
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDesc() {
        return "Use while in a Voice Channel to get a Screenshare Link";
    }

    @Override
    public String getType() {
        return "main";
    }

    private void Embed(long vcID, long guildID, String guildName, String vcName, String guildIcon, MessageReceivedEvent event) {

        EmbedBuilder builder = new EmbedBuilder()
                .setAuthor("Screenshare Link For Channel: " + vcName,null , event.getAuthor().getAvatarUrl())
                .setDescription("[Screenshare](https://discordapp.com/channels/" + guildID + "/" + vcID + ")")
                .setThumbnail(guildIcon)
                .setFooter(guildName, null)
                .setColor(new Color(0,255,255));


        event.getChannel().sendMessage(builder.build()).queue();
    }

}
