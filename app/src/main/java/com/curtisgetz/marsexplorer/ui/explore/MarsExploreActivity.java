
/*
 * Created by Curtis Getz on 11/6/18 11:24 AM
 * Last modified 11/4/18 1:48 AM
 */

package com.curtisgetz.marsexplorer.ui.explore;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.rover_explore.ExploreCategory;
import com.curtisgetz.marsexplorer.ui.MarsBaseActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.ExploreDetailActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.favorites.FavoritePhotosFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.mars_facts.MarsFactsFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.mars_weather.MarsWeatherFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.FullPhotoFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.FullPhotoPagerFragment;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Activity to display the options for exploring Mars
 */
public class MarsExploreActivity extends MarsBaseActivity implements
        ExploreCategoryAdapter.ExploreCategoryClick , MarsFactsFragment.FactsInteraction
        ,FullPhotoPagerFragment.FullPhotoPagerInteraction {


    @BindView(R.id.mars_explore_categories_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.explore_mars_title)
    TextView mTitleText;
    @BindView(R.id.mars_explore_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    private ExploreCategoryAdapter mAdapter;
    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_explore);
        ButterKnife.bind(this);
        isTwoPane = (findViewById(R.id.mars_explore_sw600_land) != null);

        mAdapter = new ExploreCategoryAdapter(getLayoutInflater(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        populateUI();
    }

    /**
     * Set Adapter data with explore categories to update the UI
     */
    private void populateUI(){
        List<ExploreCategory> categories = HelperUtils.getExploreCategories(this, HelperUtils.MARS_EXPLORE_INDEX);
        mAdapter.setData(categories);
    }

    /**
     * Handle clicks of categories
     * @param categoryIndex index of clicked category.
     */
    @Override
    public void onCategoryClick(int categoryIndex) {
        if(isNetworkAvailable()){
            //if two pane then open fragment in detail view
            if(isTwoPane){
                Fragment fragment;
                switch (categoryIndex){
                    case HelperUtils.MARS_WEATHER_CAT_INDEX:
                        fragment = MarsWeatherFragment.newInstance();
                        break;
                    case HelperUtils.MARS_FACTS_CAT_INDEX:
                        fragment = MarsFactsFragment.newInstance();
                        break;
                    case HelperUtils.MARS_FAVORITES_CAT_INDEX:
                        fragment = FavoritePhotosFragment.newInstance();
                        break;
                    default:
                        return;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.explore_detail_container,
                        fragment).commit();
            //if not two plane then open Explore Detail Activity
            }else {
            Intent intent = new Intent(getApplicationContext(), ExploreDetailActivity.class);
            intent.putExtra(getString(R.string.explore_index_extra_key), categoryIndex);
            intent.putExtra(getString(R.string.parent_activity_tag_extra), this.getClass().getSimpleName());
            startActivity(intent);
            }
        }

    }

    /**
     * Chcek if network is available
     * @return true if network is available. false if network is not available
     */
    private boolean isNetworkAvailable()  {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if((networkInfo != null && networkInfo.isConnected())){
            return true;
        }else {
            Snackbar.make(mCoordinatorLayout, R.string.internet_required,
                    Snackbar.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * Allow Fragments to have this Activity display LONG Snack messages
     * @param message String for Snack to display to user
     */
    @Override
    public void displaySnack(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();

    }

    /**
     * Call displaySnack method on {@link FullPhotoFragment}. This will allow the CoordinatorLayout
     * to handle the snack display and moving the FAB out of the way.
     * @param message String for Snack to display to user
     */
    @Override
    public void callDisplaySnack(String message) {
        FullPhotoFragment photoFragment = (FullPhotoFragment) getSupportFragmentManager()
                .findFragmentByTag(FullPhotoFragment.class.getSimpleName());

        if (photoFragment != null) {
            photoFragment.displaySnack(message);
        }
    }

    /**
     * Get date string of current image in {@link FullPhotoFragment}
     * @return date from {@link FullPhotoFragment} as a String.
     */
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

    /**
     * Get rover index of current image in {@link FullPhotoFragment}
     * @return rover index as an int
     */
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
}
