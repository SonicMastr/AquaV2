package com.jaylon.aqua.objects.weeb;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MangaObject {
    private String titleEnglish, titleRomaji, titleNative, siteUrl, thumbLarge, thumbMedium, description, startDate, status, characters, volumes;

    private boolean isAdult;

    public MangaObject(Medium medium) {
        this.titleEnglish = medium.getTitle().getEnglish();
        this.titleNative = medium.getTitle().getNative();
        this.titleRomaji = medium.getTitle().getRomaji();
        this.siteUrl = medium.getSiteUrl();
        this.thumbLarge = medium.getCoverImage().getLarge();
        this.thumbMedium = medium.getCoverImage().getMedium();
        this.volumes = Objects.requireNonNullElse(medium.getVolumes(), "None").toString();
        this.isAdult = medium.getIsAdult();
        setDescription(medium.getDescription());
        setCharacters(medium.getCharacters().getEdges());
        setStatus(medium.getStatus());
        setStartDate(medium.getStartDate());
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

    private void setStartDate(StartDate startDate) {
        if (startDate.getMonth() == null && startDate.getDay() == null && startDate.getYear() == null) {
            this.startDate = "None";
        } else {
            String month = Objects.requireNonNullElse(startDate.getMonth(), "").toString();
            String day = Objects.requireNonNullElse(startDate.getDay(), "").toString();
            String year = Objects.requireNonNullElse(startDate.getYear(), "").toString();

            this.startDate = month + "/" + day + "/" + year;
        }
    }

    /* Getters */

    public String getTitleEnglish() {
        if (titleEnglish == null)
            this.titleEnglish = this.titleRomaji;
        return titleEnglish;
    }

    public String getTitleRomaji() {
        if (titleRomaji == null)
            this.titleRomaji = this.titleEnglish;
        return titleRomaji;
    }

    public String getTitleNative() {
        if (titleNative == null)
            this.titleNative = this.titleEnglish;
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

    public String getStatus() {
        return status;
    }

    public String getVolumes() { return volumes; }

    public String getCharacters() {
        return characters;
    }

    public boolean isAdult() {
        return isAdult;
    }

}
