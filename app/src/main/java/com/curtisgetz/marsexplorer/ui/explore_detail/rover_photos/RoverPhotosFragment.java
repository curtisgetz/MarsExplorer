package com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.Cameras;
import com.curtisgetz.marsexplorer.ui.info.InfoDialogFragment;
import com.curtisgetz.marsexplorer.ui.settings.SettingsActivity;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.InformationUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoverPhotosFragment extends Fragment implements RoverPhotosAdapter.PhotoClickListener {

    //TODO REWRITE THIS FRAGMENT TO REDUCE CODE along with layout. Replace Butterknife with Android Veiw Binding
    public static int SEARCH_BY_DATE = 0;
    public static int SEARCH_BY_SOL = 1;

    private String mSol;
    private int mRoverIndex;
    private CamerasViewModel mViewModel;
    private SolFromDateViewModel mSolDateViewModel;
    private String mDateString;
    private Unbinder mUnBinder;
    private static boolean hasShownPrefSnack = false;

    //RecyclerViews for each camera
    @BindView(R.id.photos_fhaz_recyclerview)
    RecyclerView mFhazRecyclerView;
    @BindView(R.id.photos_rhaz_recyclerview)
    RecyclerView mRhazRecyclerView;
    @BindView(R.id.photos_mast_recyclerview)
    RecyclerView mMastRecyclerView;
    @BindView(R.id.photos_chemcam_recyclerview)
    RecyclerView mChemcamRecyclerView;
    @BindView(R.id.photos_mahli_recyclerview)
    RecyclerView mMahliRecyclerView;
    @BindView(R.id.photos_mardi_recyclerview)
    RecyclerView mMardiRecyclerView;
    @BindView(R.id.photos_navcam_recyclerview)
    RecyclerView mNavcamRecyclerView;
    @BindView(R.id.photos_pancam_recyclerview)
    RecyclerView mPancamRecyclerView;
    @BindView(R.id.photos_minites_recyclerview)
    RecyclerView mMinitesRecyclerView;
    @BindView(R.id.photos_idc_recyclerview)
    RecyclerView mIdcRecyclerView;
    @BindView(R.id.photos_icc_recyclerview)
    RecyclerView mIccRecyclerView;

    @BindView(R.id.photos_rucam_recyclerview)
    RecyclerView mRucamRecyclerView;
    @BindView(R.id.photos_rdcam_recyclerview)
    RecyclerView mRdcamRecyclerView;
    @BindView(R.id.photos_ddcam_recyclerview)
    RecyclerView mDdcamRecyclerView;
    @BindView(R.id.photos_pucam1_recyclerview)
    RecyclerView mPucam1RecyclerView;
    @BindView(R.id.photos_pucam2_recyclerview)
    RecyclerView mPucam2RecyclerView;
    @BindView(R.id.photos_navcam_left_recyclerview)
    RecyclerView mNavleftRecyclerView;
    @BindView(R.id.photos_navcam_right_recyclerview)
    RecyclerView mNavrightRecyclerView;
    @BindView(R.id.photos_mcz_left_recyclerview)
    RecyclerView mMczleftRecyclerView;
    @BindView(R.id.photos_mcz_right_recyclerview)
    RecyclerView mMczrightmRecyclerView;
    @BindView(R.id.photos_fhaz_left_recyclerview)
    RecyclerView mFhazleftRecyclerView;
    @BindView(R.id.photos_fhaz_right_recyclerview)
    RecyclerView mFhazrightRecyclerView;
    @BindView(R.id.photos_rhaz_right_recyclerview)
    RecyclerView mRhazrightRecyclerView;
    @BindView(R.id.photos_rhaz_left_recyclerview)
    RecyclerView mRhazleftRecyclerView;
    @BindView(R.id.photos_skycam_recyclerview)
    RecyclerView mSkycamRecyclerView;
    @BindView(R.id.photos_sherloc_recyclerview)
    RecyclerView mSherlocRecyclerView;



    //TextViews for camera name labels
    @BindView(R.id.photos_fhaz_label)
    TextView mFhazLabel;
    @BindView(R.id.photos_rhaz_label)
    TextView mRhazLabel;
    @BindView(R.id.photos_mast_label)
    TextView mMastLabel;
    @BindView(R.id.photos_chemcam_label)
    TextView mChemcamLabel;
    @BindView(R.id.photos_mahli_label)
    TextView mMahliLabel;
    @BindView(R.id.photos_mardi_label)
    TextView mMardiLabel;
    @BindView(R.id.photos_navcam_label)
    TextView mNavcamLabel;
    @BindView(R.id.photos_pancam_label)
    TextView mPancamLabel;
    @BindView(R.id.photos_minites_label)
    TextView mMinitesLabel;
    @BindView(R.id.photos_idc_label)
    TextView mIdcLabel;
    @BindView(R.id.photos_icc_label)
    TextView mIccLabel;
    @BindView(R.id.photos_rucam_label)
    TextView mRucamLabel;
    @BindView(R.id.photos_rdcam_label)
    TextView mRdcamLabel;
    @BindView(R.id.photos_ddcam_label)
    TextView mDdcamLabel;
    @BindView(R.id.photos_pucam1_label)
    TextView mPucam1Label;
    @BindView(R.id.photos_pucam2_label)
    TextView mPucam2Label;
    @BindView(R.id.photos_navcam_left_label)
    TextView mNavcamleftLabel;
    @BindView(R.id.photos_navcam_right_label)
    TextView mNavcamrightLabel;
    @BindView(R.id.photos_mcz_left_label)
    TextView mMczleftLabel;
    @BindView(R.id.photos_mcz_right_label)
    TextView mMcarightLabel;
    @BindView(R.id.photos_fhaz_left_label)
    TextView mFhazleftLabel;
    @BindView(R.id.photos_fhaz_right_label)
    TextView mFhazrightLabel;
    @BindView(R.id.photos_rhaz_left_label)
    TextView mRhazleftLabel;
    @BindView(R.id.photos_rhaz_right_label)
    TextView mRhazrightLabel;
    @BindView(R.id.photos_skycam_label)
    TextView mSkycamLabel;
    @BindView(R.id.photos_sherloc_label)
    TextView mSherlocLabel;






    @BindView(R.id.rover_photos_main_progress)
    ProgressBar mMainProgress;
    @BindView(R.id.rover_photos_title)
    TextView mTitleText;
    @BindView(R.id.rover_photos_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    private RoverPhotosAdapter mFhazAdapter;
    private RoverPhotosAdapter mRhazAdapter;
    private RoverPhotosAdapter mMastAdapter;
    private RoverPhotosAdapter mChemcamAdapter;
    private RoverPhotosAdapter mMahliAdapter;
    private RoverPhotosAdapter mMardiAdapter;
    private RoverPhotosAdapter mNavcamAdapter;
    private RoverPhotosAdapter mPancamAdapter;
    private RoverPhotosAdapter mMinitesAdapter;
    private RoverPhotosAdapter mIdcAdapter;
    private RoverPhotosAdapter mIccAdapter;

    private RoverPhotosAdapter mRucamAdapter;
    private RoverPhotosAdapter mRdcamAdapter;
    private RoverPhotosAdapter mDdcamAdapter;
    private RoverPhotosAdapter mPucam1Adapter;
    private RoverPhotosAdapter mPucam2Adapter;
    private RoverPhotosAdapter mNavleftAdapter;
    private RoverPhotosAdapter mNavrightAdapter;
    private RoverPhotosAdapter mMczleftAdapter;
    private RoverPhotosAdapter mMczrightAdapter;
    private RoverPhotosAdapter mFhazleftAdapter;
    private RoverPhotosAdapter mFhazrightAdapter;
    private RoverPhotosAdapter mRhazleftAdapter;
    private RoverPhotosAdapter mRhazrightAdapter;
    private RoverPhotosAdapter mSkycamAdapter;
    private RoverPhotosAdapter mSherlocAdapter;


    public static RoverPhotosFragment newInstance(Context context, int roverIndex, String sol, int searchType, String date) {
        RoverPhotosFragment fragment = new RoverPhotosFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(context.getResources().getString(R.string.rover_index_extra), roverIndex);
        bundle.putString(context.getResources().getString(R.string.sol_number_extra_key), sol);
        bundle.putInt(context.getString(R.string.photo_search_type), searchType);
        bundle.putString(context.getString(R.string.date_extra), date);
        fragment.setArguments(bundle);
        return fragment;
    }

    public RoverPhotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rover_photos, container, false);
        mUnBinder = ButterKnife.bind(this, view);

        shownPhotoLimitDialog();
        displayPrefSnack();
        hideAllCameraViews();
        showMainProgress();

        if (savedInstanceState == null) {
            Bundle bundle = getArguments();

            mRoverIndex = bundle.getInt(getString(R.string.rover_index_extra));
            int searchType = bundle.getInt(getString(R.string.photo_search_type));
            if (searchType == SEARCH_BY_DATE) {
                String date = bundle.getString(getString(R.string.date_extra));
                SolFromDateVMFactory solDateFactory = new SolFromDateVMFactory(getActivity().getApplication(), mRoverIndex, date);
                mSolDateViewModel = ViewModelProviders.of(this, solDateFactory).get(SolFromDateViewModel.class);
                mSolDateViewModel.getSolLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String sol) {
                        if (sol == null) return;
                        setupCameraViewModel(sol);
                    }
                });
            } else {
                mSol = bundle.getString(getString(R.string.sol_number_extra_key));
                setupCameraViewModel(mSol);
            }

        } else {
            mSol = savedInstanceState.getString(getString(R.string.sol_number_saved_key),
                    HelperUtils.DEFAULT_SOL_NUMBER);
            mRoverIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key),
                    HelperUtils.CURIOSITY_ROVER_INDEX);

            setupCameraViewModel(mSol);
        }

        setRoverTitle();


        return view;
    }

    /**
     * Setup View Model for Cameras
     *
     * @param sol the sol to search for pictures
     */
    private void setupCameraViewModel(String sol) {
        mSol = sol;
        CamerasVMFactory factory = new CamerasVMFactory(getActivity().getApplication(), mRoverIndex, sol);
        mViewModel = ViewModelProviders.of(this, factory).get(CamerasViewModel.class);
        mViewModel.getCameras().observe(getViewLifecycleOwner(), new Observer<Cameras>() {
            @Override
            public void onChanged(@Nullable Cameras cameras) {
                setupUI();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.sol_number_saved_key), mSol);
        outState.putInt(getString(R.string.rover_index_saved_key), mRoverIndex);
    }

    /**
     * Create a Linear Layout Manager for each RecyclerView
     *
     * @return A new Horizontal Linear Layout Manager
     */
    private LinearLayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    }

    /**
     * Set up Recycler Views for cameras with pictures
     */
    private void setupUI() {
        //if camera has any images then setup adapter and show views.
        boolean anyCameras = false;
        if (mViewModel.getImageUrlsForCamera(HelperUtils.CAM_FHAZ_INDEX) != null) {
            mFhazAdapter = new RoverPhotosAdapter(this);
            mFhazRecyclerView.setLayoutManager(createLayoutManager());
            mFhazRecyclerView.setAdapter(mFhazAdapter);
            mFhazAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_FHAZ_INDEX));
            mFhazRecyclerView.setVisibility(View.VISIBLE);
            mFhazLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if (mViewModel.getImageUrlsForCamera(HelperUtils.CAM_RHAZ_INDEX) != null) {
            mRhazAdapter = new RoverPhotosAdapter(this);
            mRhazRecyclerView.setLayoutManager(createLayoutManager());
            mRhazRecyclerView.setAdapter(mRhazAdapter);
            mRhazAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_RHAZ_INDEX));
            mRhazRecyclerView.setVisibility(View.VISIBLE);
            mRhazLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if (mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MAST_INDEX) != null) {
            mMastAdapter = new RoverPhotosAdapter(this);
            mMastRecyclerView.setLayoutManager(createLayoutManager());
            mMastRecyclerView.setAdapter(mMastAdapter);
            mMastAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MAST_INDEX));
            mMastRecyclerView.setVisibility(View.VISIBLE);
            mMastLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if (mViewModel.getImageUrlsForCamera(HelperUtils.CAM_CHEMCAM_INDEX) != null) {
            mChemcamAdapter = new RoverPhotosAdapter(this);
            mChemcamRecyclerView.setLayoutManager(createLayoutManager());
            mChemcamRecyclerView.setAdapter(mChemcamAdapter);
            mChemcamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_CHEMCAM_INDEX));
            mChemcamRecyclerView.setVisibility(View.VISIBLE);
            mChemcamLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if (mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MAHLI_INDEX) != null) {
            mMahliAdapter = new RoverPhotosAdapter(this);
            mMahliRecyclerView.setLayoutManager(createLayoutManager());
            mMahliRecyclerView.setAdapter(mMahliAdapter);
            mMahliAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MAHLI_INDEX));
            mMahliRecyclerView.setVisibility(View.VISIBLE);
            mMahliLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if (mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MARDI_INDEX) != null) {
            mMardiAdapter = new RoverPhotosAdapter(this);
            mMardiRecyclerView.setLayoutManager(createLayoutManager());
            mMardiRecyclerView.setAdapter(mMardiAdapter);
            mMardiAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MARDI_INDEX));
            mMardiRecyclerView.setVisibility(View.VISIBLE);
            mMardiLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if (mViewModel.getImageUrlsForCamera(HelperUtils.CAM_NAVCAM_INDEX) != null) {
            mNavcamAdapter = new RoverPhotosAdapter(this);
            mNavcamRecyclerView.setLayoutManager(createLayoutManager());
            mNavcamRecyclerView.setAdapter(mNavcamAdapter);
            mNavcamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_NAVCAM_INDEX));
            mNavcamRecyclerView.setVisibility(View.VISIBLE);
            mNavcamLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if (mViewModel.getImageUrlsForCamera(HelperUtils.CAM_PANCAM_INDEX) != null) {
            mPancamAdapter = new RoverPhotosAdapter(this);
            mPancamRecyclerView.setLayoutManager(createLayoutManager());
            mPancamRecyclerView.setAdapter(mPancamAdapter);
            mPancamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_PANCAM_INDEX));
            mPancamRecyclerView.setVisibility(View.VISIBLE);
            mPancamLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if (mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MINITES_INDEX) != null) {
            mMinitesAdapter = new RoverPhotosAdapter(this);
            mMinitesRecyclerView.setLayoutManager(createLayoutManager());
            mMinitesRecyclerView.setAdapter(mMinitesAdapter);
            mMinitesAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MINITES_INDEX));
            mMinitesRecyclerView.setVisibility(View.VISIBLE);
            mMinitesLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if (mViewModel.getImageUrlsForCamera(HelperUtils.CAM_IDC_INDEX) != null){
            mIdcAdapter = new RoverPhotosAdapter(this);
            mIdcRecyclerView.setLayoutManager(createLayoutManager());
            mIdcRecyclerView.setAdapter(mIdcAdapter);
            mIdcAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_IDC_INDEX));
            mIdcRecyclerView.setVisibility(View.VISIBLE);
            mIdcLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_ICC_INDEX) != null){
            mIccAdapter = new RoverPhotosAdapter(this);
            mIccRecyclerView.setLayoutManager(createLayoutManager());
            mIccRecyclerView.setAdapter(mIccAdapter);
            mIccAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_ICC_INDEX));
            mIccRecyclerView.setVisibility(View.VISIBLE);
            mIccLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_EDL_RUCAM_INDEX) != null){
            mRucamAdapter = new RoverPhotosAdapter(this);
            mRucamRecyclerView.setLayoutManager(createLayoutManager());
            mRucamRecyclerView.setAdapter(mRucamAdapter);
            mRucamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_EDL_RUCAM_INDEX));
            mRucamRecyclerView.setVisibility(View.VISIBLE);
            mRucamLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_EDL_RDCAM_INDEX) != null){
            mRdcamAdapter = new RoverPhotosAdapter(this);
            mRdcamRecyclerView.setLayoutManager(createLayoutManager());
            mRdcamRecyclerView.setAdapter(mRdcamAdapter);
            mRdcamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_EDL_RDCAM_INDEX));
            mRdcamRecyclerView.setVisibility(View.VISIBLE);
            mRdcamLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }

        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_EDL_DDCAM_INDEX) != null){
            mDdcamAdapter = new RoverPhotosAdapter(this);
            mDdcamRecyclerView.setLayoutManager(createLayoutManager());
            mDdcamRecyclerView.setAdapter(mDdcamAdapter);
            mDdcamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_EDL_DDCAM_INDEX));
            mDdcamRecyclerView.setVisibility(View.VISIBLE);
            mDdcamLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }

        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_EDL_PUCAM1_INDEX) != null){
            mPucam1Adapter = new RoverPhotosAdapter(this);
            mPucam1RecyclerView.setLayoutManager(createLayoutManager());
            mPucam1RecyclerView.setAdapter(mPucam1Adapter);
            mPucam1Adapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_EDL_PUCAM1_INDEX));
            mPucam1RecyclerView.setVisibility(View.VISIBLE);
            mPucam1Label.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_EDL_PUCAM1_INDEX) != null){
            mPucam1Adapter = new RoverPhotosAdapter(this);
            mPucam1RecyclerView.setLayoutManager(createLayoutManager());
            mPucam1RecyclerView.setAdapter(mPucam1Adapter);
            mPucam1Adapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_EDL_PUCAM1_INDEX));
            mPucam1RecyclerView.setVisibility(View.VISIBLE);
            mPucam1Label.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_EDL_PUCAM2_INDEX) != null){
            mPucam2Adapter = new RoverPhotosAdapter(this);
            mPucam2RecyclerView.setLayoutManager(createLayoutManager());
            mPucam2RecyclerView.setAdapter(mPucam2Adapter);
            mPucam2Adapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_EDL_PUCAM2_INDEX));
            mPucam2RecyclerView.setVisibility(View.VISIBLE);
            mPucam2Label.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_NAVCAM_LEFT_INDEX) != null){
            mNavleftAdapter = new RoverPhotosAdapter(this);
            mNavleftRecyclerView.setLayoutManager(createLayoutManager());
            mNavleftRecyclerView.setAdapter(mNavleftAdapter);
            mNavleftAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_NAVCAM_LEFT_INDEX));
            mNavleftRecyclerView.setVisibility(View.VISIBLE);
            mNavcamleftLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_NAVCAM_RIGHT_INDEX) != null){
            mNavrightAdapter = new RoverPhotosAdapter(this);
            mNavrightRecyclerView.setLayoutManager(createLayoutManager());
            mNavrightRecyclerView.setAdapter(mNavrightAdapter);
            mNavrightAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_NAVCAM_RIGHT_INDEX));
            mNavrightRecyclerView.setVisibility(View.VISIBLE);
            mNavcamrightLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MCZ_RIGHT_INDEX) != null){
            mMczrightAdapter = new RoverPhotosAdapter(this);
            mMczrightmRecyclerView.setLayoutManager(createLayoutManager());
            mMczrightmRecyclerView.setAdapter(mMczrightAdapter);
            mMczrightAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MCZ_RIGHT_INDEX));
            mMczrightmRecyclerView.setVisibility(View.VISIBLE);
            mMcarightLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MCZ_LEFT_INDEX) != null){
            mMczleftAdapter = new RoverPhotosAdapter(this);
            mMczleftRecyclerView.setLayoutManager(createLayoutManager());
            mMczleftRecyclerView.setAdapter(mMczleftAdapter);
            mMczleftAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MCZ_LEFT_INDEX));
            mMczleftRecyclerView.setVisibility(View.VISIBLE);
            mMczleftLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_FRONT_HAZCAM_LEFT_A_INDEX) != null){
            mFhazleftAdapter = new RoverPhotosAdapter(this);
            mFhazleftRecyclerView.setLayoutManager(createLayoutManager());
            mFhazleftRecyclerView.setAdapter(mFhazleftAdapter);
            mFhazleftAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_FRONT_HAZCAM_LEFT_A_INDEX));
            mFhazleftRecyclerView.setVisibility(View.VISIBLE);
            mFhazleftLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_FRONT_HAZCAM_LEFT_A_INDEX) != null){
            mFhazleftAdapter = new RoverPhotosAdapter(this);
            mFhazleftRecyclerView.setLayoutManager(createLayoutManager());
            mFhazleftRecyclerView.setAdapter(mFhazleftAdapter);
            mFhazleftAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_FRONT_HAZCAM_LEFT_A_INDEX));
            mFhazleftRecyclerView.setVisibility(View.VISIBLE);
            mFhazleftLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_FRONT_HAZCAM_RIGHT_A_INDEX) != null){
            mFhazrightAdapter = new RoverPhotosAdapter(this);
            mFhazrightRecyclerView.setLayoutManager(createLayoutManager());
            mFhazrightRecyclerView.setAdapter(mFhazrightAdapter);
            mFhazrightAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_FRONT_HAZCAM_RIGHT_A_INDEX));
            mFhazrightRecyclerView.setVisibility(View.VISIBLE);
            mFhazrightLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_REAR_HAZCAM_LEFT_INDEX) != null){
            mRhazleftAdapter = new RoverPhotosAdapter(this);
            mRhazleftRecyclerView.setLayoutManager(createLayoutManager());
            mRhazleftRecyclerView.setAdapter(mRhazleftAdapter);
            mRhazleftAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_REAR_HAZCAM_LEFT_INDEX));
            mRhazleftRecyclerView.setVisibility(View.VISIBLE);
            mRhazleftLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_REAR_HAZCAM_RIGHT_INDEX) != null){
            mRhazrightAdapter = new RoverPhotosAdapter(this);
            mRhazrightRecyclerView.setLayoutManager(createLayoutManager());
            mRhazrightRecyclerView.setAdapter(mRhazrightAdapter);
            mRhazrightAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_REAR_HAZCAM_RIGHT_INDEX));
            mRhazrightRecyclerView.setVisibility(View.VISIBLE);
            mRhazrightLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_SKYCAM_INDEX) != null){
            mSkycamAdapter = new RoverPhotosAdapter(this);
            mSkycamRecyclerView.setLayoutManager(createLayoutManager());
            mSkycamRecyclerView.setAdapter(mSkycamAdapter);
            mSkycamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_SKYCAM_INDEX));
            mSkycamRecyclerView.setVisibility(View.VISIBLE);
            mSkycamLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_SHERLOC_INDEX) != null){
            mSherlocAdapter = new RoverPhotosAdapter(this);
            mSherlocRecyclerView.setLayoutManager(createLayoutManager());
            mSherlocRecyclerView.setAdapter(mSherlocAdapter);
            mSherlocAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_SHERLOC_INDEX));
            mSherlocRecyclerView.setVisibility(View.VISIBLE);
            mSherlocLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }



        if (!anyCameras) {
            displayNoCameraSnack();
        } else {
            updateTitleText();
        }
        hideMainProgress();
    }


    /**
     * Display a SnackBar message informing the user if images are being limited or if all are being displayed
     */
    private void displayPrefSnack() {
        if (hasShownPrefSnack) return;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean gettingAllPhotos = sharedPreferences.getBoolean(getString(R.string.pref_get_all_photos_key),
                getResources().getBoolean(R.bool.pref_limit_photos_default));
        String snackMessage;
        if (!gettingAllPhotos) {
            snackMessage = getString(R.string.limiting_photos_snack);
        } else {
            snackMessage = getString(R.string.getting_all_photos_snack);
        }

        final Snackbar snackbar = Snackbar.make(mCoordinatorLayout, snackMessage, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.settings_action, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });
        snackbar.show();

        hasShownPrefSnack = true;
    }

    private void displayNoCameraSnack() {
        mTitleText.setText(getString(R.string.no_photos));
        final Snackbar snackbar = Snackbar.make(mCoordinatorLayout, R.string.no_photos, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snackbar_back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) return;
                getActivity().onBackPressed();
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    private void setRoverTitle() {
        String roverName = HelperUtils.getRoverNameByIndex(getContext(), mRoverIndex);
        mTitleText.setText(roverName);
    }

    private void updateTitleText() {
        String roverName = HelperUtils.getRoverNameByIndex(getContext(), mRoverIndex);
        Cameras cameras = mViewModel.getCameras().getValue();
        if (cameras == null) return;
        mSol = cameras.getSol();
        mDateString = cameras.getEarthDate();
        String title = TextUtils.join(" - ", new String[]{roverName, mSol, mDateString});
        mTitleText.setText(title);
    }

    private void hideAllCameraViews() {
        mFhazLabel.setVisibility(View.GONE);
        mFhazRecyclerView.setVisibility(View.GONE);
        mRhazLabel.setVisibility(View.GONE);
        mRhazRecyclerView.setVisibility(View.GONE);
        mMastLabel.setVisibility(View.GONE);
        mMastRecyclerView.setVisibility(View.GONE);
        mChemcamLabel.setVisibility(View.GONE);
        mChemcamRecyclerView.setVisibility(View.GONE);
        mMahliLabel.setVisibility(View.GONE);
        mMahliRecyclerView.setVisibility(View.GONE);
        mMardiLabel.setVisibility(View.GONE);
        mMardiRecyclerView.setVisibility(View.GONE);
        mNavcamLabel.setVisibility(View.GONE);
        mNavcamRecyclerView.setVisibility(View.GONE);
        mPancamLabel.setVisibility(View.GONE);
        mPancamRecyclerView.setVisibility(View.GONE);
        mMinitesLabel.setVisibility(View.GONE);
        mMinitesRecyclerView.setVisibility(View.GONE);
        mIdcLabel.setVisibility(View.GONE);
        mIdcRecyclerView.setVisibility(View.GONE);
        mIccLabel.setVisibility(View.GONE);
        mIccRecyclerView.setVisibility(View.GONE);
        mRucamRecyclerView.setVisibility(View.GONE);
        mRdcamRecyclerView.setVisibility(View.GONE);
        mDdcamRecyclerView.setVisibility(View.GONE);
        mPucam1RecyclerView.setVisibility(View.GONE);
        mPucam2RecyclerView.setVisibility(View.GONE);
        mNavleftRecyclerView.setVisibility(View.GONE);
        mNavrightRecyclerView.setVisibility(View.GONE);
        mMczleftRecyclerView.setVisibility(View.GONE);
        mMczrightmRecyclerView.setVisibility(View.GONE);
        mFhazleftRecyclerView.setVisibility(View.GONE);
        mFhazrightRecyclerView.setVisibility(View.GONE);
        mRhazleftRecyclerView.setVisibility(View.GONE);
        mRhazrightRecyclerView.setVisibility(View.GONE);
        mSkycamRecyclerView.setVisibility(View.GONE);
        mSherlocRecyclerView.setVisibility(View.GONE);
        mRucamLabel.setVisibility(View.GONE);
        mRdcamLabel.setVisibility(View.GONE);
        mDdcamLabel.setVisibility(View.GONE);
        mPucam1Label.setVisibility(View.GONE);
        mPucam2Label.setVisibility(View.GONE);
        mNavcamleftLabel.setVisibility(View.GONE);
        mNavcamrightLabel.setVisibility(View.GONE);
        mMczleftLabel.setVisibility(View.GONE);
        mMcarightLabel.setVisibility(View.GONE);
        mFhazleftLabel.setVisibility(View.GONE);
        mFhazrightLabel.setVisibility(View.GONE);
        mRhazleftLabel.setVisibility(View.GONE);
        mRhazrightLabel.setVisibility(View.GONE);
        mSkycamLabel.setVisibility(View.GONE);
        mSherlocLabel.setVisibility(View.GONE);



    }

    private void showAllViews() {
        mFhazLabel.setVisibility(View.VISIBLE);
        mFhazRecyclerView.setVisibility(View.VISIBLE);
        mRhazLabel.setVisibility(View.VISIBLE);
        mRhazRecyclerView.setVisibility(View.VISIBLE);
        mMastLabel.setVisibility(View.VISIBLE);
        mMastRecyclerView.setVisibility(View.VISIBLE);
        mChemcamLabel.setVisibility(View.VISIBLE);
        mChemcamRecyclerView.setVisibility(View.VISIBLE);
        mMahliLabel.setVisibility(View.VISIBLE);
        mMahliRecyclerView.setVisibility(View.VISIBLE);
        mMardiLabel.setVisibility(View.VISIBLE);
        mMardiRecyclerView.setVisibility(View.VISIBLE);
        mNavcamLabel.setVisibility(View.VISIBLE);
        mNavcamRecyclerView.setVisibility(View.VISIBLE);
        mPancamLabel.setVisibility(View.VISIBLE);
        mPancamRecyclerView.setVisibility(View.VISIBLE);
        mMinitesLabel.setVisibility(View.VISIBLE);
        mMinitesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideMainProgress() {
        mMainProgress.setVisibility(View.GONE);
    }

    private void showMainProgress() {
        mMainProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPhotoClick(List<String> url, View view, int clickedPos) {
        FragmentActivity activity = getActivity();
        if (activity == null) return;

        ArrayList<String> urls = new ArrayList<>(url);
        FullPhotoFragment photoFragment = FullPhotoFragment.newInstance(activity, urls,
                clickedPos, mRoverIndex, mDateString);
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.explore_detail_container, photoFragment,
                        FullPhotoFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();

    }

    /**
     * Show info Dialog Fragment informing user the app is not downloading all images by default.
     * Set SharedPreferences so this is only displayed on first run
     */
    private void shownPhotoLimitDialog() {
        //see if dialog has been shown to user before.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (!sharedPreferences.getBoolean(getString(R.string.pref_shown_photos_info),
                getResources().getBoolean(R.bool.pref_shown_photos_dialog_default))) {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                InfoDialogFragment infoDialogFragment = InfoDialogFragment.newInstance(activity, InformationUtils.PHOTO_LIMIT_PREF);
                infoDialogFragment.show(activity.getSupportFragmentManager(), InformationUtils.class.getSimpleName());
                //change shared pref to true so dialog only shows one time. Snackbar will continue to show sometimes.
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_shown_photos_info), true);
                editor.apply();
            }
        }
    }

}
