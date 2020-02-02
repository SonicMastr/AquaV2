
package com.jaylon.aqua.objects.weebCharacters;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Node implements Serializable
{

    @SerializedName("title")
    @Expose
    private Title title;
    private final static long serialVersionUID = -2576749307994264897L;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

}
