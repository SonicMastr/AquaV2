
package com.jaylon.aqua.objects.weeb;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NextAiringEpisode implements Serializable
{

    @SerializedName("airingAt")
    @Expose
    private Integer airingAt;
    @SerializedName("timeUntilAiring")
    @Expose
    private Integer timeUntilAiring;
    @SerializedName("episode")
    @Expose
    private Integer episode;
    private final static long serialVersionUID = 499366244394226245L;

    public Integer getAiringAt() {
        return airingAt;
    }

    public void setAiringAt(Integer airingAt) {
        this.airingAt = airingAt;
    }

    public Integer getTimeUntilAiring() {
        return timeUntilAiring;
    }

    public void setTimeUntilAiring(Integer timeUntilAiring) {
        this.timeUntilAiring = timeUntilAiring;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

}
