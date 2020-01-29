
package com.jaylon.aqua.objects.weeb;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Characters implements Serializable
{

    @SerializedName("edges")
    @Expose
    private List<Edge> edges = null;
    private final static long serialVersionUID = 8650687922059376026L;

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

}
