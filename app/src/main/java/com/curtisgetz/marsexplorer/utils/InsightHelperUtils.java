package com.curtisgetz.marsexplorer.utils;

import android.content.Context;
import android.print.PrinterId;
import android.util.Log;

import com.curtisgetz.marsexplorer.data.Cameras;
import com.curtisgetz.marsexplorer.data.insight_lander.InsightPhoto;
import com.curtisgetz.marsexplorer.data.insight_lander.InsightResponse;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;

public final class InsightHelperUtils {

    private final static String INSIGHT_LAUNCH_DATE = "2018-05-05";
    private final static String INSIGHT_LANDING_DATE = "2018-11-26";
    private final static long SOL_IN_MILLISECONDS = 88775244L;
    private final static DateTime mInsightSolZero = new DateTime(DateTime.parse("2018-11-26T00:00:00.000Z"));
    private final static String IDC_CAMERA_KEY = "idc";
    private final static String ICC_CAMERA_KEY = "icc";
    //private DateTime mCurrentDate = new DateTime(DateTimeZone.UTC);

    public static RoverManifest createInsightManifest(Context context){
        return new RoverManifest(HelperUtils.INSIGHT_LANDER_INDEX, HelperUtils.getRoverNameByIndex(context, HelperUtils.INSIGHT_LANDER_INDEX),
                INSIGHT_LAUNCH_DATE, INSIGHT_LANDING_DATE, "", String.valueOf(estimateMaxSol()), "", "");

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
