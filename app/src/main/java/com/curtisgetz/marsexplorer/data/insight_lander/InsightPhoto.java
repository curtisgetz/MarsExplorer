package com.curtisgetz.marsexplorer.data.insight_lander;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsightPhoto {


    @SerializedName("id")
    @Expose
    private Integer id;
//    @SerializedName("camera_vector")
//    @Expose
//    private Object cameraVector;
//    @SerializedName("site")
//    @Expose
//    private Object site;
//    @SerializedName("imageid")
//    @Expose
//    private String imageid;
//    @SerializedName("subframe_rect")
//    @Expose
//    private String subframeRect;
    @SerializedName("sol")
    @Expose
    private Integer sol;
   /* @SerializedName("scale_factor")
    @Expose
    private Integer scaleFactor;
    @SerializedName("camera_model_component_list")
    @Expose
    private Object cameraModelComponentList;*/
    @SerializedName("instrument")
    @Expose
    private String instrument;
    @SerializedName("url")
    @Expose
    private String url;
   /* @SerializedName("spacecraft_clock")
    @Expose
    private Double spacecraftClock;
    @SerializedName("attitude")
    @Expose
    private Object attitude;
    @SerializedName("camera_position")
    @Expose
    private Object cameraPosition;
    @SerializedName("camera_model_type")
    @Expose
    private Object cameraModelType;
    @SerializedName("drive")
    @Expose
    private Object drive;
    @SerializedName("xyz")
    @Expose
    private Object xyz;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("mission")
    @Expose
    private String mission;
    //@SerializedName("extended")
    //@Expose
   // private String extended;
    @SerializedName("date_taken")
    @Expose
    private String dateTaken;
    @SerializedName("date_received")
    @Expose
    private String dateReceived;
    @SerializedName("instrument_sort")
    @Expose
    private Integer instrumentSort;
    @SerializedName("sample_type_sort")
    @Expose
    private Integer sampleTypeSort;
    @SerializedName("is_thumbnail")
    @Expose
    private Boolean isThumbnail;

    @SerializedName("description")
    @Expose
    private String description;*/
   @SerializedName("title")
   @Expose
   private String title;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public Object getCameraVector() {
//        return cameraVector;
//    }
//
//    public void setCameraVector(Object cameraVector) {
//        this.cameraVector = cameraVector;
//    }
//
//    public Object getSite() {
//        return site;
//    }
//
//    public void setSite(Object site) {
//        this.site = site;
//    }
//
//    public String getImageid() {
//        return imageid;
//    }
//
//    public void setImageid(String imageid) {
//        this.imageid = imageid;
//    }
//
//    public String getSubframeRect() {
//        return subframeRect;
//    }
//
//    public void setSubframeRect(String subframeRect) {
//        this.subframeRect = subframeRect;
//    }

    public Integer getSol() {
        return sol;
    }

    public void setSol(Integer sol) {
        this.sol = sol;
    }

   /* public Integer getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(Integer scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public Object getCameraModelComponentList() {
        return cameraModelComponentList;
    }

    public void setCameraModelComponentList(Object cameraModelComponentList) {
        this.cameraModelComponentList = cameraModelComponentList;
    }
*/
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

  /*  public Double getSpacecraftClock() {
        return spacecraftClock;
    }

    public void setSpacecraftClock(Double spacecraftClock) {
        this.spacecraftClock = spacecraftClock;
    }

    public Object getAttitude() {
        return attitude;
    }

    public void setAttitude(Object attitude) {
        this.attitude = attitude;
    }

    public Object getCameraPosition() {
        return cameraPosition;
    }

    public void setCameraPosition(Object cameraPosition) {
        this.cameraPosition = cameraPosition;
    }

    public Object getCameraModelType() {
        return cameraModelType;
    }

    public void setCameraModelType(Object cameraModelType) {
        this.cameraModelType = cameraModelType;
    }

    public Object getDrive() {
        return drive;
    }

    public void setDrive(Object drive) {
        this.drive = drive;
    }

    public Object getXyz() {
        return xyz;
    }

    public void setXyz(Object xyz) {
        this.xyz = xyz;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

*//*    public String getExtended() {
        return extended;
    }

    public void setExtended(String extended) {
        this.extended = extended;
    }*//*

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Integer getInstrumentSort() {
        return instrumentSort;
    }

    public void setInstrumentSort(Integer instrumentSort) {
        this.instrumentSort = instrumentSort;
    }

    public Integer getSampleTypeSort() {
        return sampleTypeSort;
    }

    public void setSampleTypeSort(Integer sampleTypeSort) {
        this.sampleTypeSort = sampleTypeSort;
    }

    public Boolean getIsThumbnail() {
        return isThumbnail;
    }

    public void setIsThumbnail(Boolean isThumbnail) {
        this.isThumbnail = isThumbnail;
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

  /*  public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
*/
}
