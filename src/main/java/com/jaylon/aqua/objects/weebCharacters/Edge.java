
package com.jaylon.aqua.objects.weebCharacters;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Edge implements Serializable
{

    @SerializedName("characterRole")
    @Expose
    private String characterRole;
    @SerializedName("node")
    @Expose
    private Node node;
    private final static long serialVersionUID = 5463235505411258111L;

    public String getCharacterRole() {
        return characterRole;
    }

    public void setCharacterRole(String characterRole) {
        this.characterRole = characterRole;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

}
