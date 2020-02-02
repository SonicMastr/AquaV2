package com.jaylon.aqua.objects.weeb;

import java.util.ArrayList;

public class MangaInfo {

    private ArrayList<MangaObject> manga = new ArrayList<>();
    private int pageNumber, pageCount;
    private boolean hasNextPage;

    public MangaInfo(WeebObject manga) {
        if (!manga.getData().getPage().getMedia().isEmpty()) {
            for (Medium mangaMedium : manga.getData().getPage().getMedia()) {
                this.manga.add(new MangaObject(mangaMedium));
            }
        } else {
            this.manga = null;
        }
        if (manga.getData().getPage().getPageInfo() != null) {
            this.pageCount = manga.getData().getPage().getPageInfo().getLastPage();
            this.pageNumber = manga.getData().getPage().getPageInfo().getCurrentPage();
            this.hasNextPage = manga.getData().getPage().getPageInfo().getHasNextPage();
        } else {
            this.pageCount = 0;
            this.pageNumber = 0;
            this.hasNextPage = false;
        }
    }

    public MangaObject getManga(int index) {
        return manga.get(index);
    }

    public int getMangaSize() {
        return manga.size();
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
