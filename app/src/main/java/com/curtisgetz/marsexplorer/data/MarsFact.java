
/*
 * Created by Curtis Getz on 11/6/18 10:35 AM
 * Last modified 11/4/18 1:44 AM
 */

package com.curtisgetz.marsexplorer.data;


/**
 * Object for facts about Mars. Includes a short and full description of the fact and a url for the
 * source of the fact
 */
public class MarsFact {

    private int dayToShow;
    private String factName;
    private String shortDescription;
    private String fullDescription;
    private String url;

    // Suppress unused constructor warning. Class is used by RealTime DB callback to construct returned data
    @SuppressWarnings("unused")
    public MarsFact() {
    }

    @SuppressWarnings("unused")
    public MarsFact(int dayToShow, String factName, String shortDescription, String fullDescription, String url) {
        this.dayToShow = dayToShow;
        this.factName = factName;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.url = url;
    }


    public int getDayToShow() {
        return dayToShow;
    }

    public void setDayToShow(int dayToShow) {
        this.dayToShow = dayToShow;
    }

    public String getFactName() {
        return factName;
    }

    public void setFactName(String factName) {
        this.factName = factName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
