
/*
 * Created by Curtis Getz on 11/6/18 11:44 AM
 * Last modified 11/4/18 1:48 AM
 */

package com.curtisgetz.marsexplorer.ui.explore;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Factory for RoverManifest View Model. Allows passing rover index into View Model constructor
 */
public class RoverManifestVMFactory extends ViewModelProvider.NewInstanceFactory {

    private Application mApplication;
    private final int mRoverIndex;

    RoverManifestVMFactory(int roverIndex, Application application) {
        this.mApplication = application;
        this.mRoverIndex = roverIndex;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RoverManifestViewModel(mRoverIndex, mApplication);
    }
}
