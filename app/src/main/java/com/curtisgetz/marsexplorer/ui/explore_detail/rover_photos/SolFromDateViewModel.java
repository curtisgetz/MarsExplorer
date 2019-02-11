package com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.curtisgetz.marsexplorer.data.MarsRepository;

/**
 * View model for getting Sol for user selected Date
 */
class SolFromDateViewModel extends AndroidViewModel {


    private LiveData<String> mSolLiveData;
    private MarsRepository mRepository;


    SolFromDateViewModel(@NonNull Application application, int roverIndex, String date) {
        super(application);
        mRepository = MarsRepository.getInstance(application);
        mSolLiveData = mRepository.getSolFromDate(application.getApplicationContext(), roverIndex, date);
    }


    LiveData<String> getSolLiveData() {
        return mSolLiveData;
    }
}
