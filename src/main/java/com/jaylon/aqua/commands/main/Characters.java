package com.jaylon.aqua.commands.main;

import com.google.gson.Gson;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jaylon.aqua.objects.CommandInterface;
import com.jaylon.aqua.objects.Response;
import com.jaylon.aqua.objects.weeb.AnimeInfo;
import com.jaylon.aqua.objects.weeb.AnimeObject;
import com.jaylon.aqua.objects.weeb.WeebObject;
import com.jaylon.aqua.objects.weebCharacters.CharacterInfo;
import com.jaylon.aqua.objects.weebCharacters.CharacterObject;
import com.jaylon.aqua.objects.weebCharacters.WeebCharacterObject;
import com.jaylon.aqua.utils.Request;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Characters implements CommandInterface {
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

    public Characters(EventWaiter waiter) {
        this.waiter = waiter;
    }


    @Override
    public void run(List<String> args, MessageReceivedEvent event) {
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
                    WeebCharacterObject characterData = gson.fromJson(data, WeebCharacterObject.class);
                    CharacterInfo characterInfo = new CharacterInfo(characterData);
                    channel.sendMessage(CharacterListEmbed(characterInfo, event, args).build()).queue(msg -> {
                        msg.addReaction(left).queue();
                        msg.addReaction(right).queue();
                        msg.addReaction(one).queue();
                        msg.addReaction(two).queue();
                        msg.addReaction(three).queue();
                        msg.addReaction(four).queue();
                        msg.addReaction(five).queue();

                        initWaiter(msg, authorId, characterInfo);
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
                    WeebCharacterObject characterData = gson.fromJson(data, WeebCharacterObject.class);
                    CharacterInfo characterInfo = new CharacterInfo(characterData);
                    channel.sendMessage(CharacterEmbed(characterInfo, 0, event).build()).queue();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getName() {
        return "character";
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

    private void initWaiter(Message msg, long authorId, CharacterInfo info) {
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
                        editCharacterList(msg, authorId);
                    } else {
                        initWaiter(msg, authorId, info);
                    }
                }
                if (right.equals(emote.getName())) {
                    if (info.getHasNextPage()) {
                        pageNumber++;
                        System.out.println(pageNumber);
                        editCharacterList(msg, authorId);
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
                msg.editMessage(CharacterEmbed(info, index, tempEvent).build()).queue();
            }
        }, 30, TimeUnit.SECONDS,
                () -> {
            msg.delete().queue();
        });
    }

    private void editCharacterList(Message msg, long authorId) {
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
            WeebCharacterObject characterData = gson.fromJson(data, WeebCharacterObject.class);
            CharacterInfo characterInfo = new CharacterInfo(characterData);
            msg.editMessage(CharacterListEmbed(characterInfo, tempEvent, tempArgs).build()).queue(message -> {
                initWaiter(message, authorId, characterInfo);
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
                "characters (search: $search) " +
                "{ name { first last} image { large } siteUrl description(asHtml: false) " +
                "media { edges { characterRole node { title { english romaji native } } } }" +
                " } } }\"," +
                "\"variables\":" +
                "{\"search\":\"" + String.join(" ", args) + "\"," +
                "\"page\":"+ (pageNumber) +"," +
                "\"perPage\":5}}";
    }

    private EmbedBuilder CharacterListEmbed(CharacterInfo characterInfo, MessageReceivedEvent event, List<String> args) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Search Term: " + String.join(" ", args));
        embed.setAuthor("Results", null, event.getJDA().getSelfUser().getAvatarUrl());
        embed.setDescription("Navigate using the reactions");
        embed.setColor(12390624);
        embed.setFooter(String.format("Page %d/%d", characterInfo.getPageNumber(), characterInfo.getPageCount()));
        for (int i = 0; i < characterInfo.getCharacterSize(); i++) {
            String name = characterInfo.getCharacter(i).getEnglish();
            String value = characterInfo.getCharacter(i).getFullName();
            embed.addField(String.format("%d. %s", i+1, name), value, false);
        }
        return embed;
    }

    private EmbedBuilder CharacterEmbed(CharacterInfo characterInfo, int index, MessageReceivedEvent event) {
        CharacterObject character = characterInfo.getCharacter(index);
        return new EmbedBuilder()
                .setTitle(character.getFullName())
                .setAuthor(character.getFullName(), character.getSiteUrl(), event.getJDA().getSelfUser().getAvatarUrl())
                .setThumbnail(character.getImageUrl())
                .addField("Description", character.getDescription(), false)
                .addField("Role", character.getRole(), true)
                .addField("Appearances", character.getTitlesRomaji(), true)
                .setFooter("Source: AniList")
                .setColor(12390624);
    }


}
