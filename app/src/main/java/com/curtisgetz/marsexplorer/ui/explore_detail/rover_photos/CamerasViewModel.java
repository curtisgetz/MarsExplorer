package com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.curtisgetz.marsexplorer.data.Cameras;
import com.curtisgetz.marsexplorer.data.MarsRepository;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.List;

public class CamerasViewModel extends  ViewModel {


    private LiveData<Cameras> mCameras;
    private MarsRepository mRepository;


    CamerasViewModel(Application application, int roverIndex, String solNumber) {
        mRepository = MarsRepository.getInstance(application);
        mCameras = mRepository.getCameras(application.getApplicationContext(), roverIndex, solNumber);
    }

    public LiveData<Cameras> getCameras(){
        return mCameras;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public List<String> getImageUrlsForCamera(int cameraIndex){
        // if there are no images for the camera, return null.
        // Otherwise return a List of Strings (the urls)
        Cameras cameras = mCameras.getValue();
        if(cameras == null || !(cameras.isCameraActive(cameraIndex))) return null;
        switch (cameraIndex){
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
            default:
                return null;
        }
    }


}
