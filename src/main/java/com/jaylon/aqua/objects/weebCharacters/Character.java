
package com.jaylon.aqua.objects.weebCharacters;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Character implements Serializable
{

    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("siteUrl")
    @Expose
    private String siteUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("media")
    @Expose
    private Media media;
    private final static long serialVersionUID = -5903669334344957442L;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

}
