package com.jaylon.aqua.commands.main;

import com.google.gson.Gson;
import com.jaylon.aqua.objects.CommandInterface;
import com.jaylon.aqua.objects.Response;
import com.jaylon.aqua.objects.weeb.Edge;
import com.jaylon.aqua.objects.weeb.NextAiringEpisode;
import com.jaylon.aqua.objects.weeb.StartDate;
import com.jaylon.aqua.objects.weeb.WeebObject;
import com.jaylon.aqua.utils.Request;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
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
            WeebObject anime = gson.fromJson(data, WeebObject.class);
            event.getChannel().sendMessage(Embed(anime, event).build()).queue();
            //event.getChannel().sendMessage(anime.getData().getPage().getMedia().get(0).getCoverImage().getLarge()).queue();
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

    private String getDescription(String description) {

        description = description.replaceAll("<[^>]+>", "");

        if (description.length() >= 1024) {
            description = description.substring(0, 1021) + "...";
        }

        return description;
    }

    private String getCharacters(List<Edge> characterEdge) {
        ArrayList<String> characters = new ArrayList<>();
        if (!characterEdge.isEmpty()) {
            for (Edge character : characterEdge) {
                //String firstName = character.getNode().getName().getFirst();
                //String lastName = character.getNode().getName().getLast();
                String firstName = Objects.requireNonNullElse(character.getNode().getName().getFirst(), "");
                String lastName = Objects.requireNonNullElse(character.getNode().getName().getLast(), "");
                String fullName = firstName + " " + lastName;
                characters.add(fullName);
            }
            return String.join("\n", characters);
        }
        return "None Available";
    }

    private String getStatus(String status) {
        switch (status) {
            case "FINISHED": status = "**Finished**";
                break;
            case "RELEASING": status = "**Releasing**";
                break;
            case "NOT_YET_RELEASED": status = "**Not Yet Released**";
                break;
            case "CANCELLED": status = "**Cancelled**";
                break;
            default: status = "**N/A**";
                break;
        }
        return status;
    }

    private String getStartDate(StartDate startDate) {
        if (startDate.getMonth() == null && startDate.getDay() == null && startDate.getYear() == null)
            return "None";
        String month = Objects.requireNonNullElse(startDate.getMonth().toString(), "");
        String day = Objects.requireNonNullElse(startDate.getDay().toString(), "");
        String year = Objects.requireNonNullElse(startDate.getYear().toString(), "");

        return month + "/" + day + "/" + year;
    }

    private String getNextEpisode(NextAiringEpisode episode) {
        if (episode.getAiringAt() != null && episode.getEpisode() != null) {
            SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
            Date date = new Date( (long) episode.getAiringAt() * 1000);
            String nextDate = format.format(date);
            String nextEpisode = Objects.requireNonNullElse(episode.getEpisode().toString(), "");
            return "\nEpisode " + nextEpisode + " Airs **" + nextDate + "**";
        }
        return "";
    }

    private EmbedBuilder Embed(WeebObject anime, MessageReceivedEvent event) {

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(anime.getData().getPage().getMedia().get(0).getTitle().getRomaji())
                .setDescription(anime.getData().getPage().getMedia().get(0).getTitle().getEnglish())
                .setAuthor(anime.getData().getPage().getMedia().get(0).getTitle().getNative(), anime.getData().getPage().getMedia().get(0).getSiteUrl(), event.getJDA().getSelfUser().getAvatarUrl())
                .setThumbnail(anime.getData().getPage().getMedia().get(0).getCoverImage().getLarge())
                .addField("Description", getDescription(anime.getData().getPage().getMedia().get(0).getDescription()), false)
                .addField("Main Characters", getCharacters(anime.getData().getPage().getMedia().get(0).getCharacters().getEdges()), true)
                .addField("Episodes", Objects.requireNonNullElse(anime.getData().getPage().getMedia().get(0).getEpisodes().toString(), "None"), true)
                .addField("Status", getStatus(anime.getData().getPage().getMedia().get(0).getStatus()) + getNextEpisode(anime.getData().getPage().getMedia().get(0).getNextAiringEpisode()), true)
                .addField("Start Date", getStartDate(anime.getData().getPage().getMedia().get(0).getStartDate()), true)
                .setFooter("Source: AniList")
                .setColor(12390624);

        return embed;
    }


}
