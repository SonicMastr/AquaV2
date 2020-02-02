
package com.jaylon.aqua.objects.weebCharacters;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageInfo implements Serializable
{

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;
    @SerializedName("lastPage")
    @Expose
    private Integer lastPage;
    @SerializedName("hasNextPage")
    @Expose
    private Boolean hasNextPage;
    @SerializedName("perPage")
    @Expose
    private Integer perPage;
    private final static long serialVersionUID = -6284562211655280669L;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public Boolean getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

}
