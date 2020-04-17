package com.jaylon.aqua.commands.main;

import com.google.gson.Gson;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jaylon.aqua.objects.CommandInterface;
import com.jaylon.aqua.objects.Response;
import com.jaylon.aqua.objects.weeb.*;
import com.jaylon.aqua.utils.Request;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Manga implements CommandInterface {
    private Request req = new Request();
    private HttpRequest request;
    private Response response;
    private String data;
    private Gson gson;
    private String query;
    private int pageNumber = 1;
    private EventWaiter waiter;
    private static final String left = "\u2B05";
    private static final String right = "\u27A1";
    private static final String one = "1⃣";
    private static final String two = "2⃣";
    private static final String three = "3⃣";
    private static final String four = "4⃣";
    private static final String five = "5⃣";
    private List<String> tempArgs;
    MessageReceivedEvent tempEvent;

    public Manga (EventWaiter waiter) {
        this.waiter = waiter;
    }

    @Override
    public void run(List<String> args, MessageReceivedEvent event) throws IOException, InterruptedException {
        if (pageNumber != 1)
            pageNumber = 1;
        MessageChannel channel = event.getChannel();
        tempEvent = event;
        long authorId = event.getAuthor().getIdLong();
        if (!args.isEmpty()) {
            if (args.get(0).toLowerCase().equals("-l")) {
                args.remove(0);
                if (args.isEmpty()) {
                    return;
                }
                tempArgs = args;
                query = getQuery(args).replace("\n", " ").replace("  ", " ");
                request = HttpRequest.newBuilder()
                        .uri(URI.create("https://graphql.anilist.co"))
                        .timeout(Duration.ofSeconds(10))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(query))
                        .build();
                try {
                    response = req.request(request);
                    if (response.getStatus() > 299)
                        return;
                    data = response.getResponseContent();
                    System.out.println(data);
                    gson = new Gson();
                    WeebObject mangaData = gson.fromJson(data, WeebObject.class);
                    MangaInfo mangaInfo = new MangaInfo(mangaData);
                    channel.sendMessage(MangaListEmbed(mangaInfo, event, args).build()).queue(msg -> {
                        msg.addReaction(left).queue();
                        msg.addReaction(right).queue();
                        msg.addReaction(one).queue();
                        msg.addReaction(two).queue();
                        msg.addReaction(three).queue();
                        msg.addReaction(four).queue();
                        msg.addReaction(five).queue();

                        initWaiter(msg, authorId, mangaInfo);
                    });
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                query = getQuery(args).replace("\n", " ").replace("  ", " ");
                System.out.println(query);
                request = HttpRequest.newBuilder()
                        .uri(URI.create("https://graphql.anilist.co"))
                        .timeout(Duration.ofSeconds(10))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(query))
                        .build();
                try {
                    response = req.request(request);
                    if (response.getStatus() > 299)
                        return;
                    data = response.getResponseContent();
                    gson = new Gson();
                    WeebObject mangaData = gson.fromJson(data, WeebObject.class);
                    MangaInfo mangaInfo = new MangaInfo(mangaData);
                    channel.sendMessage(MangaEmbed(mangaInfo, 0, event).build()).queue();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getName() {
        return "manga";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"m", "man"};
    }

    @Override
    public String getUsage() {
        return "manga (-l) ``[manga]``";
    }

    @Override
    public String getDesc() {
        return "Returns a Manga (or a list of Manga with -l) to view";
    }

    @Override
    public String getType() {
        return "main";
    }

    private void initWaiter(Message msg, long authorId, MangaInfo info) {
        waiter.waitForEvent(GuildMessageReactionAddEvent.class, (event) -> {
                    MessageReaction.ReactionEmote emote = event.getReactionEmote();
                    User user = event.getUser();

                    return user.getIdLong() == authorId && event.getMessageIdLong() == msg.getIdLong() && !emote.isEmote() && (left.equals(emote.getName()) || right.equals(emote.getName()) || one.equals(emote.getName()) || two.equals(emote.getName()) || three.equals(emote.getName()) || four.equals(emote.getName()) || five.equals(emote.getName()));
                }, (event) -> {

                    User user = event.getUser();
                    MessageReaction.ReactionEmote emote = event.getReactionEmote();

                    if (left.equals(emote.getName()) || right.equals(emote.getName())) {
                        if (left.equals(emote.getName())) {
                            if (pageNumber > 1) {
                                pageNumber--;
                                editMangaList(msg, authorId);
                            } else {
                                initWaiter(msg, authorId, info);
                            }
                        }
                        if (right.equals(emote.getName())) {
                            if (info.getHasNextPage()) {
                                pageNumber++;
                                System.out.println(pageNumber);
                                editMangaList(msg, authorId);
                            } else {
                                initWaiter(msg, authorId, info);
                            }
                        }
                    } else {
                        int index = 0;
                        switch (emote.getName()) {
                            case one: index = 0;
                                break;
                            case two: index = 1;
                                break;
                            case three: index = 2;
                                break;
                            case four: index = 3;
                                break;
                            case five: index = 4;
                                break;
                        }
                        msg.editMessage(MangaEmbed(info, index, tempEvent).build()).queue();
                    }
                }, 30, TimeUnit.SECONDS,
                () -> {
                    msg.delete().queue();
                });
    }

    private void editMangaList(Message msg, long authorId) {
        query = getQuery(tempArgs).replace("\n", " ").replace("  ", " ");
        request = HttpRequest.newBuilder()
                .uri(URI.create("https://graphql.anilist.co"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(query))
                .build();

        try {
            response = req.request(request);
            if (response.getStatus() > 299)
                return;
            data = response.getResponseContent();
            System.out.println(data);
            gson = new Gson();
            WeebObject mangaData = gson.fromJson(data, WeebObject.class);
            MangaInfo mangaInfo = new MangaInfo(mangaData);
            msg.editMessage(MangaListEmbed(mangaInfo, tempEvent, tempArgs).build()).queue(message -> {
                initWaiter(message, authorId, mangaInfo);
            });
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getQuery(List<String> args) {
        return "{\"query\":\" " +
                "query ($page: Int, $perPage: Int, $search: String) " +
                "{ Page (page: $page, perPage: $perPage) " +
                "{ pageInfo { total currentPage lastPage hasNextPage perPage } " +
                "media (type: MANGA, search: $search) " +
                "{ id isAdult volumes status siteUrl description(asHtml: false) " +
                "title { english romaji native } " +
                "coverImage { medium large } " +
                "startDate { year month day } " +
                "characters(role: MAIN) { edges { node { name { first last } } } }" +
                " } } }\"," +
                "\"variables\":" +
                "{\"search\":\"" + String.join(" ", args) + "\"," +
                "\"page\":"+ (pageNumber) +"," +
                "\"perPage\":5}}";
    }

    private EmbedBuilder MangaListEmbed(MangaInfo mangaInfo, MessageReceivedEvent event, List<String> args) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Search Term: " + String.join(" ", args));
        embed.setAuthor("Results", null, event.getJDA().getSelfUser().getAvatarUrl());
        embed.setDescription("Navigate using the reactions");
        embed.setColor(new Color(112, 238, 119));
        embed.setFooter(String.format("Page %d/%d", mangaInfo.getPageNumber(), mangaInfo.getPageCount()));
        for (int i = 0; i < mangaInfo.getMangaSize(); i++) {
            if (mangaInfo.getManga(i).isAdult() && !event.getTextChannel().isNSFW()) {
                String name = "NSFW";
                String value = "Please use this command in an NSFW channel to view";
                embed.addField(name, value, false);
            } else {
                String name = mangaInfo.getManga(i).getTitleEnglish();
                String value = mangaInfo.getManga(i).getTitleRomaji();
                embed.addField(String.format("%d. %s", i+1, name), value, false);
            }
        }
        return embed;
    }

    private EmbedBuilder MangaEmbed(MangaInfo mangaInfo, int index, MessageReceivedEvent event) {
        MangaObject manga = mangaInfo.getManga(index);
        if (manga.isAdult()) {
            if (event.getTextChannel().isNSFW()) {
                return new EmbedBuilder()
                        .setTitle(manga.getTitleRomaji())
                        .setDescription(manga.getTitleEnglish())
                        .setAuthor(manga.getTitleNative(), manga.getSiteUrl(), event.getJDA().getSelfUser().getAvatarUrl())
                        .setThumbnail(manga.getThumbLarge())
                        .addField("Description", manga.getDescription(), false)
                        .addField("Main Characters", manga.getCharacters(), true)
                        .addField("Volumes", manga.getVolumes(), true)
                        .addField("Status", manga.getStatus(), true)
                        .addField("Start Date", manga.getStartDate(), true)
                        .setFooter("Source: AniList")
                        .setColor(new Color(112, 238, 119));
            } else {
                return new EmbedBuilder()
                        .setDescription("Channel is not NSFW")
                        .setColor(new Color(255, 0, 0));
            }
        } else {
            return new EmbedBuilder()
                    .setTitle(manga.getTitleRomaji())
                    .setDescription(manga.getTitleEnglish())
                    .setAuthor(manga.getTitleNative(), manga.getSiteUrl(), event.getJDA().getSelfUser().getAvatarUrl())
                    .setThumbnail(manga.getThumbLarge())
                    .addField("Description", manga.getDescription(), false)
                    .addField("Main Characters", manga.getCharacters(), true)
                    .addField("Volumes", manga.getVolumes(), true)
                    .addField("Status", manga.getStatus(), true)
                    .addField("Start Date", manga.getStartDate(), true)
                    .setFooter("Source: AniList")
                    .setColor(new Color(112, 238, 119));
        }
    }
}
