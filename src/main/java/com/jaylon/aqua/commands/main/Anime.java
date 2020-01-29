package com.jaylon.aqua.commands.main;

import com.google.gson.Gson;
import com.jaylon.aqua.objects.CommandInterface;
import com.jaylon.aqua.objects.Response;
import com.jaylon.aqua.objects.weeb.*;
import com.jaylon.aqua.utils.Request;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class Anime implements CommandInterface {
    private Request req = new Request();
    private HttpRequest request;
    private Response response;
    private String data;
    private Gson gson;

    @Override
    public void run(List<String> args, MessageReceivedEvent event) {
        if (args.get(0).equals("-l")) { args.remove(0); }
        String query = getQuery(args).replace("\n", " ").replace("  ", " ");
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
            event.getChannel().sendMessage(AnimeEmbed(animeInfo, 0, event).build()).queue();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
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
                "\"page\":1," +
                "\"perPage\":1}}";
    }

    private EmbedBuilder AnimeEmbed(AnimeInfo animeInfo, int index, MessageReceivedEvent event) {
        AnimeObject anime = animeInfo.getAnime(index);

        return new EmbedBuilder()
                .setTitle(anime.getTitleRomaji())
                .setDescription(anime.getDescription())
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
