package com.curtisgetz.marsexplorer.data.insight_lander;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InsightResponse {
    @SerializedName("items")
    @Expose
    private List<InsightPhoto> items = null;
    @SerializedName("more")
    @Expose
    private Boolean more;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("per_page")
    @Expose
    private Integer perPage;

    public List<InsightPhoto> getItems() {
        return items;
    }

    public void setItems(List<InsightPhoto> items) {
        this.items = items;
    }

    public Boolean getMore() {
        return more;
    }

    public void setMore(Boolean more) {
        this.more = more;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

}
