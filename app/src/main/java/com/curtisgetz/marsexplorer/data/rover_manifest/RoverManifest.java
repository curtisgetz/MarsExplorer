
/*
 * Created by Curtis Getz on 11/6/18 10:19 AM
 * Last modified 10/25/18 2:32 PM
 */

package com.curtisgetz.marsexplorer.data.rover_manifest;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.curtisgetz.marsexplorer.utils.HelperUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


/**
 * Object for rover manifest details.
 * Room Entity
 */
@Entity(tableName = "roverManifest")
public class RoverManifest {

    @PrimaryKey
    private int mRoverIndex;
    private String mRoverName;
    private String mLaunchDate;
    private String mLandingDate;
    private String mStatus;
    private String mMaxSol;
    private String mMaxDate;
    private String mTotalPhotos;


    public RoverManifest(int roverIndex, String roverName, String launchDate, String landingDate,
                         String status, String maxSol, String maxDate, String totalPhotos) {

        this.mRoverIndex = roverIndex;
        this.mRoverName = roverName;
        this.mLaunchDate = launchDate;
        this.mLandingDate = landingDate;
        this.mStatus = status;
        this.mMaxSol = maxSol;
        this.mMaxDate = maxDate;
        this.mTotalPhotos = totalPhotos;
    }

    /**
     * Get the minimum Sol for the rover. Pulls from HelperUtils
     *
     * @return the minimum sol as an int
     */
    private int getMinSol() {
        switch (mRoverIndex) {
            case HelperUtils.CURIOSITY_ROVER_INDEX:
                return HelperUtils.CURIOSITY_SOL_START;
            case HelperUtils.SPIRIT_ROVER_INDEX:
                return HelperUtils.SPIRIT_SOL_START;
            case HelperUtils.INSIGHT_LANDER_INDEX:
                    return HelperUtils.INSIGHT_SOL_START;
            case HelperUtils.PERSEVERANCE_ROVER_INDEX:
                return HelperUtils.PERSEVERANCE_SOL_START;
            default:
                //if no match then use 1 as sol start to be safe
                return HelperUtils.OPPORTUNITY_SOL_START;
        }
    }


    /**
     * Get the sol range as a String for display to user
     *
     * @return sol range as a String
     */
    public String getSolRange() {
        String minSol = String.valueOf(getMinSol());
        return minSol + " - " + mMaxSol;
    }


    public int getRoverIndex() {
        return mRoverIndex;
    }

    public void setRoverIndex(int mRoverIndex) {
        this.mRoverIndex = mRoverIndex;
    }

    public String getRoverName() {
        return mRoverName;
    }

    public void setRoverName(String mRoverName) {
        this.mRoverName = mRoverName;
    }

    public String getLaunchDate() {
        return mLaunchDate;
    }

    public void setLaunchDate(String mLaunchDate) {
        this.mLaunchDate = mLaunchDate;
    }

    public String getLandingDate() {
        return mLandingDate;
    }

    public void setLandingDate(String mLandingDate) {
        this.mLandingDate = mLandingDate;
    }

    public String getStatus() {
        return mStatus;
    }


    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getMaxSol() {
        return mMaxSol;
    }

    public void setMaxSol(String mMaxSol) {
        this.mMaxSol = mMaxSol;
    }

    public String getMaxDate() {
        return mMaxDate;
    }

    public void setMaxDate(String mMaxDate) {
        this.mMaxDate = mMaxDate;
    }

    public String getTotalPhotos() {
        return mTotalPhotos;
    }

    public void setTotalPhotos(String mTotalPhotos) {
        this.mTotalPhotos = mTotalPhotos;
    }

    public int getMinSolInt() {
        return getMinSol();
    }

    /**
     * Get the maximum sol value as an int
     *
     * @return maximum sol
     */
    public int getMaxSolInt() {
        int maxSol;
        try {
            maxSol = Integer.parseInt(mMaxSol);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return HelperUtils.DEFAULT_MAX_SOL;
        }
        return maxSol;
    }


}
