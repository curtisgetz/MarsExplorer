
/*
 * Created by Curtis Getz on 11/6/18 10:37 AM
 * Last modified 11/4/18 1:40 AM
 */

package com.curtisgetz.marsexplorer.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.curtisgetz.marsexplorer.data.room.AppDataBase;
import com.curtisgetz.marsexplorer.data.room.MarsDao;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;
import com.curtisgetz.marsexplorer.utils.AppExecutors;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.JsonUtils;
import com.curtisgetz.marsexplorer.utils.NetworkUtils;

import java.net.URL;
import java.util.List;


/**
 * Repository class for exposing data source to the rest of the application
 */

public class MarsRepository {

    private MarsDao mMarsDao;
    private static MarsRepository sInstance;


    /**
     * Get an instance of MarsRepository
     * @param application for creating instance
     * @return instance of repository
     */
    public static MarsRepository getInstance(Application application){
        if(sInstance == null){
            sInstance = new MarsRepository(application);
        }
        return sInstance;
    }

    private MarsRepository(Application application){
        AppDataBase dataBase = AppDataBase.getInstance(application);
        mMarsDao = dataBase.marsDao();
    }

    /**
     * Get a RoverManifest object from DAO
     * @param index rover index needed to select correct manifest from DAO
     * @return LiveData wrapped Rover Manifest
     */
    public LiveData<RoverManifest> getRoverManifest(int index){
        return mMarsDao.loadRoverManifestByIndex(index);
    }

    /**
     * Save RoverManifest into DAO
     * @param roverManifest object to save
     */
    public void insertManifest(final RoverManifest roverManifest){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.insertRoverManifest(roverManifest);
            }
        });
    }

    /**
     * Download manifests from NASA.gov API and update DAO
     * @param context needed to build Url
     */
    public void downloadManifestsFromNetwork(final Context context){
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                for(int roverIndex : HelperUtils.ROVER_INDICES) {
                    try {
                        URL manifestUrl = NetworkUtils.buildManifestUrl(context, roverIndex);
                        String jsonResponse = NetworkUtils.getResponseFromHttpUrl(manifestUrl);
                        RoverManifest manifest = JsonUtils.getRoverManifest(roverIndex, jsonResponse);
                        mMarsDao.insertRoverManifest(manifest);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        });
    }

    /**
     * Get Cameras object from NASA.gov API query for specified rover and sol
     * @param context needed to build url and parse Json
     * @param roverIndex needed for API query
     * @param sol needed for API query
     * @return LiveData wrapped Cameras object
     */
    public LiveData<Cameras> getCameras(final Context context, final int roverIndex, final String sol){
        final MutableLiveData<Cameras> cameras = new MutableLiveData<>();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL solRequestUrl = NetworkUtils.buildPhotosUrl(context, roverIndex, sol);
                    String jsonResponse = NetworkUtils.getResponseFromHttpUrl(context, solRequestUrl);
                    cameras.postValue(JsonUtils.getCameraUrls(roverIndex, jsonResponse));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return cameras;
    }

    /**
     * Get latest Mars weather available
     * @param context needed to build Url
     * @return LiveData wrapped List of WeatherDetail objects
     */
    public LiveData<List<WeatherDetail>> getLatestWeather(final Context context){
        final MutableLiveData<List<WeatherDetail>> weatherDetail = new MutableLiveData<>();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try{
                    URL weatherUrl = NetworkUtils.buildWeatherUrl();
                    String jsonResponse = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
                    weatherDetail.postValue(JsonUtils.getWeatherDetail(context, jsonResponse));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return weatherDetail;
    }

    /**
     * Save List of MainExploreType to DAO
     * @param exploreTypes List of MainExploreType objects
     */
    public void addExploreTypesToDB(final List<MainExploreType> exploreTypes){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.insertExploreTypeList(exploreTypes);
            }
        });
    }

    /**
     * Save FavoriteImage to DAO
     * @param image FavoriteImage to save
     */
    public void saveFavoritePhoto(final FavoriteImage image){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.insertFavoriteImage(image);
            }
        });
    }

    /**
     * Get List of MainExploreTypes
     * @return LiveData wrapped List of MainExploreType objects
     */
    public LiveData<List<MainExploreType>> getAllExploreTypes(){
        return mMarsDao.loadAllExploreTypes();
    }

    /**
     * Delete Favorite Image from DAO
     * @param favoriteImage FavoriteImage to delete
     */
    public void deleteFavoriteImage(final FavoriteImage favoriteImage){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.deleteFavorite(favoriteImage);
            }
        });
    }

    /**
     * Get all user favorite images from DAO
     * @return LiveData wrapped List of FavoriteImage objects
     */
    public LiveData<List<FavoriteImage>> getAllFavorites(){
        return mMarsDao.loadAllFavorites();
    }

    /**
     * Delete all user favorite images from DAO
     */
    public void deleteAllFavorites(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.deleteAllFavorites();
            }
        });
    }

    /**
     * Save Tweet object to DAO
     * @param tweet Tweet object to save
     */
    public void insertTweet(final Tweet tweet){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.insertTweet(tweet);
            }
        });
    }

    /**
     * Get all Tweets from DAO
     * @return LiveData wrapped List of Tweet objects
     */
    public LiveData<List<Tweet>> getAllTweets(){
        return mMarsDao.loadAllTweets();
    }
}
