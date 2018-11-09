package com.curtisgetz.marsexplorer.utils;

import android.content.Context;

import com.curtisgetz.marsexplorer.R;

public final class InformationUtils {

//class for getting correct text for Info Dialog

    public final static int ERROR_LOADING_INFO = -1;
    public final static int SOL_RANGE_INFO = 0;
    public final static int WEATHER_INFO = 1;
    public final static int AIR_TEMP_INFO = 2;
    public final static int GROUND_TEMP_INFO = 3;
    public final static int SUNRISE_SUNSET_INFO = 4;
    public final static int ATMO_INFO = 5;
    public final static int PHOTO_LIMIT_PREF = 6;
    public final static int ROVER_JOB_PREF = 7;
    public final static int TWEET_JOB_PREF = 8;
    public final static int CREDIT_INFO = 9;



    public static String getInformationText(Context context,  int infoIndex){

        switch (infoIndex){
            case SOL_RANGE_INFO:
                return context.getResources().getString(R.string.info_sol_text_details);
            case WEATHER_INFO:
                return context.getString(R.string.info_weather);
            case AIR_TEMP_INFO:
                return context.getString(R.string.info_air_temp);
            case GROUND_TEMP_INFO:
                return context.getString(R.string.info_ground_temp);
            case SUNRISE_SUNSET_INFO:
                return context.getString(R.string.info_sunrise_sunset);
            case ATMO_INFO:
                return context.getString(R.string.info_atmo);
            case PHOTO_LIMIT_PREF:
                return context.getString(R.string.shown_photos_info_dialog);
            case ROVER_JOB_PREF:
                return context.getString(R.string.rover_manifest_pref_desc);
            case TWEET_JOB_PREF:
                return context.getString(R.string.tweet_pref_desc);
            case CREDIT_INFO:
                return context.getString(R.string.credits_dialog);
            default:
                return context.getResources().getString(R.string.info_error_text);
        }
    }



}
