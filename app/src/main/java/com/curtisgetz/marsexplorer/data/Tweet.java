
/*
 * Created by Curtis Getz on 11/6/18 11:13 AM
 * Last modified 11/4/18 1:40 AM
 */

package com.curtisgetz.marsexplorer.data;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Object to hold details about Tweets.
 * Room Entity
 */
@Entity(tableName = "tweet")
public class Tweet implements Parcelable {

    @PrimaryKey
    private int mTweetId;
    private int mUserId;
    private String mUserName;
    private String mDate;
    private String mTweetText;
    private String mTweetPhotoUrl;

    public Tweet(int tweetId, int userId, String userName, String date, String tweetText) {
        this.mTweetId = tweetId;
        this.mUserId = userId;
        this.mUserName = userName;
        this.mDate = date;
        this.mTweetText = tweetText;
    }

    @Ignore
    public Tweet(Parcel parcel) {
        mTweetId = parcel.readInt();
        mUserId = parcel.readInt();
        mUserName = parcel.readString();
        mDate = parcel.readString();
        mTweetText = parcel.readString();
    }

    @Ignore
    public Tweet() {

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mTweetId);
        parcel.writeInt(mUserId);
        parcel.writeString(mUserName);
        parcel.writeString(mDate);
        parcel.writeString(mTweetText);
    }


    public String getTweetPhotoUrl() {
        return mTweetPhotoUrl;
    }

    public void setTweetPhotoUrl(String mTweetPhotoUrl) {
        this.mTweetPhotoUrl = mTweetPhotoUrl;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    public int getTweetId() {
        return mTweetId;
    }

    public void setTweetId(int id) {
        this.mTweetId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getTweetText() {
        return mTweetText;
    }

    public void setTweetText(String tweetText) {
        this.mTweetText = tweetText;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public final static Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel parcel) {
            return new Tweet(parcel);
        }

        @Override
        public Tweet[] newArray(int i) {
            return new Tweet[i];
        }
    };

}
