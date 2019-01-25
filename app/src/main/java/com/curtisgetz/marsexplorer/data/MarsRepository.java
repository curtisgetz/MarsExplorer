
/*
 * Created by Curtis Getz on 11/6/18 10:37 AM
 * Last modified 11/4/18 1:40 AM
 */

package com.curtisgetz.marsexplorer.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.NetworkRequest;
import android.util.Log;

import com.curtisgetz.marsexplorer.data.room.AppDataBase;
import com.curtisgetz.marsexplorer.data.room.MarsDao;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;
import com.curtisgetz.marsexplorer.utils.AppExecutors;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.JsonUtils;
import com.curtisgetz.marsexplorer.utils.NetworkUtils;

import java.io.IOException;
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

    public MutableLiveData<String> getSolFromDate(final Context context, final int roverIndex, final String date){
        final MutableLiveData<String> sol = new MutableLiveData<>();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                URL url = NetworkUtils.buildDateToSolUrl(context, roverIndex, date);
                try{
                    String json = NetworkUtils.getResponseFromHttpUrl(url);
                    String solString = JsonUtils.extractSolFromDateUrl(json);
                    sol.postValue(solString);
                    Log.e("JSON", solString);
                }catch (IOException e){

                }
            }
        });

        return sol;
    }



    /**
     * Get Cameras object from NASA.gov API query for specified rover and sol
     * @param context needed to build url and parse Json
     * @param roverIndex needed for API query
     * @param sol needed for API query
     * @return LiveData wrapped Cameras object
     */
    public MutableLiveData<Cameras> getCameras(final Context context, final int roverIndex, final String sol){
        final int MAX_QUERY = 50;
        final MutableLiveData<Cameras> cameras = new MutableLiveData<>();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL findRequestUrl = NetworkUtils.buildPhotosCheckUrl(context, roverIndex, sol);
                    String jsonResponse = NetworkUtils.getResponseFromHttpUrl(context, findRequestUrl);
                    if(JsonUtils.isSolActive(jsonResponse)){
                        //If there are images then return the post the values to cameras.
                        URL solRequestUrl = NetworkUtils.buildPhotosUrl(context, roverIndex, sol);
                        String cameraJson = NetworkUtils.getResponseFromHttpUrl(context, solRequestUrl);
                        cameras.postValue(JsonUtils.getCameraUrls(roverIndex, cameraJson));
                    //if no cameras then attempt to round up to nearest sol with images and postValue.
                    // if unable to find an active sol after running through MAX number of times
                    // then return the orginal sol and UI will display a message informing the user
                    //there are no images on this sol
                    }else {
                        String newSol = findNextActiveSol(context, sol, roverIndex);
                        URL solRequestUrl = NetworkUtils.buildPhotosUrl(context, roverIndex,newSol);
                        String cameraJson = NetworkUtils.getResponseFromHttpUrl(context, solRequestUrl);
                        cameras.postValue(JsonUtils.getCameraUrls(roverIndex, cameraJson));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return cameras;
    }

    /**
     * Finds the next sol with images and returns that sol.
     * @param context needed to build URL and parse Json
     * @param sol original sol searched
     * @param roverIndex index of rover being searched
     * @return the next sol with images, or the original sol if hit MAX_QUERY
     */
    private String findNextActiveSol(Context context, String sol, int roverIndex ){
        final int MAX_QUERY = 50;
        //Search for nearest sol (up to MAX_QUERY_COUNT number of times if the sol searched
        // is empty
        int queryCount = 0;
        String checkUrl = "";
        String newSol = sol;
        Integer solInt = Integer.parseInt(sol);

        while (queryCount < MAX_QUERY && !JsonUtils.isSolActive(checkUrl)) {
            try {
                solInt++;
                newSol = String.valueOf(solInt);
                URL findUrl = NetworkUtils.buildPhotosCheckUrl(context, roverIndex, newSol);
                checkUrl = NetworkUtils.getResponseFromHttpUrl(context, findUrl);
                queryCount++;
                Log.d("REPOSITORY", String.valueOf(queryCount) + " ** " + newSol);
            } catch (IOException e) {
                e.printStackTrace();
                return sol;
            }
        }
        return newSol;

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
