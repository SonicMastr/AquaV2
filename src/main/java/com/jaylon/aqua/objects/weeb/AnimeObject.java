package com.jaylon.aqua.objects.weeb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AnimeObject {
    private String titleEnglish, titleRomaji, titleNative, siteUrl, thumbLarge, thumbMedium, description, startDate, nextEpisode, status, characters, episodes, nextAiringEpisode;

    public AnimeObject(Medium anime) {
        this.titleEnglish = anime.getTitle().getEnglish();
        this.titleNative = anime.getTitle().getEnglish();
        this.titleRomaji = anime.getTitle().getRomaji();
        this.siteUrl = anime.getSiteUrl();
        this.thumbLarge = anime.getCoverImage().getLarge();
        this.thumbMedium = anime.getCoverImage().getMedium();
        this.episodes = Objects.requireNonNullElse(anime.getEpisodes().toString(), "None");
        this.nextAiringEpisode = anime.getNextAiringEpisode().getEpisode().toString();
        setDescription(anime.getDescription());
        setCharacters(anime.getCharacters().getEdges());
        setStatus(anime.getStatus());
        setNextEpisode(anime.getNextAiringEpisode());
        setStartDate(anime.getStartDate());
    }

    /* Setters */

    private void setDescription(String description) {

        description = description.replaceAll("<[^>]+>", "");

        if (description.length() >= 1024) {
            description = description.substring(0, 1021) + "...";
        }

        this.description = description;
    }

    private void setCharacters(List<Edge> characterEdge) {
        ArrayList<String> characters = new ArrayList<>();
        if (!characterEdge.isEmpty()) {
            for (Edge character : characterEdge) {
                String firstName = Objects.requireNonNullElse(character.getNode().getName().getFirst(), "");
                String lastName = Objects.requireNonNullElse(character.getNode().getName().getLast(), "");
                String fullName = firstName + " " + lastName;
                characters.add(fullName);
            }
            this.characters = String.join("\n", characters);
        } else {
            this.characters = "None Available";
        }
    }

    private void setStatus(String status) {
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
        this.status = status;
    }

    private void setNextEpisode(NextAiringEpisode episode) {
        if (episode.getAiringAt() != null && episode.getEpisode() != null) {
            SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
            Date date = new Date( (long) episode.getAiringAt() * 1000);
            String nextDate = format.format(date);
            String nextEpisode = Objects.requireNonNullElse(episode.getEpisode().toString(), "");
            this.nextEpisode = "\nEpisode " + nextEpisode + " Airs **" + nextDate + "**";
        } else {
            this.nextEpisode = "";
        }
    }

    private void setStartDate(StartDate startDate) {
        if (startDate.getMonth() == null && startDate.getDay() == null && startDate.getYear() == null) {
            this.startDate = "None";
        } else {
            String month = Objects.requireNonNullElse(startDate.getMonth().toString(), "");
            String day = Objects.requireNonNullElse(startDate.getDay().toString(), "");
            String year = Objects.requireNonNullElse(startDate.getYear().toString(), "");

            this.startDate = month + "/" + day + "/" + year;
        }
    }

    /* Getters */

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public String getTitleRomaji() {
        return titleRomaji;
    }

    public String getTitleNative() {
        return titleNative;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public String getThumbLarge() {
        return thumbLarge;
    }

    public String getThumbMedium() {
        return thumbMedium;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getNextEpisode() {
        return nextEpisode;
    }

    public String getStatus() {
        return status;
    }

    public String getCharacters() {
        return characters;
    }

    public String getNextAiringEpisode() {
        return nextAiringEpisode;
    }

    public String getEpisodes() {
        return episodes;
    }
}
