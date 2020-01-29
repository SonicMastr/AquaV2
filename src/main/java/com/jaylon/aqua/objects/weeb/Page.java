
package com.jaylon.aqua.objects.weeb;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page implements Serializable
{

    @SerializedName("pageInfo")
    @Expose
    private PageInfo pageInfo;
    @SerializedName("media")
    @Expose
    private List<Medium> media = null;
    private final static long serialVersionUID = 5169795935478993765L;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

}
