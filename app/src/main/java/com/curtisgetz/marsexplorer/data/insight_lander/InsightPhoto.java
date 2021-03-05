package com.curtisgetz.marsexplorer.data.insight_lander;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsightPhoto {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sol")
    @Expose
    private Integer sol;
    @SerializedName("instrument")
    @Expose
    private String instrument;
    @SerializedName("url")
    @Expose
    private String url;
   @SerializedName("title")
   @Expose
   private String title;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSol() {
        return sol;
    }

    public void setSol(Integer sol) {
        this.sol = sol;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
