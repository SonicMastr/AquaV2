package com.jaylon.aqua.objects.weebCharacters;

import java.util.ArrayList;

public class CharacterInfo {
    private ArrayList<CharacterObject> character = new ArrayList<>();
    private int pageNumber, pageCount;
    private boolean hasNextPage;

    public CharacterInfo(WeebCharacterObject character) {
        if (!character.getData().getPage().getCharacters().isEmpty()) {
            for (Character characters : character.getData().getPage().getCharacters()) {
                this.character.add(new CharacterObject(characters));
            }
        } else {
            this.character = null;
        }
        if (character.getData().getPage().getPageInfo() != null) {
            this.pageCount = character.getData().getPage().getPageInfo().getLastPage();
            this.pageNumber = character.getData().getPage().getPageInfo().getCurrentPage();
            this.hasNextPage = character.getData().getPage().getPageInfo().getHasNextPage();
        } else {
            this.pageCount = 0;
            this.pageNumber = 0;
            this.hasNextPage = false;
        }
    }

    public int getCharacterSize() {
        return this.character.size();
    }

    public CharacterObject getCharacter(int index) {
        return this.character.get(index);
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
