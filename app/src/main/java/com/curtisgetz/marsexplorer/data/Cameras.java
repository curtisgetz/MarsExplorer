/**
 * Created by Curtis Getz on 2/19/2018.
 */

package com.curtisgetz.marsexplorer.data;


import androidx.annotation.NonNull;

import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Object for holding Lists of urls for each camera on a rover
 */

public class Cameras {

    private final static String TAG = Cameras.class.getSimpleName();

    private int mRoverIndex;
    @NonNull
    private List<String> mFHAZ = new ArrayList<>();
    @NonNull
    private List<String> mRHAZ = new ArrayList<>();
    @NonNull
    private List<String> mNAVCAM = new ArrayList<>();
    @NonNull
    private List<String> mMAST = new ArrayList<>();
    @NonNull
    private List<String> mCHEMCAM = new ArrayList<>();
    @NonNull
    private List<String> mMAHLI = new ArrayList<>();
    @NonNull
    private List<String> mMARDI = new ArrayList<>();
    @NonNull
    private List<String> mPANCAM = new ArrayList<>();
    @NonNull
    private List<String> mMINITES = new ArrayList<>();
    @NonNull
    private List<String> mIDC = new ArrayList<>();
    @NonNull
    private List<String> mICC = new ArrayList<>();
    @NonNull
    private  List<String> mRUCAM = new ArrayList<>();
    @NonNull
    private  List<String> mRDCAM = new ArrayList<>();
    @NonNull
    private List<String> mDDCAM = new ArrayList<>();
    @NonNull
    private List<String> mPUCAM1 = new ArrayList<>();
    @NonNull
    private List<String> mPUCAM2 = new ArrayList<>();
    @NonNull
    private List<String> mNAVLEFT = new ArrayList<>();
    @NonNull
    private List<String> mNAVRIGHT = new ArrayList<>();
    @NonNull
    private List<String> mMCZRIGHT = new ArrayList<>();
    @NonNull
    private List<String> mMCZLEFT = new ArrayList<>();
    @NonNull
    private List<String> mFHAZLEFT = new ArrayList<>();
    @NonNull
    private List<String> mFHAZRIGHT = new ArrayList<>();
    @NonNull
    private List<String> mRHAZRIGHT = new ArrayList<>();
    @NonNull
    private List<String> mRHAZLEFT = new ArrayList<>();
    @NonNull
    private List<String> mSKYCAM = new ArrayList<>();
    @NonNull
    private List<String> mSHERLOC = new ArrayList<>();


    @NonNull
    private String mEarthDate;

    private String mSol;

    /**
     * Constructor for Insight Lander
     * @param index rover index
     * @param idc List of IDC Urls
     * @param icc List of ICC Urls
     * @param earthDate Earth date as a String
     * @param sol Sol as a String
     */

    public Cameras(int index, @NonNull List<String> idc, @NonNull List<String> icc, @NonNull String earthDate, String sol){
        mRoverIndex = index;

        mIDC = idc;
        mICC = icc;
        mEarthDate = earthDate;
        mSol = sol;
    }


    public Cameras(int index, @NonNull List<String> fhaz, @NonNull List<String> rhaz,
                   @NonNull List<String> navcam, @NonNull List<String> mast, @NonNull List<String> chemcam,
                   @NonNull List<String> mahli, @NonNull List<String> mardi, @NonNull List<String> pancam,
                   @NonNull List<String> minites, @NonNull List<String> rucam, @NonNull List<String> rdcam,
                   @NonNull List<String> ddcam, @NonNull List<String> pucam1, @NonNull List<String> pucam2,
                   @NonNull List<String> navcamleft, @NonNull List<String> navcamright,
                   @NonNull List<String> mczleft, @NonNull List<String> mczright,
                   @NonNull List<String> fhazleft, @NonNull List<String> fhazright, @NonNull List<String> rhazleft,
                   @NonNull List<String> rhazright, @NonNull List<String> skycam, @NonNull List<String> sherloc,
                   @NonNull String earthDate, String sol ) {
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

        mRUCAM = rucam;
        mRDCAM = rdcam;
        mDDCAM = ddcam;
        mPUCAM1 = pucam1;
        mPUCAM2 = pucam2;
        mNAVLEFT = navcamleft;
        mNAVRIGHT = navcamright;
        mMCZRIGHT = mczright;
        mMCZLEFT = mczleft;
        mFHAZLEFT = fhazleft;
        mFHAZRIGHT = fhazright;
        mRHAZRIGHT = rhazright;
        mRHAZLEFT = rhazleft;
        mSKYCAM = skycam;
        mSHERLOC = sherloc;

        mEarthDate = earthDate;
        mSol = sol;
    }

    @NonNull
    public List<String> getIDC() {
        return mIDC;
    }

    public void setIDC(@NonNull List<String> mIDC) {
        this.mIDC = mIDC;
    }

    @NonNull
    public List<String> getICC() {
        return mICC;
    }

    public void setICC(@NonNull List<String> mICC) {
        this.mICC = mICC;
    }

    public List<String> getFHAZ() {
        return mFHAZ;
    }

    public List<String> getRHAZ() {
        return mRHAZ;
    }

    public List<String> getNAVCAM() {
        return mNAVCAM;
    }

    public List<String> getMAST() {
        return mMAST;
    }

    public List<String> getCHEMCAM() {
        return mCHEMCAM;
    }

    public List<String> getMAHLI() {
        return mMAHLI;
    }

    public List<String> getMARDI() {
        return mMARDI;
    }

    public List<String> getPANCAM() {
        return mPANCAM;
    }

    public List<String> getMINITES() {
        return mMINITES;
    }

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

    @NonNull
    public List<String> getmRUCAM() {
        return mRUCAM;
    }

    public void setmRUCAM(@NonNull List<String> mRUCAM) {
        this.mRUCAM = mRUCAM;
    }

    @NonNull
    public List<String> getmRDCAM() {
        return mRDCAM;
    }

    public void setmRDCAM(@NonNull List<String> mRDCAM) {
        this.mRDCAM = mRDCAM;
    }

    @NonNull
    public List<String> getmDDCAM() {
        return mDDCAM;
    }

    public void setmDDCAM(@NonNull List<String> mDDCAM) {
        this.mDDCAM = mDDCAM;
    }

    @NonNull
    public List<String> getmPUCAM1() {
        return mPUCAM1;
    }

    public void setmPUCAM1(@NonNull List<String> mPUCAM1) {
        this.mPUCAM1 = mPUCAM1;
    }

    @NonNull
    public List<String> getmPUCAM2() {
        return mPUCAM2;
    }

    public void setmPUCAM2(@NonNull List<String> mPUCAM2) {
        this.mPUCAM2 = mPUCAM2;
    }

    @NonNull
    public List<String> getmNAVLEFT() {
        return mNAVLEFT;
    }

    public void setmNAVLEFT(@NonNull List<String> mNAVLEFT) {
        this.mNAVLEFT = mNAVLEFT;
    }

    @NonNull
    public List<String> getmNAVRIGHT() {
        return mNAVRIGHT;
    }

    public void setmNAVRIGHT(@NonNull List<String> mNAVRIGHT) {
        this.mNAVRIGHT = mNAVRIGHT;
    }

    @NonNull
    public List<String> getmMCZRIGHT() {
        return mMCZRIGHT;
    }

    public void setmMCZRIGHT(@NonNull List<String> mMCZRIGHT) {
        this.mMCZRIGHT = mMCZRIGHT;
    }

    @NonNull
    public List<String> getmMCZLEFT() {
        return mMCZLEFT;
    }

    public void setmMCZLEFT(@NonNull List<String> mMCZLEFT) {
        this.mMCZLEFT = mMCZLEFT;
    }

    @NonNull
    public List<String> getmFHAZLEFT() {
        return mFHAZLEFT;
    }

    public void setmFHAZLEFT(@NonNull List<String> mFHAZLEFT) {
        this.mFHAZLEFT = mFHAZLEFT;
    }

    @NonNull
    public List<String> getmFHAZRIGHT() {
        return mFHAZRIGHT;
    }

    public void setmFHAZRIGHT(@NonNull List<String> mFHAZRIGHT) {
        this.mFHAZRIGHT = mFHAZRIGHT;
    }

    @NonNull
    public List<String> getmRHAZRIGHT() {
        return mRHAZRIGHT;
    }

    public void setmRHAZRIGHT(@NonNull List<String> mRHAZRIGHT) {
        this.mRHAZRIGHT = mRHAZRIGHT;
    }

    @NonNull
    public List<String> getmRHAZLEFT() {
        return mRHAZLEFT;
    }

    public void setmRHAZLEFT(@NonNull List<String> mRHAZLEFT) {
        this.mRHAZLEFT = mRHAZLEFT;
    }

    @NonNull
    public List<String> getmSKYCAM() {
        return mSKYCAM;
    }

    public void setmSKYCAM(@NonNull List<String> mSKYCAM) {
        this.mSKYCAM = mSKYCAM;
    }

    @NonNull
    public List<String> getmSHERLOC() {
        return mSHERLOC;
    }

    public void setmSHERLOC(@NonNull List<String> mSHERLOC) {
        this.mSHERLOC = mSHERLOC;
    }

    /**
     * Check if a camera has any photos
     *
     * @param cameraIndex index of camera. Used to check each camera for photos
     * @return true if camera has any photo urls. false if camera
     */
    public boolean isCameraActive(@HelperUtils.CAMERA_INDEX int cameraIndex) {
        //if list has a size greater than 0, the camera has images and is considered 'active'
        switch (cameraIndex) {
            case HelperUtils.CAM_FHAZ_INDEX:
                return (mFHAZ.size() > 0);
            case HelperUtils.CAM_RHAZ_INDEX:
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
            case HelperUtils.CAM_IDC_INDEX:
                return (mIDC.size() > 0);
            case HelperUtils.CAM_ICC_INDEX:
                return (mICC.size() > 0);
            case HelperUtils.CAM_EDL_DDCAM_INDEX:
                return  (mDDCAM.size() > 0);
            case HelperUtils.CAM_EDL_PUCAM1_INDEX:
                return (mPUCAM1.size() > 0);
            case HelperUtils.CAM_EDL_PUCAM2_INDEX:
                return (mPUCAM2.size() > 0);
            case HelperUtils.CAM_EDL_RDCAM_INDEX:
                return (mRDCAM.size() > 0);
            case HelperUtils.CAM_EDL_RUCAM_INDEX:
                return (mRUCAM.size() > 0);
            case HelperUtils.CAM_FRONT_HAZCAM_LEFT_A_INDEX:
                return (mFHAZLEFT.size() > 0);
            case HelperUtils.CAM_FRONT_HAZCAM_RIGHT_A_INDEX:
                return (mRHAZRIGHT.size() > 0);
            case HelperUtils.CAM_MCZ_LEFT_INDEX:
                return (mMCZLEFT.size() > 0);
            case HelperUtils.CAM_MCZ_RIGHT_INDEX:
                return (mMCZRIGHT.size() > 0);
            case HelperUtils.CAM_NAVCAM_LEFT_INDEX:
                return (mNAVLEFT.size() > 0);
            case HelperUtils.CAM_NAVCAM_RIGHT_INDEX:
                return (mNAVRIGHT.size() > 0);
            case HelperUtils.CAM_REAR_HAZCAM_LEFT_INDEX:
                return (mRHAZLEFT.size() > 0);
            case HelperUtils.CAM_REAR_HAZCAM_RIGHT_INDEX:
                return (mRHAZRIGHT.size() > 0);
            case HelperUtils.CAM_SKYCAM_INDEX:
                return (mSKYCAM.size() > 0);
            case HelperUtils.CAM_SHERLOC_INDEX:
                return (mSHERLOC.size() > 0);
            default:
                return false;
        }
    }

    public String getSol() {
        return mSol;
    }

    public void setSol(String sol) {
        this.mSol = sol;
    }
}