

/*
 * Created by Curtis Getz on 11/6/18 10:16 AM
 * Last modified 11/4/18 1:36 AM
 */

package com.curtisgetz.marsexplorer.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.Tweet;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;

import java.util.List;


/**
 * DAO interface for Room Database
 */
@Dao
public interface MarsDao {

    //Main Explore Types
    @Query("SELECT * FROM exploretypes ORDER BY mSortIndex")
    LiveData<List<MainExploreType>> loadAllExploreTypes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExploreTypeList(List<MainExploreType> exploreTypes);


    // Rover Manifests
    @Query("SELECT * FROM roverManifest WHERE mRoverIndex = :roverIndex")
    LiveData<RoverManifest> loadRoverManifestByIndex(int roverIndex);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRoverManifest(RoverManifest roverManifest);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRoverManifest(RoverManifest roverManifest);


    //Favorite Photos
    @Query("SELECT * FROM favoriteimage ORDER BY mId")
    LiveData<List<FavoriteImage>> loadAllFavorites();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavoriteImage(FavoriteImage favoriteImage);

    @Delete
    void deleteFavorite(FavoriteImage favoriteImage);

    @Query("DELETE FROM favoriteimage")
    void deleteAllFavorites();

    //Tweets
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTweet(Tweet tweet);

    @Query("SELECT * FROM tweet ORDER BY mTweetId DESC")
    LiveData<List<Tweet>> loadAllTweets();

}
