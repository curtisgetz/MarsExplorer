/*
 * Created by Curtis Getz on 11/6/18 11:47 AM
 * Last modified 11/4/18 1:48 AM
 */

package com.curtisgetz.marsexplorer.ui.explore;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import android.content.Context;

import com.curtisgetz.marsexplorer.data.MarsRepository;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.Random;


/**
 * View Model for Rover Manifest.
 */
class RoverManifestViewModel extends ViewModel {

    private LiveData<RoverManifest> mManifest;
    private MarsRepository mRepository;


    RoverManifestViewModel(int roverIndex, Application application){
        this.mRepository = MarsRepository.getInstance(application);
        this.mManifest = mRepository.getRoverManifest(roverIndex);
    }

    LiveData<RoverManifest> getManifest() {
        return mManifest;
    }

    /**
     * Have Repository download manifests
     * @param context pass context to Repository
     */
    void downloadNewManifests(Context context){
        mRepository.downloadManifestsFromNetwork(context);
    }


    public String validateSolInRange(String solInput){
        if(mManifest.getValue() == null) return HelperUtils.DEFAULT_SOL_NUMBER;
        int solInputNumber;
        try {
            solInputNumber = Integer.parseInt(solInput);

        }catch (NumberFormatException e){
            //if integer parse fails then use random sol
            return getRandomSol();
        }
        int maxSol = mManifest.getValue().getMaxSolInt();
        int minSol = mManifest.getValue().getMinSolInt();
        if(solInputNumber < minSol){
            //if entered sol is lower than min, set it to the min
            return String.valueOf(minSol);
        }else if(solInputNumber > maxSol){
            //if entered sol is greater than max sol, set to max
            return String.valueOf(maxSol);
        }else {
            //if sol is in range, return the same sol back
            return solInput;
        }
    }

    //select a random sol that is in range
    public String getRandomSol(){
        if(mManifest.getValue() == null) return HelperUtils.DEFAULT_SOL_NUMBER;
        int minSol = mManifest.getValue().getMinSolInt();
        int maxSol = mManifest.getValue().getMaxSolInt();
        Random randomRand = new Random();
        int randomNum = randomRand.nextInt((maxSol - minSol) + 1) + minSol;
        return String.valueOf(randomNum);
    }

}
