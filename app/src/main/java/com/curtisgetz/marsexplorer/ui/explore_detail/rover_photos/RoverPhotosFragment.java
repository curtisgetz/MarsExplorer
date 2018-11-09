package com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private String mSol;
    private int mRoverIndex;
    private CamerasViewModel mViewModel;
    private String mDateString;
    private Unbinder mUnBinder;
    private static boolean hasShownPrefSnack = false;

    //RecyclerViews for each camera
    @BindView(R.id.photos_fhaz_recyclerview) RecyclerView mFhazRecyclerView;
    @BindView(R.id.photos_rhaz_recyclerview) RecyclerView mRhazRecyclerView;
    @BindView(R.id.photos_mast_recyclerview) RecyclerView mMastRecyclerView;
    @BindView(R.id.photos_chemcam_recyclerview) RecyclerView mChemcamRecyclerView;
    @BindView(R.id.photos_mahli_recyclerview) RecyclerView mMahliRecyclerView;
    @BindView(R.id.photos_mardi_recyclerview) RecyclerView mMardiRecyclerView;
    @BindView(R.id.photos_navcam_recyclerview) RecyclerView mNavcamRecyclerView;
    @BindView(R.id.photos_pancam_recyclerview) RecyclerView mPancamRecyclerView;
    @BindView(R.id.photos_minites_recyclerview) RecyclerView mMinitesRecyclerView;
    //TextViews for camera name labels
    @BindView(R.id.photos_fhaz_label) TextView mFhazLabel;
    @BindView(R.id.photos_rhaz_label) TextView mRhazLabel;
    @BindView(R.id.photos_mast_label) TextView mMastLabel;
    @BindView(R.id.photos_chemcam_label) TextView mChemcamLabel;
    @BindView(R.id.photos_mahli_label) TextView mMahliLabel;
    @BindView(R.id.photos_mardi_label) TextView mMardiLabel;
    @BindView(R.id.photos_navcam_label) TextView mNavcamLabel;
    @BindView(R.id.photos_pancam_label) TextView mPancamLabel;
    @BindView(R.id.photos_minites_label) TextView mMinitesLabel;

    @BindView(R.id.rover_photos_main_progress) ProgressBar mMainProgress;
    @BindView(R.id.rover_photos_title) TextView mTitleText;
    @BindView(R.id.rover_photos_coordinator_layout) CoordinatorLayout mCoordinatorLayout;

    private RoverPhotosAdapter mFhazAdapter;
    private RoverPhotosAdapter mRhazAdapter;
    private RoverPhotosAdapter mMastAdapter;
    private RoverPhotosAdapter mChemcamAdapter;
    private RoverPhotosAdapter mMahliAdapter;
    private RoverPhotosAdapter mMardiAdapter;
    private RoverPhotosAdapter mNavcamAdapter;
    private RoverPhotosAdapter mPancamAdapter;
    private RoverPhotosAdapter mMinitesAdapter;



    public static RoverPhotosFragment newInstance(Context context, int roverIndex, String sol){
        RoverPhotosFragment fragment = new RoverPhotosFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(context.getResources().getString(R.string.rover_index_extra), roverIndex);
        bundle.putString(context.getResources().getString(R.string.sol_number_extra_key), sol);
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
        hideAllViews();
        showMainProgress();

        if(savedInstanceState == null){
            Bundle bundle = getArguments();
            mSol = bundle.getString(getString(R.string.sol_number_extra_key));
            mRoverIndex = bundle.getInt(getString(R.string.rover_index_extra));
        }else {
            mSol = savedInstanceState.getString(getString(R.string.sol_number_saved_key),
                    HelperUtils.DEFAULT_SOL_NUMBER);
            mRoverIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key),
                    HelperUtils.CURIOSITY_ROVER_INDEX);
        }

        setRoverTitle();
        CamerasVMFactory factory = new CamerasVMFactory(getActivity().getApplication(), mRoverIndex, mSol);
        mViewModel = ViewModelProviders.of(this, factory).get(CamerasViewModel.class);
        mViewModel.getCameras().observe(this, new Observer<Cameras>() {
            @Override
            public void onChanged(@Nullable Cameras cameras) {
                setupUI();
            }
        });
        return view;
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


    private LinearLayoutManager createLayoutManager(){
        return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    }


    private void setupUI(){
        //if camera has any images then setup adapter and show views.
        boolean anyCameras = false;
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_FHAZ_INDEX) != null){
            mFhazAdapter = new RoverPhotosAdapter(this);
            mFhazRecyclerView.setLayoutManager(createLayoutManager());
            mFhazRecyclerView.setAdapter(mFhazAdapter);
            mFhazAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_FHAZ_INDEX));
            mFhazRecyclerView.setVisibility(View.VISIBLE);
            mFhazLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_RHAZ_INDEX) != null){
            mRhazAdapter = new RoverPhotosAdapter(this);
            mRhazRecyclerView.setLayoutManager(createLayoutManager());
            mRhazRecyclerView.setAdapter(mRhazAdapter);
            mRhazAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_RHAZ_INDEX));
            mRhazRecyclerView.setVisibility(View.VISIBLE);
            mRhazLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MAST_INDEX) != null){
            mMastAdapter = new RoverPhotosAdapter(this);
            mMastRecyclerView.setLayoutManager(createLayoutManager());
            mMastRecyclerView.setAdapter(mMastAdapter);
            mMastAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MAST_INDEX));
            mMastRecyclerView.setVisibility(View.VISIBLE);
            mMastLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_CHEMCAM_INDEX) != null){
            mChemcamAdapter = new RoverPhotosAdapter(this);
            mChemcamRecyclerView.setLayoutManager(createLayoutManager());
            mChemcamRecyclerView.setAdapter(mChemcamAdapter);
            mChemcamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_CHEMCAM_INDEX));
            mChemcamRecyclerView.setVisibility(View.VISIBLE);
            mChemcamLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MAHLI_INDEX) != null){
            mMahliAdapter = new RoverPhotosAdapter(this);
            mMahliRecyclerView.setLayoutManager(createLayoutManager());
            mMahliRecyclerView.setAdapter(mMahliAdapter);
            mMahliAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MAHLI_INDEX));
            mMahliRecyclerView.setVisibility(View.VISIBLE);
            mMahliLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MARDI_INDEX) != null){
            mMardiAdapter = new RoverPhotosAdapter(this);
            mMardiRecyclerView.setLayoutManager(createLayoutManager());
            mMardiRecyclerView.setAdapter(mMardiAdapter);
            mMardiAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MARDI_INDEX));
            mMardiRecyclerView.setVisibility(View.VISIBLE);
            mMardiLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_NAVCAM_INDEX) != null){
            mNavcamAdapter = new RoverPhotosAdapter(this);
            mNavcamRecyclerView.setLayoutManager(createLayoutManager());
            mNavcamRecyclerView.setAdapter(mNavcamAdapter);
            mNavcamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_NAVCAM_INDEX));
            mNavcamRecyclerView.setVisibility(View.VISIBLE);
            mNavcamLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_PANCAM_INDEX) != null){
            mPancamAdapter = new RoverPhotosAdapter(this);
            mPancamRecyclerView.setLayoutManager(createLayoutManager());
            mPancamRecyclerView.setAdapter(mPancamAdapter);
            mPancamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_PANCAM_INDEX));
            mPancamRecyclerView.setVisibility(View.VISIBLE);
            mPancamLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MINITES_INDEX) != null){
            mMinitesAdapter = new RoverPhotosAdapter(this);
            mMinitesRecyclerView.setLayoutManager(createLayoutManager());
            mMinitesRecyclerView.setAdapter(mMinitesAdapter);
            mMinitesAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MINITES_INDEX));
            mMinitesRecyclerView.setVisibility(View.VISIBLE);
            mMinitesLabel.setVisibility(View.VISIBLE);
            anyCameras = true;
        }
        hideMainProgress();
        if(!anyCameras){
            displayNoCameraSnack();
        }else {
            updateTitleText();
        }
    }


    private void displayPrefSnack(){
        if(hasShownPrefSnack) return;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean gettingAllPhotos = sharedPreferences.getBoolean(getString(R.string.pref_get_all_photos_key),
                getResources().getBoolean(R.bool.pref_limit_photos_default));
        String snackMessage;
        if(!gettingAllPhotos){
            snackMessage = getString(R.string.limiting_photos_snack);
        }else {
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
        snackbar.setAction(R.string.snackbar_back, new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(getActivity() == null) return;
                getActivity().onBackPressed();
                snackbar.dismiss();
            }
        } );
        snackbar.show();
    }

    private void setRoverTitle(){
        String roverName = HelperUtils.getRoverNameByIndex(getContext(), mRoverIndex);
        mTitleText.setText(roverName);
    }

    private void updateTitleText(){
        String roverName = HelperUtils.getRoverNameByIndex(getContext(), mRoverIndex);
        Cameras cameras = mViewModel.getCameras().getValue();
        if(cameras == null) return;
        mDateString = cameras.getEarthDate();
        String title = TextUtils.join(" - ", new String[] {roverName, mSol, mDateString});
        mTitleText.setText(title);
    }

    private void hideAllViews(){
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
    }

    private void showAllViews(){
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

    private void hideMainProgress(){
        mMainProgress.setVisibility(View.GONE);
    }

    private void showMainProgress(){
        mMainProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPhotoClick(List<String> url, View view, int clickedPos) {
        FragmentActivity activity = getActivity();
        if(activity == null) return;

        ArrayList<String> urls = new ArrayList<>(url);
        FullPhotoFragment photoFragment = FullPhotoFragment.newInstance(activity, urls,
                clickedPos, mRoverIndex, mDateString);
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.explore_detail_container, photoFragment,
                        FullPhotoFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();

    }

    private void shownPhotoLimitDialog(){
        //see if dialog has been shown to user before.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(!sharedPreferences.getBoolean(getString(R.string.pref_shown_photos_info),
                getResources().getBoolean(R.bool.pref_shown_photos_dialog_default))){
            FragmentActivity activity = getActivity();
            if(activity!=null){
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
