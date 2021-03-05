package com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

public class SolFromDateVMFactory extends ViewModelProvider.NewInstanceFactory {


    private Application mApplication;
    private int mRoverIndex;
    private String mDate;

    public SolFromDateVMFactory(Application mApplication, int mRoverIndex, String mDate) {
        this.mApplication = mApplication;
        this.mRoverIndex = mRoverIndex;
        this.mDate = mDate;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SolFromDateViewModel(mApplication, mRoverIndex, mDate);
    }
}
