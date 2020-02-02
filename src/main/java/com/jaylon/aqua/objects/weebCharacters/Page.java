
package com.jaylon.aqua.objects.weebCharacters;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page implements Serializable
{

    @SerializedName("pageInfo")
    @Expose
    private PageInfo pageInfo;
    @SerializedName("characters")
    @Expose
    private List<Character> characters = null;
    private final static long serialVersionUID = -2982109827778640325L;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

}
