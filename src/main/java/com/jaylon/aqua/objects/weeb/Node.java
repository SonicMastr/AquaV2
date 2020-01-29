
package com.jaylon.aqua.objects.weeb;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Node implements Serializable
{

    @SerializedName("name")
    @Expose
    private Name name;
    private final static long serialVersionUID = 8843989295627011920L;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

}
