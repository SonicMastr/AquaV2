
package com.jaylon.aqua.objects.weeb;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Edge implements Serializable
{

    @SerializedName("node")
    @Expose
    private Node node;
    private final static long serialVersionUID = -9059313914662472949L;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

}
