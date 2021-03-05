
/*
 * Created by Curtis Getz on 11/6/18 10:33 AM
 * Last modified 11/4/18 1:44 AM
 */

package com.curtisgetz.marsexplorer.data;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    private int mSortIndex;

    public MainExploreType(int typeIndex, String text, int imageID, int sortIndex) {
        this.mTypeIndex = typeIndex;
        this.mText = text;
        this.mImageID = imageID;
        this.mSortIndex = sortIndex;
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

    public void setSortIndex(int sortIndex){
        this.mSortIndex = sortIndex;
    }

    public int getSortIndex(){return mSortIndex;}
}
