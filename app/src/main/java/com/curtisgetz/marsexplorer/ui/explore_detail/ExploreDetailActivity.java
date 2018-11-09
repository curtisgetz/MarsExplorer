package com.curtisgetz.marsexplorer.ui.explore_detail;




import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
 import android.support.v7.app.ActionBar;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.ui.MarsBaseActivity;
import com.curtisgetz.marsexplorer.ui.explore.MarsExploreActivity;
import com.curtisgetz.marsexplorer.ui.explore.RoverExploreActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.favorites.FavoritePhotosFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.mars_facts.MarsFactsFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.mars_weather.MarsWeatherFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.FullPhotoFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.FullPhotoPagerFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.RoverPhotosFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_science.RoverScienceFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.tweets.TweetsFragment;
import com.curtisgetz.marsexplorer.ui.main.MainActivity;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ExploreDetailActivity extends MarsBaseActivity implements
        FullPhotoPagerFragment.FullPhotoPagerInteraction, MarsFactsFragment.FactsInteraction {


    private int mExploreCatIndex;
    private String mCurrentSol;
    private int mRoverIndex;
    private String mParentActivity;

    @BindView(R.id.explore_detail_activity_coordinator)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_detail);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if(intent == null){
            finish();
        }else {
            mParentActivity = intent.getStringExtra(getString(R.string.parent_activity_tag_extra));
            if(savedInstanceState == null) {
                mExploreCatIndex = intent.getIntExtra(getString(R.string.explore_index_extra_key), HelperUtils.MARS_WEATHER_CAT_INDEX);
                startDetailFragment(intent);

            }else {
                mExploreCatIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key), HelperUtils.MARS_WEATHER_CAT_INDEX);
                mCurrentSol = savedInstanceState.getString(getString(R.string.sol_number_saved_key), HelperUtils.DEFAULT_SOL_NUMBER );
                mRoverIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key), HelperUtils.CURIOSITY_ROVER_INDEX);
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.rover_index_saved_key), mExploreCatIndex);
        outState.putString(getString(R.string.sol_number_saved_key), mCurrentSol);
        outState.putInt(getString(R.string.rover_index_saved_key), mRoverIndex);
    }


    private void startDetailFragment(Intent intent) {
        //create bundle for detail fragment
        Bundle bundle = new Bundle();
        switch (mExploreCatIndex) {
            case HelperUtils.MARS_WEATHER_CAT_INDEX:
                //if Mars Weather was selected, create MarsWeatherFragment.
                MarsWeatherFragment weatherFragment = MarsWeatherFragment.newInstance();
                //MarsWeatherFragment weatherFragment = new MarsWeatherFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.explore_detail_container, weatherFragment).commit();
                break;
            case HelperUtils.MARS_FACTS_CAT_INDEX:
                //if Mars Facts was selected, create MarsFacts Fragment
                MarsFactsFragment factsFragment = MarsFactsFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.explore_detail_container, factsFragment).commit();
                break;
            case HelperUtils.MARS_FAVORITES_CAT_INDEX:
                //if Favorites was selected, create FavoritePhotoFragment
                FavoritePhotosFragment photoFragment = FavoritePhotosFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.explore_detail_container, photoFragment).commit();
                break;
            //Info and science screens will use same fragment but will load different info in RoverScienceFragment
            // based on the exploreIndex (as defined in HelperUtils.)
            case HelperUtils.ROVER_INFO_CAT_INDEX:
            case HelperUtils.ROVER_SCIENCE_CAT_INDEX:
                mRoverIndex = intent.getIntExtra(getString(R.string.rover_index_extra), HelperUtils.CURIOSITY_ROVER_INDEX);
                bundle.putInt(getString(R.string.rover_index_extra), mRoverIndex);
                bundle.putInt(getString(R.string.explore_index_extra_key), mExploreCatIndex);
                RoverScienceFragment scienceFragment = RoverScienceFragment.newInstance(this, mRoverIndex, mExploreCatIndex);
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.explore_detail_container, scienceFragment).commit();
                break;
            case HelperUtils.ROVER_PICTURES_CAT_INDEX:
                mCurrentSol = intent.getStringExtra(getString(R.string.sol_number_extra_key));
                mRoverIndex = intent.getIntExtra(getString(R.string.rover_index_extra), HelperUtils.CURIOSITY_ROVER_INDEX);
                bundle.putInt(getString(R.string.rover_index_extra), mRoverIndex);
                bundle.putString(getString(R.string.sol_number_extra_key), mCurrentSol);
                RoverPhotosFragment roverPhotosFragment = RoverPhotosFragment.newInstance(this, mRoverIndex, mCurrentSol);
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.explore_detail_container, roverPhotosFragment).commit();
                break;
            case HelperUtils.ROVER_TWEETS_CAT_INDEX:
                TweetsFragment tweetsFragment = TweetsFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.explore_detail_container,
                        tweetsFragment).commit();
        }

    }


    //Fragment interaction interface methods
    @Override
    public void callDisplaySnack(String message) {
        //call display snack method on FullPhotoFragment. This will allow the coordinatorlayout
        //to handle the snack display and moving the FAB
        FullPhotoFragment photoFragment = (FullPhotoFragment) getSupportFragmentManager()
                .findFragmentByTag(FullPhotoFragment.class.getSimpleName());

        if (photoFragment != null) {
            photoFragment.displaySnack(message);
        }
    }

    @Override
    public String getDateString() {
        FullPhotoFragment photoFragment = (FullPhotoFragment) getSupportFragmentManager()
                .findFragmentByTag(FullPhotoFragment.class.getSimpleName());

        if (photoFragment != null) {
            return photoFragment.getDate();
        } else {
            return null;
        }
    }

    @Override
    public int getRoverIndex() {
        FullPhotoFragment photoFragment = (FullPhotoFragment) getSupportFragmentManager()
                .findFragmentByTag(FullPhotoFragment.class.getSimpleName());

        if (photoFragment != null) {
            return photoFragment.getRover();
        } else {
            return -1;
        }
    }

    @Override
    public void displaySnack(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    //help from https://stackoverflow.com/questions/19184154/dynamically-set-parent-activity/28334824
    // to get parent activity when parent may be different
    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivity();
    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        return getParentActivity();
    }

    private Intent getParentActivity(){
        Intent intent;
        //check extra to find parent. Parent will add their name as an extra
        if(mParentActivity == null){
            intent = new Intent(this, MainActivity.class);
        }else if (mParentActivity.equalsIgnoreCase(RoverExploreActivity.class.getSimpleName())){
            intent = new Intent(this, RoverExploreActivity.class);
        }else if(mParentActivity.equalsIgnoreCase(MarsExploreActivity.class.getSimpleName())){
            intent = new Intent(this, MarsExploreActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }



}
