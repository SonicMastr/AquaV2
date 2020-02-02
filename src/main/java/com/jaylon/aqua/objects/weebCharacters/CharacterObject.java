package com.jaylon.aqua.objects.weebCharacters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CharacterObject {
    String fullName, imageUrl, siteUrl, description, titlesEnglish, titlesNative, titlesRomaji, role, english;

    public CharacterObject(Character character) {
        this.imageUrl = character.getImage().getLarge();
        this.siteUrl = character.getSiteUrl();
        this.fullName = (Objects.requireNonNullElse(character.getName().getFirst(), " ") + " " + Objects.requireNonNullElse(character.getName().getLast(), "")).trim();
        this.english = character.getMedia().getEdges().size() > 0 ? character.getMedia().getEdges().get(0).getNode().getTitle().getRomaji() : "Misc";
        setDescription(character.getDescription());
        setTitles(character.getMedia().getEdges());
        setRole(character.getMedia().getEdges());
    }

    private void setDescription(String description) {
        if (description != null) {
            description = description.replaceAll("<[^>]+>", "");
            if (description.contains("~!") && description.contains("!~")) {
                description = description.replaceAll("~!.*!~", "");
            } else if (description.contains("~!") && !description.contains(("!~"))) {
                description = description.replaceAll("~!.*$", "");
            }

            if (description.length() >= 1024) {
                description = description.substring(0, 1021) + "...";
            }

        } else {
            description = "None";
        }

        this.description = description;
    }

    private void setTitles(List<Edge> edge) {
        ArrayList<String> titlesEng = new ArrayList<>();
        ArrayList<String> titlesNat = new ArrayList<>();
        ArrayList<String> titlesRom = new ArrayList<>();
        if (!edge.isEmpty()) {
            for (Edge edge1 : edge) {
                titlesEng.add(Objects.requireNonNullElse(edge1.getNode().getTitle().getEnglish(), ""));
                titlesNat.add(Objects.requireNonNullElse(edge1.getNode().getTitle().getNative(), ""));
                if (titlesRom.size() < 4) {
                    titlesRom.add(Objects.requireNonNullElse(edge1.getNode().getTitle().getRomaji(), edge1.getNode().getTitle().getEnglish()));
                }
            }
            this.titlesEnglish = String.join("\n", titlesEng);
            this.titlesNative = String.join("\n", titlesNat);
            this.titlesRomaji = String.join("\n", titlesRom);
        }

    }

    private void setRole(List<Edge> edge) {
        if (edge.size() > 0) {
            switch (edge.get(0).getCharacterRole()) {
                case "MAIN": this.role = "Main Character";
                    break;
                case "SUPPORTING": this.role = "Supporting Character";
                    break;
                case "BACKGROUND": this.role = "Background Character";
                    break;
                default: this.role = "N/A";
            }
        } else {
            this.role = "N/A";
        }
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getSiteUrl() {
        return this.siteUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTitlesEnglish() {
        return this.titlesEnglish;
    }

    public String getTitlesNative() {
        return this.titlesNative;
    }

    public String getTitlesRomaji() {
        return this.titlesRomaji;
    }

    public String getRole() {
        return this.role;
    }

    public String getEnglish() {
        return this.english;
    }
}
