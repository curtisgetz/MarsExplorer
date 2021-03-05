

/*
 * Created by Curtis Getz on 11/6/18 10:12 AM
 * Last modified 11/4/18 1:37 AM
 */

package com.curtisgetz.marsexplorer.data.room;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;

import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.Tweet;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;


/**
 * Room database class
 */

@Database(entities = {MainExploreType.class, RoverManifest.class, FavoriteImage.class, Tweet.class},
        version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {


    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "marsdb";
    private static AppDataBase sInstance;
    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE exploretypes ADD COLUMN mSortIndex INTEGER NOT NULL DEFAULT 1");
        }
    };


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
                        AppDataBase.DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
        }
        return sInstance;
    }


    public abstract MarsDao marsDao();
}
