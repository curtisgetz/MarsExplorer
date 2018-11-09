package com.curtisgetz.marsexplorer.ui.explore_detail.mars_weather;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.curtisgetz.marsexplorer.data.MarsRepository;
import com.curtisgetz.marsexplorer.data.WeatherDetail;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {


    private LiveData<List<WeatherDetail>> mWeather;
    private MarsRepository mRepository;


    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mRepository = MarsRepository.getInstance(application);
        mWeather = mRepository.getLatestWeather(application.getApplicationContext());
    }

    public LiveData<List<WeatherDetail>> getWeather(){
        return mWeather;
    }

}
