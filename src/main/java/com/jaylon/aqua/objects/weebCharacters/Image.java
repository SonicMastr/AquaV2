
package com.jaylon.aqua.objects.weebCharacters;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Serializable
{

    @SerializedName("large")
    @Expose
    private String large;
    private final static long serialVersionUID = 482995519165699688L;

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

}
