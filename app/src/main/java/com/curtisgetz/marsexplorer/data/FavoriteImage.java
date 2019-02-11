
/*
 * Created by Curtis Getz on 11/6/18 10:33 AM
 * Last modified 10/18/18 5:32 PM
 */

package com.curtisgetz.marsexplorer.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Object for saving user favorite images
 * Room Entity
 */

@Entity(indices = {@Index(
        value = {"mImageUrl"},
        unique = true)
})
public class FavoriteImage {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    private String mImageUrl;
    private String mDateString;
    private int mRoverIndex;

    public FavoriteImage(String imageUrl, String dateString, int roverIndex) {
        this.mImageUrl = imageUrl;
        this.mDateString = dateString;
        this.mRoverIndex = roverIndex;
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }


    public String getDateString() {
        return mDateString;
    }

    public void setDateString(String dateString) {
        this.mDateString = dateString;
    }

    public int getRoverIndex() {
        return mRoverIndex;
    }

    public void setRoverIndex(int mRoverIndex) {
        this.mRoverIndex = mRoverIndex;
    }
}
