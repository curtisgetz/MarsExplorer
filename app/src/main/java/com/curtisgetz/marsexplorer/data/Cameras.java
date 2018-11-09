/**
 * Created by Curtis Getz on 2/19/2018.
 */

package com.curtisgetz.marsexplorer.data;


import android.support.annotation.NonNull;
import android.util.Log;

import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.List;


/**
 * Object for holding Lists of urls for each camera on a rover
 */

public class Cameras {

    private final static String TAG = Cameras.class.getSimpleName();

    private int mRoverIndex;
    @NonNull
    private List<String> mFHAZ;
    @NonNull
    private List<String> mRHAZ;
    @NonNull
    private List<String> mNAVCAM;
    @NonNull
    private List<String> mMAST;
    @NonNull
    private List<String> mCHEMCAM;
    @NonNull
    private List<String> mMAHLI;
    @NonNull
    private List<String> mMARDI;
    @NonNull
    private List<String> mPANCAM;
    @NonNull
    private List<String> mMINITES;
    @NonNull
    private String mEarthDate;

    public Cameras(int index, @NonNull List<String> fhaz, @NonNull List<String> rhaz,
                   @NonNull List<String> navcam, @NonNull List<String> mast,
                   @NonNull List<String> chemcam, @NonNull List<String> mahli,
                   @NonNull List<String> mardi, @NonNull List<String> pancam,
                   @NonNull List<String> minites, @NonNull String earthDate){
        mRoverIndex = index;
        mFHAZ = fhaz;
        mRHAZ = rhaz;
        mNAVCAM = navcam;

        mMAST = mast;
        mCHEMCAM = chemcam;
        mMAHLI = mahli;
        mMARDI = mardi;

        mPANCAM = pancam;
        mMINITES = minites;

        mEarthDate = earthDate;
    }


    public List<String> getFHAZ() { return mFHAZ;}
    public List<String> getRHAZ() {return mRHAZ;}
    public List<String> getNAVCAM() {return mNAVCAM;}

    public List<String> getMAST() {return mMAST;}
    public List<String> getCHEMCAM() {return mCHEMCAM;}
    public List<String> getMAHLI() {return mMAHLI;}
    public List<String> getMARDI() {return mMARDI;}

    public List<String> getPANCAM() {return mPANCAM;}
    public List<String> getMINITES() {return mMINITES;}

    public String getEarthDate() {
        return mEarthDate;
    }

    public int getRoverIndex() {
        return mRoverIndex;
    }

    public void setRoverIndex(int mRoverIndex) {
        this.mRoverIndex = mRoverIndex;
    }

    public void setmFHAZ(@NonNull List<String> mFHAZ) {
        this.mFHAZ = mFHAZ;
    }

    public void setmRHAZ(@NonNull List<String> mRHAZ) {
        this.mRHAZ = mRHAZ;
    }

    public void setmNAVCAM(@NonNull List<String> mNAVCAM) {
        this.mNAVCAM = mNAVCAM;
    }

    public void setmMAST(@NonNull List<String> mMAST) {
        this.mMAST = mMAST;
    }

    public void setmCHEMCAM(@NonNull List<String> mCHEMCAM) {
        this.mCHEMCAM = mCHEMCAM;
    }

    public void setmMAHLI(@NonNull List<String> mMAHLI) {
        this.mMAHLI = mMAHLI;
    }

    public void setmMARDI(@NonNull List<String> mMARDI) {
        this.mMARDI = mMARDI;
    }

    public void setmPANCAM(@NonNull List<String> mPANCAM) {
        this.mPANCAM = mPANCAM;
    }

    public void setmMINITES(@NonNull List<String> mMINITES) {
        this.mMINITES = mMINITES;
    }

    public void setEarthDate(@NonNull String mEarthDate) {
        this.mEarthDate = mEarthDate;
    }

    /**
     * Check if a camera has any photos
     * @param cameraIndex index of camera. Used to check each camera for photos
     * @return true if camera has any photo urls. false if camera
     */
    public boolean isCameraActive(int cameraIndex){
        //if list has a size greater than 0, the camera has images and is considered 'active'
        switch (cameraIndex){
            case HelperUtils.CAM_FHAZ_INDEX:
                return (mFHAZ.size() > 0);
            case HelperUtils.CAM_RHAZ_INDEX:
                Log.d(TAG, String.valueOf(mRHAZ.size()));
                return (mRHAZ.size() > 0);
            case HelperUtils.CAM_MAST_INDEX:
                return (mMAST.size() > 0);
            case HelperUtils.CAM_CHEMCAM_INDEX:
                return (mCHEMCAM.size() > 0);
            case HelperUtils.CAM_MAHLI_INDEX:
                return (mMAHLI.size() > 0);
            case HelperUtils.CAM_MARDI_INDEX:
                return (mMARDI.size() > 0);
            case HelperUtils.CAM_NAVCAM_INDEX:
                return (mNAVCAM.size() > 0);
            case HelperUtils.CAM_PANCAM_INDEX:
                return (mPANCAM.size() > 0);
            case HelperUtils.CAM_MINITES_INDEX:
                return (mMINITES.size() > 0);
            default:
                return false;
        }
    }

}