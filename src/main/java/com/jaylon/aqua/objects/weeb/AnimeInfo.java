package com.jaylon.aqua.objects.weeb;

import java.util.ArrayList;
import java.util.List;

public class AnimeInfo {

    private ArrayList<AnimeObject> anime = new ArrayList<>();

    public AnimeInfo(WeebObject anime) {
        if (!anime.getData().getPage().getMedia().isEmpty()) {
            for (Medium animeMedium : anime.getData().getPage().getMedia()) {
                this.anime.add(new AnimeObject(animeMedium));
            }
        } else {
            this.anime = null;
        }
    }

    public AnimeObject getAnime(int index) {
        return anime.get(index);
    }

}
