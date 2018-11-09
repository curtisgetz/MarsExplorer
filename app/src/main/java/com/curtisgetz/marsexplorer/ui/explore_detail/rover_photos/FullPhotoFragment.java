package com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.curtisgetz.marsexplorer.ui.explore_detail.favorites.FavoriteViewModel;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullPhotoFragment extends Fragment   {


    private List<String> mUrls = new ArrayList<>();

    @Nullable
    @BindView(R.id.photo_share_fab) FloatingActionButton mShareFab;
    @BindView(R.id.full_photo_coordinator_layout) CoordinatorLayout mCoordinator;
    @BindView(R.id.photo_viewpager) ViewPager mViewPager;
    FullPhotoAdapter mAdapter;
    private int mStartingPos;
    private int mRoverIndex;
    private String mDateString;
    private boolean mIsFavorites;
    private Unbinder mUnBinder;
    private FavoriteViewModel mViewModel;


    public static FullPhotoFragment newInstance(Context context, ArrayList<String> urls, int startingPos,
                                                int roverIndex, String dateString){

        FullPhotoFragment fragment = new FullPhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(context.getString(R.string.url_list_extra), urls);
        bundle.putInt(context.getString(R.string.clicked_photo_pos_extra), startingPos);
        bundle.putInt(context.getString(R.string.rover_index_extra), roverIndex);
        bundle.putString(context.getString(R.string.date_string_extra), dateString);
        bundle.putBoolean(context.getString(R.string.is_favorite_screen_extra), false);
        fragment.setArguments(bundle);
        return fragment;
    }


    public FullPhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if(activity != null){
            mViewModel = ViewModelProviders.of(activity).get(FavoriteViewModel.class);
            mViewModel.getFavorites().observe(this, new Observer<List<FavoriteImage>>() {
                @Override
                public void onChanged(@Nullable List<FavoriteImage> favoriteImages) {
                    if(mIsFavorites){
                        if(favoriteImages == null) return;
                        List<String> urls = new ArrayList<>();
                        for(FavoriteImage image : favoriteImages){
                            urls.add(image.getImageUrl());
                        }
                        mAdapter.setData(urls);
                    }
                }
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null && getArguments() != null) {
            Bundle bundle = getArguments();
            mIsFavorites = bundle.getBoolean(getString(R.string.is_favorites_key), false);
            mRoverIndex = bundle.getInt(getString(R.string.rover_index_extra));
            mUrls = bundle.getStringArrayList(getString(R.string.url_list_extra));
            mStartingPos = bundle.getInt(getString(R.string.clicked_photo_pos_extra));
            mDateString = bundle.getString(getString(R.string.date_string_extra), getString(R.string.unknown_date));
        }else if(savedInstanceState != null) {
            mUrls = savedInstanceState.getStringArrayList(getString(R.string.url_list_saved));
            mStartingPos = savedInstanceState.getInt(getString(R.string.starting_pos_saved));
            mRoverIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key));
            mDateString = savedInstanceState.getString(getString(R.string.date_string_extra));
            mIsFavorites = savedInstanceState.getBoolean(getString(R.string.is_favorites_saved_key));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_photo, container, false);
        mUnBinder = ButterKnife.bind(this, view);

        mAdapter = new FullPhotoAdapter(getChildFragmentManager(), getActivity());
        mViewPager.setAdapter(mAdapter);
        mAdapter.setData(mUrls);
        mViewPager.setCurrentItem(mStartingPos);

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
        outState.putStringArrayList(getString(R.string.url_list_saved),  new ArrayList<>(mUrls));
        outState.putInt(getString(R.string.starting_pos_saved), mStartingPos);
        outState.putInt(getString(R.string.rover_index_saved_key), mRoverIndex);
        outState.putBoolean(getString(R.string.is_favorites_saved_key), mIsFavorites);
    }

    @OnClick(R.id.photo_share_fab)
    public void onFabClick(){
        FragmentActivity activity = getActivity();
        if(activity == null) return;

        String roverName = HelperUtils.getRoverNameByIndex(activity, mRoverIndex);
        //use custom share message if not in Favorites. Otherwise use simple share message.
        //look into custom share message for Favorites later.
        String shareMessage;
        if(mRoverIndex == -1){
            shareMessage = getString(R.string.simple_share_pic_text)
                    + "\n\n" + mUrls.get(mViewPager.getCurrentItem());
        }else {
            shareMessage = getString(R.string.share_pic_text,
                    roverName, mDateString, mUrls.get(mViewPager.getCurrentItem()));
        }
        Intent intentToShare = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setText(shareMessage)
                .getIntent();
        if(intentToShare.resolveActivity(activity.getPackageManager())!= null){
            startActivity(intentToShare);
        }

    }

    public String getDate(){
        return mDateString;
    }

    public int getRover(){
        return mRoverIndex;
    }

    public void displaySnack(String message){
         Snackbar.make(mCoordinator, message, Snackbar.LENGTH_SHORT).show();
    }



}
