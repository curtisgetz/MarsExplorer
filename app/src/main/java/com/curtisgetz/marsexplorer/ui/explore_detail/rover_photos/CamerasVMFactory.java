package com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

public class CamerasVMFactory extends ViewModelProvider.NewInstanceFactory {

    private Application mApplication;
    private int mRoverIndex;
    private String mSolNumber;

    CamerasVMFactory(Application application, int roverIndex, String solNumber) {
        this.mApplication = application;
        this.mRoverIndex = roverIndex;
        this.mSolNumber = solNumber;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CamerasViewModel(mApplication, mRoverIndex, mSolNumber);
    }
}
