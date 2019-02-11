
/*
 * Created by Curtis Getz on 11/6/18 11:10 AM
 * Last modified 11/4/18 1:40 AM
 */

package com.curtisgetz.marsexplorer.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Object holding details for rover science and info. Includes a name, details, id, and a resource id
 * for an image
 */
public class RoverScience implements Parcelable {

    private String mName;
    private String mDetails;
    private int mId;
    private int mImageResId;

    public RoverScience(int id, String name, String details, int imageId) {
        this.mId = id;
        this.mName = name;
        this.mDetails = details;
        this.mImageResId = imageId;
    }

    private RoverScience(Parcel parcel) {
        mId = parcel.readInt();
        mDetails = parcel.readString();
        mImageResId = parcel.readInt();
        mName = parcel.readString();
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmImageResId() {
        return mImageResId;
    }

    public void setmImageResId(int mImageResId) {
        this.mImageResId = mImageResId;
    }


    public String getmDetails() {
        return mDetails;
    }

    public void setmDetails(String mDetails) {
        this.mDetails = mDetails;
    }


    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mDetails);
        parcel.writeInt(mImageResId);
        parcel.writeString(mName);
    }

    public static final Parcelable.Creator<RoverScience> CREATOR = new Parcelable.Creator<RoverScience>() {
        @Override
        public RoverScience createFromParcel(Parcel parcel) {
            return new RoverScience(parcel);
        }

        @Override
        public RoverScience[] newArray(int i) {
            return new RoverScience[i];
        }
    };
}
