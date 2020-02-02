
package com.jaylon.aqua.objects.weebCharacters;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Media implements Serializable
{

    @SerializedName("edges")
    @Expose
    private List<Edge> edges = null;
    private final static long serialVersionUID = 1143728771985603861L;

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

}
