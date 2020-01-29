
package com.jaylon.aqua.objects.weeb;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Title implements Serializable
{

    @SerializedName("english")
    @Expose
    private String english;
    @SerializedName("romaji")
    @Expose
    private String romaji;
    @SerializedName("native")
    @Expose
    private String _native;
    private final static long serialVersionUID = 2319015072032575054L;

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    public String getNative() {
        return _native;
    }

    public void setNative(String _native) {
        this._native = _native;
    }

}
