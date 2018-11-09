package com.curtisgetz.marsexplorer.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.MarsRepository;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.ArrayList;
import java.util.List;

public class ExploreTypeViewModel  extends AndroidViewModel {

    private MarsRepository mRepository;
    private LiveData<List<MainExploreType>> mExploreTypes;

    public ExploreTypeViewModel(@NonNull Application application) {
        super(application);
        mRepository = MarsRepository.getInstance(application);
        mExploreTypes = mRepository.getAllExploreTypes();
    }

    public LiveData<List<MainExploreType>> getExploreTypes() {
        return mExploreTypes;
    }


    public void addExploreTypesToDB(Context context){
        //get explore types in HelperUtils.
        List<MainExploreType> mainExploreTypeList = new ArrayList<>(HelperUtils.getAllExploreTypes(context));
        mRepository.addExploreTypesToDB(mainExploreTypeList);
    }

}
