package com.jaylon.aqua.objects.weeb;

import java.util.ArrayList;
import java.util.List;

public class AnimeInfo {

    private ArrayList<AnimeObject> anime = new ArrayList<>();
    private int pageNumber, pageCount;
    private boolean hasNextPage;

    public AnimeInfo(WeebObject anime) {
        if (!anime.getData().getPage().getMedia().isEmpty()) {
            for (Medium animeMedium : anime.getData().getPage().getMedia()) {
                this.anime.add(new AnimeObject(animeMedium));
            }
        } else {
            this.anime = null;
        }
        if (anime.getData().getPage().getPageInfo() != null) {
            this.pageCount = anime.getData().getPage().getPageInfo().getLastPage();
            this.pageNumber = anime.getData().getPage().getPageInfo().getCurrentPage();
            this.hasNextPage = anime.getData().getPage().getPageInfo().getHasNextPage();
        } else {
            this.pageCount = 0;
            this.pageNumber = 0;
            this.hasNextPage = false;
        }
    }

    public AnimeObject getAnime(int index) {
        return anime.get(index);
    }

    public int getAnimeSize() {
        return anime.size();
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public boolean getHasNextPage() {
        return hasNextPage;
    }

}
