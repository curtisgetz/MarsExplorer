/*
 * Created by Curtis Getz on 11/6/18 10:21 AM
 * Last modified 11/4/18 1:37 AM
 */

package com.curtisgetz.marsexplorer.data.rover_manifest;

import android.content.Context;

import com.curtisgetz.marsexplorer.data.MarsRepository;
import com.curtisgetz.marsexplorer.utils.AppExecutors;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.JsonUtils;
import com.curtisgetz.marsexplorer.utils.NetworkUtils;
import com.firebase.jobdispatcher.JobService;

import java.net.URL;

/**
 * Job Service for retrieving rover manifest details from NASA.gov API and inserting into repository
 */
public class RoverManifestJobService extends JobService {

    private final static int[] mRoverIndices = HelperUtils.ROVER_INDICES;

    private MarsRepository mRepository;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mRepository = MarsRepository.getInstance(getApplication());

        final Context context = getApplicationContext();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                for (int roverIndex : mRoverIndices) {
                    try {
                        URL manifestUrl = NetworkUtils.buildManifestUrl(context, roverIndex);
                        String jsonResponse = NetworkUtils.getResponseFromHttpUrl(manifestUrl);
                        RoverManifest manifest = JsonUtils.getRoverManifest(roverIndex, jsonResponse);
                        mRepository.insertManifest(manifest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                jobFinished(job, false);
            }
        });

        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }


}


