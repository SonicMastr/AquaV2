
package com.jaylon.aqua.objects.weeb;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("isAdult")
    @Expose
    private Boolean isAdult;
    @SerializedName("episodes")
    @Expose
    private Integer episodes;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("siteUrl")
    @Expose
    private String siteUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("coverImage")
    @Expose
    private CoverImage coverImage;
    @SerializedName("nextAiringEpisode")
    @Expose
    private NextAiringEpisode nextAiringEpisode;
    @SerializedName("startDate")
    @Expose
    private StartDate startDate;
    @SerializedName("characters")
    @Expose
    private Characters characters;
    private final static long serialVersionUID = -337478971875277344L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsAdult() {
        return isAdult;
    }

    public void setIsAdult(Boolean isAdult) {
        this.isAdult = isAdult;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
    }

    public NextAiringEpisode getNextAiringEpisode() {
        return nextAiringEpisode;
    }

    public void setNextAiringEpisode(NextAiringEpisode nextAiringEpisode) {
        this.nextAiringEpisode = nextAiringEpisode;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public Characters getCharacters() {
        return characters;
    }

    public void setCharacters(Characters characters) {
        this.characters = characters;
    }

}
