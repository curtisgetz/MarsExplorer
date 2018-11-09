package com.curtisgetz.marsexplorer.utils;

import android.content.Context;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.Cameras;
import com.curtisgetz.marsexplorer.data.WeatherDetail;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {




//JSON keys---------------------------------
    private final static String  NASA_PHOTOS = "photos";
    //next level
    private final static String NASA_PHOTO_CAMERA = "camera";
    private final static String NASA_IMAGE = "img_src";
    private final static String NASA_DATE = "earth_date";
    private final static String NASA_NAME = "name";

    private final static String MANIFEST_KEY = "photo_manifest";

    //MAN IFEST KEYS
    private final static String ROVER_LAND_DATE = "landing_date", ROVER_LAUNCH_DATE = "launch_date",
            ROVER_STATUS = "status", ROVER_MAX_SOL = "max_sol",
            ROVER_MAX_DATE = "max_date", ROVER_TOTAL_PHOTOS = "total_photos";


    //rover cameras
    private final static String ROVER_FHAZ ="FHAZ",
            ROVER_RHAZ ="RHAZ",ROVER_MAST ="MAST",
            ROVER_CHEMCAM ="CHEMCAM",ROVER_MAHLI ="MAHLI",
            ROVER_MARDI ="MARDI",ROVER_NAVCAM ="NAVCAM",
            ROVER_PANCAM ="PANCAM", ROVER_MINITES ="MINITES";

    //if it jason parse fails, use "unknown"  if parsing the image url then Picasso will load the 'error' image specified.
    private final static String FALLBACK_STRING = "unknown";


    //weather json keys

    private final static String WEATHER_STATUS = "status";
    private final static String WEATHER_SOL = "sol";
    private final static String WEATHER_MIN_TEMP = "min_temp";
    private final static String WEATHER_MAX_TEMP = "max_temp";
    private final static String WEATHER_ATMO_OPACITY = "atmo_opacity";
    private final static String WEATHER_SUNSET = "sunset";
    private final static String WEATHER_SUNRISE = "sunrise";
    private final static String WEATHER_MIN_GROUND_TEMP = "min_gts_temp";
    private final static String WEATHER_MAX_GROUND_TEMP = "max_gts_temp";
    //array of which weather details to display
    private final static String[] WEATHER_DETAILS_TO_DISPLAY = {WEATHER_MIN_TEMP,WEATHER_MAX_TEMP,
            WEATHER_ATMO_OPACITY,WEATHER_SUNSET,WEATHER_SUNRISE,WEATHER_MIN_GROUND_TEMP,
            WEATHER_MAX_GROUND_TEMP};

//JSON keys---------------------------------



    public static List<WeatherDetail> getWeatherDetail(Context context, String url) throws JSONException{
        JSONObject mainObject = new JSONObject(url);

        int status = mainObject.optInt(WEATHER_STATUS, 0);
        if(status != 200) {
            return null;
        }
        List<WeatherDetail> weatherDetails = new ArrayList<>();
        for(String jsonKey : WEATHER_DETAILS_TO_DISPLAY){
            String sol = mainObject.optString(WEATHER_SOL, FALLBACK_STRING);
            int weatherIndex = getWeatherIndexByJsonKey(jsonKey);
            int infoIndex = HelperUtils.getWeatherInfoIndex(weatherIndex);
            String label = HelperUtils.getWeatherLabel(context, weatherIndex);
            String value = mainObject.optString(jsonKey, FALLBACK_STRING);
            value = addTempUnit(context, jsonKey, value);
            weatherDetails.add(new WeatherDetail(label, value, infoIndex, sol));
        }
        return weatherDetails;
    }


    private static int getWeatherIndexByJsonKey(String jsonKey){
        switch (jsonKey){
            case WEATHER_MIN_TEMP:
                return HelperUtils.WEATHER_MIN_TEMP_INDEX;
            case WEATHER_MAX_TEMP:
                return HelperUtils.WEATHER_MAX_TEMP_INDEX;
            case WEATHER_ATMO_OPACITY:
                return HelperUtils.WEATHER_ATMO_INDEX;
            case WEATHER_SUNSET:
                return HelperUtils.WEATHER_SUNSET_INDEX;
            case WEATHER_SUNRISE:
                return HelperUtils.WEATHER_SUNRISE_INDEX;
            case WEATHER_MIN_GROUND_TEMP:
                return HelperUtils.WEATHER_MIN_GRND_TMP_INDEX;
            case WEATHER_MAX_GROUND_TEMP:
                return HelperUtils.WEATHER_MAX_GRND_TMP_INDEX;
            default:
                return -1;
        }

    }

    private static String addTempUnit(Context context, String jsonKey, String value){
        switch (jsonKey){
            case WEATHER_MIN_TEMP:
            case WEATHER_MAX_TEMP:
            case WEATHER_MIN_GROUND_TEMP:
            case WEATHER_MAX_GROUND_TEMP:
                return value + " " + context.getString(R.string.celsius);
            default:
                return value;
        }
    }
    public static RoverManifest getRoverManifest(int roverIndex, String url) throws JSONException {

        JSONObject mainObject = new JSONObject(url);

        if(!mainObject.has(MANIFEST_KEY)) return null;
        JSONObject manifestObject = mainObject.getJSONObject(MANIFEST_KEY);

        if(manifestObject.length() > 0){
            String name, launch, land, status, maxSol, maxDate, totalPhotos;

            name = manifestObject.optString(NASA_NAME, FALLBACK_STRING);
            launch = manifestObject.optString(ROVER_LAUNCH_DATE, FALLBACK_STRING);
            land = manifestObject.optString(ROVER_LAND_DATE, FALLBACK_STRING);
            status = manifestObject.optString(ROVER_STATUS, FALLBACK_STRING);
            maxSol = manifestObject.optString(ROVER_MAX_SOL, FALLBACK_STRING);
            maxDate = manifestObject.optString(ROVER_MAX_DATE, FALLBACK_STRING);
            totalPhotos = manifestObject.optString(ROVER_TOTAL_PHOTOS, FALLBACK_STRING);
            return new RoverManifest(roverIndex, name, launch, land, status, maxSol, maxDate, totalPhotos);
        }
        return null;
    }

    public static Cameras getCameraUrls(int roverIndex, String url) throws JSONException {

        List<String> fhaz = new ArrayList<>(),
                rhaz = new ArrayList<>(), mast = new ArrayList<>(),
                chemcam = new ArrayList<>(), mahli  = new ArrayList<>(),
                mardi = new ArrayList<>(), navcam  = new ArrayList<>(),
                pancam = new ArrayList<>(), minites = new ArrayList<>();

        String earthDate= "";

        JSONObject cameraJSON = new JSONObject(url);
        //Check for 'photos' key to see if there are any results, return null if no results
        if(!cameraJSON.has(NASA_PHOTOS)) return null;

        JSONArray jsonArray = cameraJSON.getJSONArray(NASA_PHOTOS);
        if(jsonArray.length() > 0){
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject cameraObject = jsonObject.getJSONObject(NASA_PHOTO_CAMERA);
                String cameraName = cameraObject.optString(NASA_NAME);
                String imgSrcString = jsonObject.optString(NASA_IMAGE);

                earthDate = getDateString(jsonObject.optString(NASA_DATE));

                //ADD img src to matching camera ArrayList
                switch (cameraName){
                    case ROVER_FHAZ:
                        fhaz.add(imgSrcString);
                        break;
                    case ROVER_RHAZ:
                        rhaz.add(imgSrcString);
                        break;
                    case ROVER_MAST:
                        mast.add(imgSrcString);
                        break;
                    case ROVER_CHEMCAM:
                        chemcam.add(imgSrcString);
                        break;
                    case ROVER_MAHLI:
                        mahli.add(imgSrcString);
                        break;
                    case ROVER_MARDI:
                        mardi.add(imgSrcString);
                        break;
                    case ROVER_NAVCAM:
                        navcam.add(imgSrcString);
                        break;
                    case ROVER_PANCAM:
                        pancam.add(imgSrcString);
                        break;
                    case ROVER_MINITES:
                        minites.add(imgSrcString);
                        break;
                    default:
                        break;
                }
            }
        }
        return new Cameras(roverIndex, fhaz, rhaz, navcam, mast, chemcam, mahli, mardi,  pancam,
                minites, earthDate);
    }




    public static String getDateString(String earthDate) throws ArrayIndexOutOfBoundsException{
        String[] dateStrings = earthDate.split("-");
        String year = dateStrings[0];
        String monthNumber = dateStrings[1];
        String monthName = dateStrings[1];
        String  day= dateStrings[2];
        switch (monthNumber){
            case "01":
                monthName = "January ";
                break;
            case "02":
                monthName = "February ";
                break;
            case "03":
                monthName = "March ";
                break;
            case "04":
                monthName = "April ";
                break;
            case "05":
                monthName = "May ";
                break;
            case "06":
                monthName = "June ";
                break;
            case "07":
                monthName = "July ";
                break;
            case "08":
                monthName = "August ";
                break;
            case "09":
                monthName = "September ";
                break;
            case "10":
                monthName = "October ";
                break;
            case "11":
                monthName = "November ";
                break;
            case "12":
                monthName = "December ";
                break;

        }
        return  monthName  + day + ", " + year;

    }

}
