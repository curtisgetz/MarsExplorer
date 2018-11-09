package com.curtisgetz.marsexplorer.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifestJobService;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class MarsExplorerApp extends Application{

    private FirebaseJobDispatcher mJobDispatcher;
    //set trigger execution min and max for execution window of Job
    private final static int TIME_BETWEEN_JOBS = 21600000; //6 hours in milliseconds.
    private final static int JOB_WINDOW = 1800000; // 30 minutes in milliseconds
    private final static int MANIFEST_JOB_MIN = TIME_BETWEEN_JOBS - JOB_WINDOW;
    private final static int MANIFEST_JOB_MAX= TIME_BETWEEN_JOBS + JOB_WINDOW;
    //listen for pref changes to schedule or cancel job
    private SharedPreferences.OnSharedPreferenceChangeListener mPrefListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals(getString(R.string.pref_rover_job_scheduler_key))){
                scheduleManifestJob();
            }

        }
    };




    public MarsExplorerApp() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(mPrefListener);
        scheduleManifestJob();
    }


    private void scheduleManifestJob(){
        //Check preferences to see if user wants to keep rover manifests up to date in background.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean scheduleRoverJob = sharedPreferences.getBoolean(
                getString(R.string.pref_rover_job_scheduler_key),
                getResources().getBoolean(R.bool.pref_rover_job_scheduler_default));

        mJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        if(scheduleRoverJob){
            //if user wants to keep manifests up to date then schedule job
            //keep manifests up to date periodically but will also update on demand
            Job manifestJob = mJobDispatcher.newJobBuilder()
                    .setService(RoverManifestJobService.class)
                    .setTag(RoverManifestJobService.class.getSimpleName())
                    .setLifetime(Lifetime.FOREVER)
                    .setRecurring(true)
                    .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                    .setTrigger(Trigger.executionWindow(MANIFEST_JOB_MIN, MANIFEST_JOB_MAX))
                    .setReplaceCurrent(true)
                    .setConstraints(Constraint.ON_ANY_NETWORK, Constraint.DEVICE_CHARGING,
                            Constraint.ON_UNMETERED_NETWORK)
                    .build();
            mJobDispatcher.mustSchedule(manifestJob);


        }else {
            //if user doesn't want to run background jobs, then cancel jobs.
            mJobDispatcher.cancel(RoverManifestJobService.class.getSimpleName());
        }

    }

}
