package com.jaylon.aqua.commands.main;

import com.google.gson.Gson;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jaylon.aqua.events.MessageReceived;
import com.jaylon.aqua.objects.CommandInterface;
import com.jaylon.aqua.objects.Response;
import com.jaylon.aqua.objects.weeb.*;
import com.jaylon.aqua.utils.Request;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Anime implements CommandInterface {
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

    public Anime (EventWaiter waiter) {
        this.waiter = waiter;
    }


    @Override
    public void run(List<String> args, MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        long channelId = channel.getIdLong();
        long authorId = event.getAuthor().getIdLong();
        System.out.println(channelId);
        if (!args.isEmpty()) {
            if (args.get(0).toLowerCase().equals("-l")) {
                args.remove(0);
                if (args.isEmpty()) {
                    return;
                }
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
                    gson = new Gson();
                    WeebObject animeData = gson.fromJson(data, WeebObject.class);
                    AnimeInfo animeInfo = new AnimeInfo(animeData);
                    channel.sendMessage(AnimeListEmbed(animeInfo, event, args).build()).queue(msg -> {
                        msg.addReaction(left).queue();
                        msg.addReaction(right).queue();
                        msg.addReaction(one).queue();
                        msg.addReaction(two).queue();
                        msg.addReaction(three).queue();
                        msg.addReaction(four).queue();
                        msg.addReaction(five).queue();

                        initWaiter(msg, authorId);
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
                    WeebObject animeData = gson.fromJson(data, WeebObject.class);
                    AnimeInfo animeInfo = new AnimeInfo(animeData);
                    channel.sendMessage(AnimeEmbed(animeInfo, 0, event).build()).queue();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getName() {
        return "anime";
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
        return "main";
    }

    private void initWaiter(Message msg, long authorId) {
        waiter.waitForEvent(GuildMessageReactionAddEvent.class, (event) -> {
            MessageReaction.ReactionEmote emote = event.getReactionEmote();
            User user = event.getUser();

            return user.getIdLong() == authorId && event.getMessageIdLong() == msg.getIdLong() && !emote.isEmote() && (left.equals(emote.getName()) || right.equals(emote.getName()) || one.equals(emote.getName()) || two.equals(emote.getName()) || three.equals(emote.getName()) || four.equals(emote.getName()) || five.equals(emote.getName()));
        }, (event) -> {
            User user = event.getUser();

            msg.editMessage("No").queue();
        }, 30, TimeUnit.SECONDS,
                () -> {

            msg.delete();
        });
    }

    private String getQuery(List<String> args) {
        return "{\"query\":\" " +
                "query ($page: Int, $perPage: Int, $search: String) " +
                "{ Page (page: $page, perPage: $perPage) " +
                "{ pageInfo { total currentPage lastPage hasNextPage perPage } " +
                "media (type: ANIME, search: $search) " +
                "{ id isAdult episodes status siteUrl description(asHtml: false) " +
                "title { english romaji native } " +
                "coverImage { medium large } " +
                "nextAiringEpisode { airingAt timeUntilAiring episode } " +
                "startDate { year month day } " +
                "characters(role: MAIN) { edges { node { name { first last } } } }" +
                " } } }\"," +
                "\"variables\":" +
                "{\"search\":\"" + String.join(" ", args) + "\"," +
                "\"page\":"+ (pageNumber) +"," +
                "\"perPage\":5}}";
    }

    private EmbedBuilder AnimeListEmbed(AnimeInfo animeInfo, MessageReceivedEvent event, List<String> args) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Search Term: " + String.join(" ", args));
        embed.setAuthor("Results", null, event.getJDA().getSelfUser().getAvatarUrl());
        embed.setDescription("Navigate using the reactions");
        embed.setColor(12390624);
        embed.setFooter(String.format("Page %d/%d", animeInfo.getPageNumber(), animeInfo.getPageCount()));
        for (int i = 0; i < animeInfo.getAnimeSize(); i++) {
            String name = animeInfo.getAnime(i).getTitleEnglish();
            String value = animeInfo.getAnime(i).getTitleRomaji();
            embed.addField(String.format("%d. %s", i+1, name), value, false);
        }

        return embed;
    }

    private EmbedBuilder AnimeEmbed(AnimeInfo animeInfo, int index, MessageReceivedEvent event) {
        AnimeObject anime = animeInfo.getAnime(index);
        return new EmbedBuilder()
                .setTitle(anime.getTitleRomaji())
                .setDescription(anime.getTitleEnglish())
                .setAuthor(anime.getTitleNative(), anime.getSiteUrl(), event.getJDA().getSelfUser().getAvatarUrl())
                .setThumbnail(anime.getThumbLarge())
                .addField("Description", anime.getDescription(), false)
                .addField("Main Characters", anime.getCharacters(), true)
                .addField("Episodes", anime.getEpisodes(), true)
                .addField("Status", anime.getStatus() + anime.getNextEpisode(), true)
                .addField("Start Date", anime.getStartDate(), true)
                .setFooter("Source: AniList")
                .setColor(12390624);
    }


}
