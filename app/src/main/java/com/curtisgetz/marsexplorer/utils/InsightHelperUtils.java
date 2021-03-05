package com.curtisgetz.marsexplorer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.print.PrinterId;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.Cameras;
import com.curtisgetz.marsexplorer.data.insight_lander.InsightPhoto;
import com.curtisgetz.marsexplorer.data.insight_lander.InsightResponse;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class InsightHelperUtils {

    private final static String INSIGHT_LAUNCH_DATE = "2018-05-05";
    private final static String INSIGHT_LANDING_DATE = "2018-11-26";
    private final static long SOL_IN_MILLISECONDS = 88775244L;
    private final static DateTime mInsightSolZero = new DateTime(DateTime.parse("2018-11-26T00:00:00.000Z"));
    private final static String IDC_CAMERA_KEY = "idc";
    private final static String ICC_CAMERA_KEY = "icc";
    private final static String INSIGHT_STATUS_REMOTE_CONFIG_Key = "insight_mission_status";
    //private DateTime mCurrentDate = new DateTime(DateTimeZone.UTC);

    public static RoverManifest createInsightManifest(Context context){
        String status = getStatusAndSetMaxDate(context);
        return new RoverManifest(HelperUtils.INSIGHT_LANDER_INDEX, HelperUtils.getRoverNameByIndex(context, HelperUtils.INSIGHT_LANDER_INDEX),
                INSIGHT_LAUNCH_DATE, INSIGHT_LANDING_DATE, status, String.valueOf(estimateMaxSol()),  getMaxDate(context), "");

    }

    public static String getStatusAndSetMaxDate(Context context){
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        String status = remoteConfig.getString(INSIGHT_STATUS_REMOTE_CONFIG_Key);
        if (status.equals("Active")){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(context.getString(R.string.pref_insight_latest_max_date), new DateTime().toString());
            editor.apply();
        }
        return status;
    }

    public static String getMaxDate(Context context){
       //get max date from sharedpreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String date =  sharedPreferences.getString(context.getString(R.string.pref_insight_latest_max_date),"2021-02-28");
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateObj = new DateTime(date);
        String dateTime = dateObj.toString(format);
        return dateTime;

        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        //return dateFormat.format(date);
    }

    public static Cameras insightResponseToCameras(InsightResponse insightResponse, String sol){
        List<String> iccUrls = new ArrayList<>();
        List<String> idcUrls = new ArrayList<>();
        List<InsightPhoto> photos = insightResponse.getItems();
        for(InsightPhoto photo : photos){
            if(photo.getInstrument().equals(ICC_CAMERA_KEY)){
                iccUrls.add(photo.getUrl());
            }else if(photo.getInstrument().equals(IDC_CAMERA_KEY)){
                idcUrls.add(photo.getUrl());

            }
        }
        return new Cameras(HelperUtils.INSIGHT_LANDER_INDEX, idcUrls, iccUrls, "", sol);

    }

    /**
     * Get an estimation of Max Sol
     * @return estimated max sol
     */
    public static int estimateMaxSol(){
        DateTime currentDate = new DateTime(DateTimeZone.UTC);
        Duration duration = new Duration(mInsightSolZero, currentDate);
        long milliSinceLanding = duration.getMillis();
        int estimatedSols = (int) (milliSinceLanding / SOL_IN_MILLISECONDS);
        Log.d("LOG", String.valueOf(estimatedSols));
        estimatedSols--;
        return estimatedSols;
    }

}
