package com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos;


import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.curtisgetz.marsexplorer.data.Cameras;
import com.curtisgetz.marsexplorer.data.MarsRepository;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.text.BreakIterator;
import java.util.List;

/**
 * View Model for list of camera urls
 */
class CamerasViewModel extends ViewModel {
    /**
     * LiveData wrapped Cameras object
     */
    private LiveData<Cameras> mCameras;
    /**
     * Reference to Mars Repository
     */
    @SuppressWarnings("FieldCanBeLocal")
    private MarsRepository mRepository;


    CamerasViewModel(Application application, int roverIndex, String solNumber) {
        mRepository = MarsRepository.getInstance(application);
        mCameras = mRepository.getCameras(application.getApplicationContext(), roverIndex, solNumber);
    }

    LiveData<Cameras> getCameras() {
        return mCameras;
    }

    /**
     * Get a List of urls for the specified camera
     *
     * @param cameraIndex index of camera to get images from
     * @return List of urls (if any)
     */
    List<String> getImageUrlsForCamera(@HelperUtils.CAMERA_INDEX int cameraIndex) {
        // if there are no images for the camera, return null.
        // Otherwise return a List of Strings (the urls)
        Cameras cameras = mCameras.getValue();
        // Attempt to get closest sol with images if this sol has no images

        if (cameras == null || !(cameras.isCameraActive(cameraIndex))) return null;
        switch (cameraIndex) {
            case HelperUtils.CAM_FHAZ_INDEX:
                return cameras.getFHAZ();
            case HelperUtils.CAM_RHAZ_INDEX:
                return cameras.getRHAZ();
            case HelperUtils.CAM_MAST_INDEX:
                return cameras.getMAST();
            case HelperUtils.CAM_CHEMCAM_INDEX:
                return cameras.getCHEMCAM();
            case HelperUtils.CAM_MAHLI_INDEX:
                return cameras.getMAHLI();
            case HelperUtils.CAM_MARDI_INDEX:
                return cameras.getMARDI();
            case HelperUtils.CAM_NAVCAM_INDEX:
                return cameras.getNAVCAM();
            case HelperUtils.CAM_PANCAM_INDEX:
                return cameras.getPANCAM();
            case HelperUtils.CAM_MINITES_INDEX:
                return cameras.getMINITES();
            case HelperUtils.CAM_IDC_INDEX:
                return cameras.getIDC();
            case HelperUtils.CAM_ICC_INDEX:
                return cameras.getICC();
            case HelperUtils.CAM_EDL_RUCAM_INDEX:
                return cameras.getmRUCAM();
            case HelperUtils.CAM_EDL_DDCAM_INDEX:
                return cameras.getmDDCAM();
            case HelperUtils.CAM_EDL_PUCAM1_INDEX:
                return cameras.getmPUCAM1();
            case HelperUtils.CAM_EDL_PUCAM2_INDEX:
                return cameras.getmPUCAM2();
            case HelperUtils.CAM_EDL_RDCAM_INDEX:
                return cameras.getmRDCAM();
            case HelperUtils.CAM_FRONT_HAZCAM_LEFT_A_INDEX:
                return cameras.getmFHAZLEFT();
            case HelperUtils.CAM_FRONT_HAZCAM_RIGHT_A_INDEX:
                return cameras.getmFHAZRIGHT();
            case HelperUtils.CAM_MCZ_LEFT_INDEX:
                return cameras.getmMCZLEFT();
            case HelperUtils.CAM_MCZ_RIGHT_INDEX:
                return cameras.getmMCZRIGHT();
            case HelperUtils.CAM_NAVCAM_LEFT_INDEX:
                return cameras.getmNAVLEFT();
            case HelperUtils.CAM_NAVCAM_RIGHT_INDEX:
                return cameras.getmNAVRIGHT();
            case HelperUtils.CAM_REAR_HAZCAM_LEFT_INDEX:
                return cameras.getmRHAZLEFT();
            case HelperUtils.CAM_REAR_HAZCAM_RIGHT_INDEX:
                return cameras.getmRHAZRIGHT();
            case HelperUtils.CAM_SKYCAM_INDEX:
                return cameras.getmSKYCAM();
            case HelperUtils.CAM_SHERLOC_INDEX:
                return cameras.getmSHERLOC();
            default:
                return null;
        }
    }

}
