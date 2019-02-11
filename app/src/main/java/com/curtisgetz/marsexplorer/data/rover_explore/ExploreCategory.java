

/*
 * Created by Curtis Getz on 11/6/18 10:17 AM
 * Last modified 11/4/18 1:33 AM
 */

package com.curtisgetz.marsexplorer.data.rover_explore;


/**
 * Object for explore categories for Rovers and Mars
 */
public class ExploreCategory {

    private int mCatIndex;
    private String mTitleText;
    private int mImageResId;
    private String mContentDescription = "";

    public ExploreCategory() {

    }

    public ExploreCategory(String mTitleText, int mImageResId, int catIndex) {
        this.mTitleText = mTitleText;
        this.mImageResId = mImageResId;
        this.mCatIndex = catIndex;
    }

    public ExploreCategory(String mTitleText, int mImageResId, int catIndex, String contentDescription) {
        this.mTitleText = mTitleText;
        this.mImageResId = mImageResId;
        this.mCatIndex = catIndex;
        this.mContentDescription = contentDescription;
    }


    public String getmTitleText() {
        return mTitleText;
    }

    public void setmTitleText(String mTitleText) {
        this.mTitleText = mTitleText;
    }

    public int getmImageResId() {
        return mImageResId;
    }

    public void setmImageResId(int mImageResId) {
        this.mImageResId = mImageResId;
    }

    public int getmCatIndex() {
        return mCatIndex;
    }

    public void setmCatIndex(int mCatIndex) {
        this.mCatIndex = mCatIndex;
    }

    public void setContentDescription(String contentDescription) {
        this.mContentDescription = contentDescription;

    }

    public String getContentDescription() {
        return mContentDescription;
    }


}
