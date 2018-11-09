
/*
 * Created by Curtis Getz on 11/6/18 10:33 AM
 * Last modified 11/4/18 1:44 AM
 */

package com.curtisgetz.marsexplorer.data;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Object for main explore types. Includes an index for the explore type, a String describing the
 * explore type, and a resource id for the image
 */

@Entity(tableName = "exploretypes")
public class MainExploreType {

    @PrimaryKey
    private int mTypeIndex;
    private String mText;
    private int mImageID;

    public MainExploreType(int mTypeIndex, String mText, int mImageID) {
        this.mTypeIndex = mTypeIndex;
        this.mText = mText;
        this.mImageID = mImageID;
    }

    public int getTypeIndex() {
        return mTypeIndex;
    }

    public void setTypeIndex(int mTypeIndex) {
        this.mTypeIndex = mTypeIndex;
    }


    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public int getImageID() {
        return mImageID;
    }

    public void setImageID(int mImageID) {
        this.mImageID = mImageID;
    }
}
