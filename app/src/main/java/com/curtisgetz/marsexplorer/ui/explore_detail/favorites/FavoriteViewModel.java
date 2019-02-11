
/*
 * Created by Curtis Getz on 11/6/18 3:19 PM
 * Last modified 11/4/18 1:48 AM
 */

package com.curtisgetz.marsexplorer.ui.explore_detail.favorites;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.curtisgetz.marsexplorer.data.MarsRepository;

import java.util.List;


//May need a better solution if number of favorite photos gets too high.
//Need to look into proper handling for large data sets rather than loading all favorites
//into view model.   Can be optimized.

/**
 * ViewModel for handling all of the user's favorite images {@link FavoriteImage}
 */
public class FavoriteViewModel extends AndroidViewModel {


    private MarsRepository mRepository;
    private LiveData<List<FavoriteImage>> mFavorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        mRepository = MarsRepository.getInstance(application);
        mFavorites = mRepository.getAllFavorites();
    }

    public LiveData<List<FavoriteImage>> getFavorites() {
        return mFavorites;
    }

    /**
     * Calls deleteFavoriteImage method in {@link MarsRepository} to delete an image from user's favorites.
     *
     * @param favoriteImage the {@link FavoriteImage} to delete.
     */
    private void deleteFavoriteImage(FavoriteImage favoriteImage) {
        mRepository.deleteFavoriteImage(favoriteImage);
    }

    /**
     * Saves an image to user's favorites if image is not already a favorite.
     * Checks if the image url is already a favorite. If it is not then create a new {@link FavoriteImage}
     * object with the url, date string, and rover index.
     *
     * @param url        the URL of the image.
     * @param dateString the date, as a String, the photo was taken.
     * @param roverIndex the index of the rover the photo was taken by.
     */
    public void saveFavoriteImage(String url, String dateString, int roverIndex) {
        if (!isAlreadyFavorite(url)) {
            FavoriteImage favoriteImage = new FavoriteImage(url, dateString, roverIndex);
            mRepository.saveFavoritePhoto(favoriteImage);
        }
    }

    /**
     * Removes an image from the user's favorites. Searches through {@link FavoriteImage} objects
     * to find the FavoriteImage with a url that matches the current image in ViewPager.
     * When a match is found, call deleteFavoriteImage() method and pass the FavoriteImage to delete.
     *
     * @param url the url of the image the user wants to remove from their favorites
     */
    public void removeAlreadyFavorite(String url) {
        if (mFavorites.getValue() == null) {
            return;
        }
        for (FavoriteImage image : mFavorites.getValue()) {
            if (url.equalsIgnoreCase(image.getImageUrl())) {
                deleteFavoriteImage(image);
                return;
            }
        }
    }

    /**
     * Checjs if an image url is already saved as a favorite.
     *
     * @param url the URL of the image to check.
     * @return true if the url is already a favorite.
     */
    public boolean isAlreadyFavorite(String url) {
        if (mFavorites.getValue() == null) {
            return false;
        }
        for (FavoriteImage image : mFavorites.getValue()) {
            if (url.equalsIgnoreCase(image.getImageUrl())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calls deleteAllFavorites() method in {@link MarsRepository} to delete all of the user's
     * favorite images.
     */
    void deleteAllFavorites() {
        mRepository.deleteAllFavorites();
    }

}
