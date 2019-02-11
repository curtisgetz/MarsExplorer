

/*
 * Created by Curtis Getz on 11/6/18 10:12 AM
 * Last modified 11/4/18 1:37 AM
 */

package com.curtisgetz.marsexplorer.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.Tweet;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;


/**
 * Room database class
 */

@Database(entities = {MainExploreType.class, RoverManifest.class, FavoriteImage.class, Tweet.class},
        version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {


    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "marsdb";
    private static AppDataBase sInstance;

    /**
     * Get an instance of the Room Database
     *
     * @param context to build the database instance
     * @return instance of the Room database
     */
    public static AppDataBase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,
                        AppDataBase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }


    public abstract MarsDao marsDao();
}
