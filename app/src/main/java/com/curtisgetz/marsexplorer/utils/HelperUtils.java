

/*
 * Created by Curtis Getz on 11/6/18 9:48 AM
 * Last modified 11/6/18 9:33 AM
 */

package com.curtisgetz.marsexplorer.utils;

import android.content.Context;
import android.content.PeriodicSync;

import androidx.annotation.IntDef;
import androidx.annotation.NavigationRes;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.RoverScience;
import com.curtisgetz.marsexplorer.data.rover_explore.ExploreCategory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Helper class to handle indices and fetching resources
 */

public final class HelperUtils {


    //TODO SEPARATE INTO MORE UTILITY CLASSES BASED ON INDEX TYPE.
    public final static String PHOTO_PAGER_URL_EXTRA = "photo_url_extra";
    public final static String SCIENCE_PARCELABLE_EXTRA = "science_parcelable_extra";

    public final static String DEFAULT_SOL_NUMBER = "200";

    public final static String CURIOSITY_LANDING_DATE = "2012-08-06";
    public final static String OPPORTUNITY_LANDING_DATE = "2004-01-25";
    public final static String SPIRIT_LANDING_DATE = "2004-01-04";


    public final static long SPIRIT_LANDING_MILLISECONDS = 1055203200000L; // June 10, 2003, Spirit's landing date
    public final static long OPPORTUNITY_LANDING_MILLISECONDS = 1074988800000L; // Jan 25, 2004
    public final static long CURIOSITY_LANDING_MILLISECONDS = 1344211200000L; // Aug 6, 2012

    //Sol start for each rover
    public final static int CURIOSITY_SOL_START = 0;
    public final static int OPPORTUNITY_SOL_START = 1;
    public final static int SPIRIT_SOL_START = 1;
    public final static int INSIGHT_SOL_START = 0;
    public final  static int PERSEVERANCE_SOL_START = 0;
    //all rovers have at least 6 sols. use if any errors getting true max sol
    public final static int DEFAULT_MAX_SOL = 6;


    @IntDef({MARS_EXPLORE_INDEX, PERSEVERANCE_ROVER_INDEX ,CURIOSITY_ROVER_INDEX, OPPORTUNITY_ROVER_INDEX, SPIRIT_ROVER_INDEX, INSIGHT_LANDER_INDEX})
    @Retention(RetentionPolicy.SOURCE)
    @interface EXPLORE_INDEX {
    }

    @IntDef({CURIOSITY_ROVER_INDEX, PERSEVERANCE_ROVER_INDEX, OPPORTUNITY_ROVER_INDEX, SPIRIT_ROVER_INDEX, INSIGHT_LANDER_INDEX})
    @Retention(RetentionPolicy.SOURCE)
    @interface ROVER_INDEX {
    }


    //EXPLORE INDICES
    public final static int MARS_EXPLORE_INDEX = 0;
    public final static int CURIOSITY_ROVER_INDEX = 1;
    public final static int OPPORTUNITY_ROVER_INDEX = 2;
    public final static int SPIRIT_ROVER_INDEX = 3;
    public final static int INSIGHT_LANDER_INDEX = 4;
    public final static int PERSEVERANCE_ROVER_INDEX = 5;

    //EXPLORE SORT
    public final static int MARS_EXPLORE_SORT_INDEX = 1;
    public final static int PERSEVERANCE_SORT_INDEX = 2;
    public final static int INSIGHT_SORT_INDEX = 3;
    public final static int CURIOSITY_SORT_INDEX = 4;
    public final static int OPPORTUNITY_SORT_INDEX = 5;
    public final static int SPIRIT_SORT_INDEX = 6;

    public final static int[] ROVER_INDICES = {PERSEVERANCE_ROVER_INDEX, CURIOSITY_ROVER_INDEX, OPPORTUNITY_ROVER_INDEX, SPIRIT_ROVER_INDEX};


    @IntDef({MARS_WEATHER_CAT_INDEX, MARS_FACTS_CAT_INDEX, MARS_FAVORITES_CAT_INDEX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MARS_EXPLORE_CATEGORY_INDEX {
    }

    @IntDef({ROVER_PICTURES_CAT_INDEX, ROVER_INFO_CAT_INDEX, ROVER_SCIENCE_CAT_INDEX, ROVER_TWEETS_CAT_INDEX})
    @Retention(RetentionPolicy.SOURCE)
    @interface ROVER_CATEGORY_INDEX {
    }

    @IntDef({ROVER_PICTURES_CAT_INDEX, ROVER_INFO_CAT_INDEX, ROVER_SCIENCE_CAT_INDEX, ROVER_TWEETS_CAT_INDEX,
            MARS_WEATHER_CAT_INDEX, MARS_FACTS_CAT_INDEX, MARS_FAVORITES_CAT_INDEX})
    @Retention(RetentionPolicy.SOURCE)
    @interface EXPLORE_CATEGORY_INDEX {
    }


    //MARS EXPLORE CATEGORY INDICES
    public final static int MARS_WEATHER_CAT_INDEX = 100;
    public final static int MARS_FACTS_CAT_INDEX = 101;
    public final static int MARS_FAVORITES_CAT_INDEX = 102;

    private final static int[] MARS_EXPLORE_CATEGORIES =
            {MARS_WEATHER_CAT_INDEX, MARS_FACTS_CAT_INDEX, MARS_FAVORITES_CAT_INDEX};

    //ROVER CATEGORY INDICES
    public final static int ROVER_PICTURES_CAT_INDEX = 0;
    public final static int ROVER_INFO_CAT_INDEX = 1;
    public final static int ROVER_SCIENCE_CAT_INDEX = 2;
    public final static int ROVER_TWEETS_CAT_INDEX = 3;


    //Set available categories for each rover
    private final static int[] CURIOSITY_CATEGORIES = {ROVER_PICTURES_CAT_INDEX,
            ROVER_INFO_CAT_INDEX, ROVER_SCIENCE_CAT_INDEX, MARS_FAVORITES_CAT_INDEX};
    private final static int[] OPPORTUNITY_CATEGORIES = {ROVER_PICTURES_CAT_INDEX,
            ROVER_INFO_CAT_INDEX, ROVER_SCIENCE_CAT_INDEX, MARS_FAVORITES_CAT_INDEX};
    private final static int[] SPIRIT_CATEGORIES = {ROVER_PICTURES_CAT_INDEX,
            ROVER_INFO_CAT_INDEX, ROVER_SCIENCE_CAT_INDEX, MARS_FAVORITES_CAT_INDEX};

    private final static int[] INSIGHT_CATEGORIES = {ROVER_PICTURES_CAT_INDEX, MARS_FAVORITES_CAT_INDEX};

    private final static int[] PERSEVERANCE_CATEGORIES = {ROVER_PICTURES_CAT_INDEX, MARS_FAVORITES_CAT_INDEX};


    @IntDef({CAM_FHAZ_INDEX, CAM_RHAZ_INDEX, CAM_MAST_INDEX, CAM_CHEMCAM_INDEX, CAM_MAHLI_INDEX,
            CAM_MARDI_INDEX, CAM_NAVCAM_INDEX, CAM_PANCAM_INDEX, CAM_MINITES_INDEX, CAM_IDC_INDEX, CAM_ICC_INDEX,
            CAM_EDL_RUCAM_INDEX, CAM_EDL_RDCAM_INDEX, CAM_EDL_DDCAM_INDEX, CAM_EDL_PUCAM1_INDEX, CAM_EDL_PUCAM2_INDEX,
            CAM_NAVCAM_LEFT_INDEX, CAM_NAVCAM_RIGHT_INDEX, CAM_MCZ_LEFT_INDEX, CAM_MCZ_RIGHT_INDEX, CAM_FRONT_HAZCAM_LEFT_A_INDEX,
            CAM_FRONT_HAZCAM_RIGHT_A_INDEX, CAM_REAR_HAZCAM_LEFT_INDEX, CAM_REAR_HAZCAM_RIGHT_INDEX,
            CAM_SKYCAM_INDEX, CAM_SHERLOC_INDEX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CAMERA_INDEX {
    }

    //CAMERA INDICES
    public final static int CAM_FHAZ_INDEX = 0;
    public final static int CAM_RHAZ_INDEX = 1;
    public final static int CAM_MAST_INDEX = 2;
    public final static int CAM_CHEMCAM_INDEX = 3;
    public final static int CAM_MAHLI_INDEX = 4;
    public final static int CAM_MARDI_INDEX = 5;
    public final static int CAM_NAVCAM_INDEX = 6;
    public final static int CAM_PANCAM_INDEX = 7;
    public final static int CAM_MINITES_INDEX = 8;
    //Insight Camera Indices
    public final static int CAM_IDC_INDEX = 9;
    public final static int CAM_ICC_INDEX = 10;
    //Perseverance Camera Indices
    public final static int CAM_EDL_RUCAM_INDEX = 11;
    public final static int CAM_EDL_RDCAM_INDEX = 12;
    public final static int CAM_EDL_DDCAM_INDEX = 13;
    public final static int CAM_EDL_PUCAM1_INDEX = 14;
    public final static int CAM_EDL_PUCAM2_INDEX = 15;
    public final static int CAM_NAVCAM_LEFT_INDEX = 16;
    public final static int CAM_NAVCAM_RIGHT_INDEX = 17;
    public final static int CAM_MCZ_RIGHT_INDEX = 18;
    public final static int CAM_MCZ_LEFT_INDEX = 19;
    public final static int CAM_FRONT_HAZCAM_LEFT_A_INDEX = 20 ;
    public final static int CAM_FRONT_HAZCAM_RIGHT_A_INDEX = 21;
    public final static int CAM_REAR_HAZCAM_LEFT_INDEX = 22;
    public final static int CAM_REAR_HAZCAM_RIGHT_INDEX = 23;
    public final static int CAM_SKYCAM_INDEX = 24;
    public final static int CAM_SHERLOC_INDEX = 25;



    @IntDef({WEATHER_MIN_TEMP_INDEX, WEATHER_MAX_TEMP_INDEX, WEATHER_ATMO_INDEX, WEATHER_SUNSET_INDEX,
            WEATHER_SUNRISE_INDEX, WEATHER_MIN_GRND_TMP_INDEX, WEATHER_MAX_GRND_TMP_INDEX})
    @Retention(RetentionPolicy.SOURCE)
    @interface WEATHER_INDEX {
    }

    //WEATHER DETAILS INDICES
    final static int WEATHER_MIN_TEMP_INDEX = 3;
    final static int WEATHER_MAX_TEMP_INDEX = 4;
    final static int WEATHER_ATMO_INDEX = 5;
    final static int WEATHER_SUNSET_INDEX = 6;
    final static int WEATHER_SUNRISE_INDEX = 7;
    final static int WEATHER_MIN_GRND_TMP_INDEX = 9;
    final static int WEATHER_MAX_GRND_TMP_INDEX = 10;


    @IntDef({SCIENCE_CHEMCAM_INDEX, SCIENCE_MAHLI_INDEX, SCIENCE_MINITES_INDEX, SCIENCE_REMS_INDEX,
            SCIENCE_APXS_INDEX, SCIENCE_CHEMIN_INDEX, SCIENCE_SAM_INDEX, SCIENCE_RAD_INDEX, SCIENCE_DAN_INDEX,
            SCIENCE_MB_INDEX, SCIENCE_MI_INDEX, SCIENCE_MASTCAM_INDEX, SCIENCE_PANCAM_INDEX, SCIENCE_HAZCAM_INDEX,
            SCIENCE_NAVCAM_INDEX, SCIENCE_DRT_INDEX, SCIENCE_ROBOTIC_ARM_INDEX, SCIENCE_LEGS_WHEELS_INDEX,
            SCIENCE_POWER_INDEX, SCIENCE_ANTENNA_INDEX, SCIENCE_BRAINS_INDEX, SCIENCE_MAGNET_INDEX,
            SCIENCE_SOLAR_INDEX, SCIENCE_BODY_INDEX, SCIENCE_TEMP_INDEX, SCIENCE_MARDI_INDEX,
            SCIENCE_RAT_INDEX
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface ALL_SCIENCE_INDEX {
    }

    @IntDef({SCIENCE_MASTCAM_INDEX, SCIENCE_CHEMCAM_INDEX, SCIENCE_REMS_INDEX, SCIENCE_HAZCAM_INDEX,
            SCIENCE_NAVCAM_INDEX, SCIENCE_MAHLI_INDEX, SCIENCE_APXS_INDEX, SCIENCE_CHEMIN_INDEX,
            SCIENCE_SAM_INDEX, SCIENCE_DRT_INDEX, SCIENCE_RAD_INDEX, SCIENCE_DAN_INDEX, SCIENCE_MARDI_INDEX,
            SCIENCE_ROBOTIC_ARM_INDEX, SCIENCE_LEGS_WHEELS_INDEX, SCIENCE_POWER_INDEX, SCIENCE_ANTENNA_INDEX,
            SCIENCE_BRAINS_INDEX})
    @Retention(RetentionPolicy.SOURCE)
    @interface CURIOSITY_SCIENCE_INDEX {
    }

    @IntDef({SCIENCE_PANCAM_INDEX, SCIENCE_NAVCAM_INDEX, SCIENCE_MINITES_INDEX, SCIENCE_MB_INDEX,
            SCIENCE_MI_INDEX, SCIENCE_APXS_INDEX, SCIENCE_RAT_INDEX, SCIENCE_MAGNET_INDEX, SCIENCE_SOLAR_INDEX,
            SCIENCE_BODY_INDEX, SCIENCE_TEMP_INDEX, SCIENCE_BRAINS_INDEX, SCIENCE_ROBOTIC_ARM_INDEX,
            SCIENCE_LEGS_WHEELS_INDEX, SCIENCE_ANTENNA_INDEX})
    @Retention(RetentionPolicy.SOURCE)
    @interface OPP_SPIRIT_SCIENCE_INDEX {
    }

    //ROVER INFO INDICES
    private final static int SCIENCE_MASTCAM_INDEX = 200;
    private final static int SCIENCE_PANCAM_INDEX = 202;
    private final static int SCIENCE_HAZCAM_INDEX = 204;
    private final static int SCIENCE_NAVCAM_INDEX = 203;
    private final static int SCIENCE_DRT_INDEX = 211;
    private final static int SCIENCE_ROBOTIC_ARM_INDEX = 215;
    private final static int SCIENCE_LEGS_WHEELS_INDEX = 216;
    private final static int SCIENCE_POWER_INDEX = 217;
    private final static int SCIENCE_ANTENNA_INDEX = 218;
    private final static int SCIENCE_BRAINS_INDEX = 219;
    private final static int SCIENCE_MAGNET_INDEX = 223;
    private final static int SCIENCE_SOLAR_INDEX = 224;
    private final static int SCIENCE_BODY_INDEX = 225;
    private final static int SCIENCE_TEMP_INDEX = 226;
    private final static int SCIENCE_MARDI_INDEX = 214;
    private final static int SCIENCE_RAT_INDEX = 222;


    private final static int[] CURIOSITY_ROVER_INFO_INDICES = {SCIENCE_MASTCAM_INDEX,
            SCIENCE_HAZCAM_INDEX, SCIENCE_NAVCAM_INDEX, SCIENCE_DRT_INDEX, SCIENCE_MARDI_INDEX,
            SCIENCE_ROBOTIC_ARM_INDEX, SCIENCE_LEGS_WHEELS_INDEX, SCIENCE_POWER_INDEX,
            SCIENCE_ANTENNA_INDEX, SCIENCE_BRAINS_INDEX};

    private final static int[] OPPORTUNITY_ROVER_INFO_INDICES = {SCIENCE_PANCAM_INDEX,
            SCIENCE_NAVCAM_INDEX, SCIENCE_RAT_INDEX, SCIENCE_MAGNET_INDEX, SCIENCE_SOLAR_INDEX,
            SCIENCE_BODY_INDEX, SCIENCE_BRAINS_INDEX, SCIENCE_TEMP_INDEX,
            SCIENCE_ROBOTIC_ARM_INDEX, SCIENCE_LEGS_WHEELS_INDEX, SCIENCE_ANTENNA_INDEX};

    private final static int[] SPIRIT_ROVER_INFO_INDICES = {SCIENCE_PANCAM_INDEX,
            SCIENCE_NAVCAM_INDEX, SCIENCE_RAT_INDEX, SCIENCE_MAGNET_INDEX, SCIENCE_SOLAR_INDEX,
            SCIENCE_BODY_INDEX, SCIENCE_BRAINS_INDEX, SCIENCE_TEMP_INDEX,
            SCIENCE_ROBOTIC_ARM_INDEX, SCIENCE_LEGS_WHEELS_INDEX, SCIENCE_ANTENNA_INDEX};


    //SCIENCE INSTRUMENT INDICES
    private final static int SCIENCE_CHEMCAM_INDEX = 201;
    private final static int SCIENCE_MAHLI_INDEX = 205;
    private final static int SCIENCE_MINITES_INDEX = 206;
    private final static int SCIENCE_REMS_INDEX = 207;
    private final static int SCIENCE_APXS_INDEX = 208;
    private final static int SCIENCE_CHEMIN_INDEX = 209;
    private final static int SCIENCE_SAM_INDEX = 210;
    private final static int SCIENCE_RAD_INDEX = 212;
    private final static int SCIENCE_DAN_INDEX = 213;
    private final static int SCIENCE_MB_INDEX = 220;
    private final static int SCIENCE_MI_INDEX = 221;

    private final static int[] CURIOSITY_SCIENCE_INDICES = {SCIENCE_CHEMCAM_INDEX,
            SCIENCE_REMS_INDEX, SCIENCE_MAHLI_INDEX, SCIENCE_APXS_INDEX,
            SCIENCE_CHEMIN_INDEX, SCIENCE_SAM_INDEX, SCIENCE_RAD_INDEX, SCIENCE_DAN_INDEX};

    private static final int[] OPPORTUNITY_SCIENCE_INDICES = {SCIENCE_MINITES_INDEX,
            SCIENCE_MB_INDEX, SCIENCE_APXS_INDEX, SCIENCE_MI_INDEX};

    private static final int[] SPIRIT_SCIENCE_INDICES = {SCIENCE_MINITES_INDEX,
            SCIENCE_MB_INDEX, SCIENCE_APXS_INDEX, SCIENCE_MI_INDEX};


    /**
     * Returns a List of RoverScience objects based on the rover index and the rover category index.
     *
     * @param context       needed to fetch string resources in called methods
     * @param roverIndex    needed to fetch list of available science indices
     * @param roverCatIndex needed to fetch either science or info details
     * @return the list of RoverScience objects
     */

    public static List<RoverScience> getScienceList(Context context,
                                                    @ROVER_INDEX int roverIndex,
                                                    @ROVER_CATEGORY_INDEX int roverCatIndex) {
        int[] indices;
        switch (roverIndex) {
            case CURIOSITY_ROVER_INDEX:
                if (roverCatIndex == ROVER_SCIENCE_CAT_INDEX) {
                    indices = CURIOSITY_SCIENCE_INDICES;
                } else {
                    indices = CURIOSITY_ROVER_INFO_INDICES;
                }
                break;
            case OPPORTUNITY_ROVER_INDEX:
                if (roverCatIndex == ROVER_SCIENCE_CAT_INDEX) {
                    indices = OPPORTUNITY_SCIENCE_INDICES;
                } else {
                    indices = OPPORTUNITY_ROVER_INFO_INDICES;
                }
                break;
            case SPIRIT_ROVER_INDEX:
                if (roverCatIndex == ROVER_SCIENCE_CAT_INDEX) {
                    indices = SPIRIT_SCIENCE_INDICES;
                } else {
                    indices = SPIRIT_ROVER_INFO_INDICES;
                }
                break;
            default:
                indices = new int[0];
        }

        List<RoverScience> roverScienceList = new ArrayList<>();
        for (int index : indices) {
            String text = getScienceText(context, index, roverIndex);
            int imageId;
            if (roverIndex == CURIOSITY_ROVER_INDEX) {
                imageId = getCuriosityScienceImageId(index);
            } else {
                imageId = getOppSpiritScienceImageId(index);
            }
            String title = getScienceTabTitle(context, index);
            roverScienceList.add(new RoverScience(index, title, text, imageId));
        }
        return roverScienceList;
    }

    private static String getScienceTabTitle(Context context, @ALL_SCIENCE_INDEX int scienceIndex) {
        switch (scienceIndex) {
            case SCIENCE_MASTCAM_INDEX:
                return context.getString(R.string.science_mast);
            case SCIENCE_CHEMCAM_INDEX:
                return context.getString(R.string.science_chemcam);
            case SCIENCE_REMS_INDEX:
                return context.getString(R.string.science_rems);
            case SCIENCE_HAZCAM_INDEX:
                return context.getString(R.string.science_hazcam);
            case SCIENCE_NAVCAM_INDEX:
                return context.getString(R.string.science_navcam);
            case SCIENCE_MAHLI_INDEX:
                return context.getString(R.string.science_mahli);
            case SCIENCE_APXS_INDEX:
                return context.getString(R.string.science_apxs);
            case SCIENCE_CHEMIN_INDEX:
                return context.getString(R.string.science_chemin);
            case SCIENCE_SAM_INDEX:
                return context.getString(R.string.science_sam);
            case SCIENCE_DRT_INDEX:
                return context.getString(R.string.science_drt);
            case SCIENCE_RAD_INDEX:
                return context.getString(R.string.science_rad);
            case SCIENCE_DAN_INDEX:
                return context.getString(R.string.science_dan);
            case SCIENCE_MARDI_INDEX:
                return context.getString(R.string.science_mardi);
            case SCIENCE_ROBOTIC_ARM_INDEX:
                return context.getString(R.string.science_arm);
            case SCIENCE_LEGS_WHEELS_INDEX:
                return context.getString(R.string.science_legs);
            case SCIENCE_POWER_INDEX:
                return context.getString(R.string.science_power);
            case SCIENCE_ANTENNA_INDEX:
                return context.getString(R.string.science_antenna);
            case SCIENCE_BRAINS_INDEX:
                return context.getString(R.string.science_brains);
            case SCIENCE_PANCAM_INDEX:
                return context.getString(R.string.science_pancam);
            case SCIENCE_MINITES_INDEX:
                return context.getString(R.string.science_minites);
            case SCIENCE_MB_INDEX:
                return context.getString(R.string.science_mb);
            case SCIENCE_MI_INDEX:
                return context.getString(R.string.science_mi);
            case SCIENCE_RAT_INDEX:
                return context.getString(R.string.science_rat);
            case SCIENCE_MAGNET_INDEX:
                return context.getString(R.string.science_magnet);
            case SCIENCE_SOLAR_INDEX:
                return context.getString(R.string.science_solar);
            case SCIENCE_BODY_INDEX:
                return context.getString(R.string.science_body);
            case SCIENCE_TEMP_INDEX:
                return context.getString(R.string.science_temp);
            default:
                return "";
        }
    }


    private static String getScienceText(Context context, @ALL_SCIENCE_INDEX int scienceIndex,
                                         @ROVER_INDEX int roverIndex) {
        switch (scienceIndex) {
            case SCIENCE_MASTCAM_INDEX:
                return context.getString(R.string.curiosity_mast_camera_details);
            case SCIENCE_CHEMCAM_INDEX:
                return context.getString(R.string.curiosity_chemcam_details);
            case SCIENCE_REMS_INDEX:
                return context.getString(R.string.curiosity_rems_details);
            case SCIENCE_HAZCAM_INDEX:
                return context.getString(R.string.curiosity_hazcam_details);
            case SCIENCE_MAHLI_INDEX:
                return context.getString(R.string.curiosity_mahli_details);
            case SCIENCE_CHEMIN_INDEX:
                return context.getString(R.string.curiosity_chemin_details);
            case SCIENCE_SAM_INDEX:
                return context.getString(R.string.curiosity_sam_details);
            case SCIENCE_DRT_INDEX:
                return context.getString(R.string.curiosity_drt_details);
            case SCIENCE_RAD_INDEX:
                return context.getString(R.string.curiosity_rad_details);
            case SCIENCE_DAN_INDEX:
                return context.getString(R.string.curiosity_dan_details);
            case SCIENCE_MARDI_INDEX:
                return context.getString(R.string.curiosity_mardi_details);
            case SCIENCE_POWER_INDEX:
                return context.getString(R.string.curiosity_power_details);
            case SCIENCE_PANCAM_INDEX:
                return context.getString(R.string.opp_spirit_pancam_details);
            case SCIENCE_MINITES_INDEX:
                return context.getString(R.string.opp_spirit_minites_details);
            case SCIENCE_MB_INDEX:
                return context.getString(R.string.opp_spirit_mb_details);
            case SCIENCE_MI_INDEX:
                return context.getString(R.string.opp_spirit_mi_details);
            case SCIENCE_RAT_INDEX:
                return context.getString(R.string.opp_spirit_rat_details);
            case SCIENCE_MAGNET_INDEX:
                return context.getString(R.string.opp_spirit_magnet_details);
            case SCIENCE_SOLAR_INDEX:
                return context.getString(R.string.opp_spirit_solar_details);
            case SCIENCE_BODY_INDEX:
                return context.getString(R.string.opp_spirit_body_details);
            case SCIENCE_NAVCAM_INDEX:
                if (roverIndex == CURIOSITY_ROVER_INDEX) {
                    return context.getString(R.string.curiosity_navcam_details);
                } else {
                    return context.getString(R.string.opp_spirit_navcam_details);
                }
            case SCIENCE_APXS_INDEX:
                if (roverIndex == CURIOSITY_ROVER_INDEX) {
                    return context.getString(R.string.curiosity_apxs_details);
                } else {
                    return context.getString(R.string.opp_spirit_apxs_details);
                }
            case SCIENCE_ROBOTIC_ARM_INDEX:
                if (roverIndex == CURIOSITY_ROVER_INDEX) {
                    return context.getString(R.string.curiosity_robotic_arm_details);
                } else {
                    return context.getString(R.string.opp_spirit_robotic_arm_details);
                }
            case SCIENCE_LEGS_WHEELS_INDEX:
                if (roverIndex == CURIOSITY_ROVER_INDEX) {
                    return context.getString(R.string.curiosity_legswheels_details);
                } else {
                    return context.getString(R.string.opp_spirit_legswheels_details);
                }
            case SCIENCE_ANTENNA_INDEX:
                if (roverIndex == CURIOSITY_ROVER_INDEX) {
                    return context.getString(R.string.curiosity_antenna_details);
                } else {
                    return context.getString(R.string.opp_spirit_antenna_details);
                }
            case SCIENCE_BRAINS_INDEX:
                if (roverIndex == CURIOSITY_ROVER_INDEX) {
                    return context.getString(R.string.curiosity_brains_details);
                } else {
                    return context.getString(R.string.opp_spirit_brains_details);
                }
            case SCIENCE_TEMP_INDEX:
                if (roverIndex == SPIRIT_ROVER_INDEX) {
                    return context.getString(R.string.spirit_temp_details);
                } else {
                    return context.getString(R.string.opp_temp_details);
                }
        }
        return null;
    }

    //get image resId for science instrument or rover part info
    private static int getCuriosityScienceImageId(@CURIOSITY_SCIENCE_INDEX int scienceIndex) {
        switch (scienceIndex) {
            case SCIENCE_MASTCAM_INDEX:
                return R.drawable.curiosity_mastcam;
            case SCIENCE_CHEMCAM_INDEX:
                return R.drawable.curiosity_chemcam;
            case SCIENCE_REMS_INDEX:
                return R.drawable.curiosity_rems;
            case SCIENCE_HAZCAM_INDEX:
                return R.drawable.curiosity_hazcams;
            case SCIENCE_NAVCAM_INDEX:
                return R.drawable.curiosity_navcam;
            case SCIENCE_MAHLI_INDEX:
                return R.drawable.curiosity_apxs_mahli;
            case SCIENCE_APXS_INDEX:
                return R.drawable.curiosity_apxs_mahli;
            case SCIENCE_CHEMIN_INDEX:
                return R.drawable.curiosity_chemin;
            case SCIENCE_SAM_INDEX:
                return R.drawable.curiosity_sam;
            case SCIENCE_DRT_INDEX:
                return R.drawable.curiosity_drt;
            case SCIENCE_RAD_INDEX:
                return R.drawable.curiosity_rad;
            case SCIENCE_DAN_INDEX:
                return R.drawable.curiosity_dan;
            case SCIENCE_MARDI_INDEX:
                return R.drawable.curiosity_mardi;
            case SCIENCE_ROBOTIC_ARM_INDEX:
                return R.drawable.curiosity_arm;
            case SCIENCE_LEGS_WHEELS_INDEX:
                return R.drawable.curiosity_body;
            case SCIENCE_POWER_INDEX:
                return R.drawable.curiosity_power;
            case SCIENCE_ANTENNA_INDEX:
                return R.drawable.curiosity_antenna;
            case SCIENCE_BRAINS_INDEX:
                return R.drawable.curiosity_body;
            default:
                return R.drawable.curiosity_selfie;
        }
    }

    //get image resId for science instrument or rover part info
    private static int getOppSpiritScienceImageId(@OPP_SPIRIT_SCIENCE_INDEX int scienceIndex) {
        switch (scienceIndex) {
            case SCIENCE_PANCAM_INDEX:
                return R.drawable.opp_spirit_pancam;
            case SCIENCE_NAVCAM_INDEX:
                return R.drawable.opp_spirit_navcam;
            case SCIENCE_MINITES_INDEX:
                return R.drawable.opp_spirit_minites;
            case SCIENCE_MB_INDEX:
            case SCIENCE_MI_INDEX:
            case SCIENCE_APXS_INDEX:
            case SCIENCE_RAT_INDEX:
                return R.drawable.opp_spirit_mb_mi_apxs_rat;
            case SCIENCE_MAGNET_INDEX:
                return R.drawable.opp_spirit_magnets;
            case SCIENCE_SOLAR_INDEX:
                return R.drawable.opp_spirit_solar;
            case SCIENCE_BODY_INDEX:
            case SCIENCE_TEMP_INDEX:
            case SCIENCE_BRAINS_INDEX:
                return R.drawable.opp_spirit_body_temp;
            case SCIENCE_ROBOTIC_ARM_INDEX:
                return R.drawable.opp_spirit_arm;
            case SCIENCE_LEGS_WHEELS_INDEX:
                return R.drawable.opp_spirit_legs_wheels;
            case SCIENCE_ANTENNA_INDEX:
                return R.drawable.opp_spirit_antenna;
            default:
                return R.drawable.opp_spirit_main;
        }
    }


    public static String getRoverNameByIndex(Context context, @ROVER_INDEX int roverIndex) {

        switch (roverIndex) {
            case CURIOSITY_ROVER_INDEX:
                return context.getResources().getString(R.string.curiosity_rover);
            case OPPORTUNITY_ROVER_INDEX:
                return context.getResources().getString(R.string.opportunity_rover);
            case SPIRIT_ROVER_INDEX:
                return context.getResources().getString(R.string.spirit_rover);
            case INSIGHT_LANDER_INDEX:
                return context.getString(R.string.insight_lander_title);
            case PERSEVERANCE_ROVER_INDEX:
                return context.getString(R.string.perseverance_rover_title);
            default:
                return "";
        }
    }


    public static List<ExploreCategory> getExploreCategories(Context context) {
        return setupCategories(context, MARS_EXPLORE_CATEGORIES, MARS_EXPLORE_INDEX);
    }

    public static List<ExploreCategory> getRoverCategories(Context context, @EXPLORE_INDEX int exploreIndex) {
        //set up categories for selected rover
        int[] categories;
        switch (exploreIndex) {
            case MARS_EXPLORE_INDEX:
                categories = MARS_EXPLORE_CATEGORIES;
                break;
            case CURIOSITY_ROVER_INDEX:
                categories = CURIOSITY_CATEGORIES;
                break;
            case OPPORTUNITY_ROVER_INDEX:
                categories = OPPORTUNITY_CATEGORIES;
                break;
            case SPIRIT_ROVER_INDEX:
                categories = SPIRIT_CATEGORIES;
                break;
            case INSIGHT_LANDER_INDEX:
                categories = INSIGHT_CATEGORIES;
                break;
            case PERSEVERANCE_ROVER_INDEX:
                categories = PERSEVERANCE_CATEGORIES;
                break;
            default:
                return null;
        }
        //return list of categories.
        return setupCategories(context, categories, exploreIndex);
    }


    private static List<ExploreCategory> setupCategories(Context context, int[] categories, @EXPLORE_INDEX int exploreIndex) {
        List<ExploreCategory> exploreCategoriesList = new ArrayList<>();
        for (int category : categories) {
            String title = getCategoryTitle(context, category);
            int imageResId = getCategoryImgResId(context, exploreIndex, category);
            //use title as content description when creating new ExploreCategory
            exploreCategoriesList.add(new ExploreCategory(title, imageResId, category, title));
        }
        return exploreCategoriesList;
    }

    private static String getCategoryTitle(Context context, int categoryIndex) {
        switch (categoryIndex) {
            case MARS_WEATHER_CAT_INDEX:
                return context.getString(R.string.mars_weather_category_title);
            case MARS_FACTS_CAT_INDEX:
                return context.getString(R.string.mars_facts_category_title);
            case MARS_FAVORITES_CAT_INDEX:
                return context.getString(R.string.mars_user_favorites);
            case ROVER_PICTURES_CAT_INDEX:
                return context.getString(R.string.rover_picture_category_title);
            case ROVER_INFO_CAT_INDEX:
                return context.getString(R.string.rover_information_category_title);
            case ROVER_SCIENCE_CAT_INDEX:
                return context.getString(R.string.rover_science_category_title);
            case ROVER_TWEETS_CAT_INDEX:
                return context.getString(R.string.rover_tweets_category_title);
            default:
                return "";
        }

    }


    private static int getCategoryImgResId(Context context, @EXPLORE_INDEX int exploreIndex,
                                           @EXPLORE_CATEGORY_INDEX int categoryIndex) {
        // Images will have the format = CATEGORY_INDEX
        //ex. Photo category for Curiosity will be 'photos_1'
        String exploreIndexString = String.valueOf(exploreIndex);
        String resourcePrefix;
        switch (categoryIndex) {
            case MARS_WEATHER_CAT_INDEX:
                resourcePrefix = context.getString(R.string.mars_weather_res_prefix);
                break;
            case MARS_FACTS_CAT_INDEX:
                resourcePrefix = context.getString(R.string.mars_facts_res_prefix);
                break;
            case MARS_FAVORITES_CAT_INDEX:
                resourcePrefix = context.getString(R.string.mars_favorites_res_prefix);
                exploreIndexString = String.valueOf(MARS_FAVORITES_CAT_INDEX);
                break;
            case ROVER_PICTURES_CAT_INDEX:
                resourcePrefix = context.getString(R.string.photos_category_res_prefix);
                break;
            case ROVER_INFO_CAT_INDEX:
                resourcePrefix = context.getString(R.string.info_category_res_prefix);
                break;
            case ROVER_SCIENCE_CAT_INDEX:
                resourcePrefix = context.getString(R.string.science_category_res_prefix);
                break;
            case ROVER_TWEETS_CAT_INDEX:
                resourcePrefix = context.getString(R.string.tweets_category_res_prefix);
                break;
            default:
                resourcePrefix = context.getString(R.string.photos_category_res_prefix);
        }
        String resName = resourcePrefix + exploreIndexString;
        return context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
    }


    public static String getMainExploreOptionTitle(Context context, @EXPLORE_INDEX int exploreIndex) {
        String mainExploreTitle = "";
        String roverString = context.getString(R.string.rover_string);
        switch (exploreIndex) {
            case MARS_EXPLORE_INDEX:
                mainExploreTitle = context.getString(R.string.explore_mars);
                break;
            case CURIOSITY_ROVER_INDEX:
                mainExploreTitle = context.getString(R.string.curiosity_rover)  + " " + roverString;;
                break;
            case OPPORTUNITY_ROVER_INDEX:
                mainExploreTitle = context.getString(R.string.opportunity_rover)  + " " + roverString;;
                break;
            case SPIRIT_ROVER_INDEX:
                mainExploreTitle = context.getString(R.string.spirit_rover)  + " " + roverString;;
                break;
            case INSIGHT_LANDER_INDEX:
                mainExploreTitle = context.getString(R.string.insight_lander_title);
                break;
            case PERSEVERANCE_ROVER_INDEX:
                mainExploreTitle = context.getString(R.string.perseverance_rover_title) + " " + roverString;
            default:
                break;
        }
        return mainExploreTitle;
    }


    public static String getWeatherLabel(Context context, @WEATHER_INDEX int weatherIndex) {
        switch (weatherIndex) {
            case WEATHER_MIN_TEMP_INDEX:
                return context.getString(R.string.weather_min_air_temp);
            case WEATHER_MAX_TEMP_INDEX:
                return context.getString(R.string.weather_max_air_temp);
            case WEATHER_ATMO_INDEX:
                return context.getString(R.string.weather_atmo);
            case WEATHER_SUNSET_INDEX:
                return context.getString(R.string.weather_sunset);
            case WEATHER_SUNRISE_INDEX:
                return context.getString(R.string.weather_sunrise);
            case WEATHER_MIN_GRND_TMP_INDEX:
                return context.getString(R.string.weather_min_ground_temp);
            case WEATHER_MAX_GRND_TMP_INDEX:
                return context.getString(R.string.weather_max_ground_temp);
            default:
                return context.getString(R.string.unknown);

        }
    }

    public static int getWeatherInfoIndex(@WEATHER_INDEX int weatherIndex) {
        switch (weatherIndex) {
            case WEATHER_MIN_TEMP_INDEX:
            case WEATHER_MAX_TEMP_INDEX:
                return InformationUtils.AIR_TEMP_INFO;
            case WEATHER_MIN_GRND_TMP_INDEX:
            case WEATHER_MAX_GRND_TMP_INDEX:
                return InformationUtils.GROUND_TEMP_INFO;
            case WEATHER_ATMO_INDEX:
                return InformationUtils.ATMO_INFO;
            case WEATHER_SUNRISE_INDEX:
            case WEATHER_SUNSET_INDEX:
                return InformationUtils.SUNRISE_SUNSET_INFO;
            default:
                return InformationUtils.ERROR_LOADING_INFO;
        }
    }

    public static String capitalizeFirstLetter(String text) {
        if(text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();

    }


    // Setup Main Explore Options Below
    public static List<MainExploreType> getAllExploreTypes(Context context) {
        List<MainExploreType> mainExploreTypes = new ArrayList<>();
        mainExploreTypes.add(createPerseveranceExploreType(context));
        mainExploreTypes.add(createMarsExploreType(context));
        mainExploreTypes.add(createInsightExploreType(context));
        mainExploreTypes.add(createCuriosityExploreType(context));
        mainExploreTypes.add(createOpportunityExploreType(context));
        mainExploreTypes.add(createSpiritExploreType(context));
        return mainExploreTypes;
    }

    private static MainExploreType createMarsExploreType(Context context) {
        return new MainExploreType(MARS_EXPLORE_INDEX,
                getMainExploreOptionTitle(context, MARS_EXPLORE_INDEX), R.drawable.explore_main, MARS_EXPLORE_SORT_INDEX);
    }

    private static MainExploreType createCuriosityExploreType(Context context) {
        return new MainExploreType(CURIOSITY_ROVER_INDEX,
                getMainExploreOptionTitle(context, CURIOSITY_ROVER_INDEX), R.drawable.curiosity_selfie, CURIOSITY_SORT_INDEX);
    }

    private static MainExploreType createOpportunityExploreType(Context context) {
        return new MainExploreType(OPPORTUNITY_ROVER_INDEX,
                getMainExploreOptionTitle(context, OPPORTUNITY_ROVER_INDEX), R.drawable.opp_spirit_main, OPPORTUNITY_SORT_INDEX);
    }

    private static MainExploreType createSpiritExploreType(Context context) {
        return new MainExploreType(SPIRIT_ROVER_INDEX,
                getMainExploreOptionTitle(context, SPIRIT_ROVER_INDEX), R.drawable.opp_spirit_main, SPIRIT_SORT_INDEX);
    }

    private static MainExploreType createInsightExploreType(Context context) {
        return new MainExploreType(INSIGHT_LANDER_INDEX,
                getMainExploreOptionTitle(context, INSIGHT_LANDER_INDEX), R.drawable.insight_selfie, INSIGHT_SORT_INDEX);
    }

    private static MainExploreType createPerseveranceExploreType(Context context) {
        return new MainExploreType(PERSEVERANCE_ROVER_INDEX,
                getMainExploreOptionTitle(context, PERSEVERANCE_ROVER_INDEX), R.drawable.perseverance_image, PERSEVERANCE_SORT_INDEX);
    }

}
