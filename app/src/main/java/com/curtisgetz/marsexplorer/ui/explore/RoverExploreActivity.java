
/*
 * Created by Curtis Getz on 11/6/18 1:50 PM
 * Last modified 11/4/18 1:48 AM
 */

package com.curtisgetz.marsexplorer.ui.explore;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.Helper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.rover_explore.ExploreCategory;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;
import com.curtisgetz.marsexplorer.ui.CirclePagerIndicatorDecoration;
import com.curtisgetz.marsexplorer.ui.MarsBaseActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.ExploreDetailActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.favorites.FavoritePhotosFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.FullPhotoFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.FullPhotoPagerFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.RoverPhotosFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_science.RoverScienceFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.tweets.TweetsFragment;
import com.curtisgetz.marsexplorer.ui.info.InfoDialogFragment;
import com.curtisgetz.marsexplorer.ui.main.MainActivity;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.InformationUtils;
import com.curtisgetz.marsexplorer.utils.JsonUtils;
import com.curtisgetz.marsexplorer.utils.NetworkUtils;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Activity for exploring a Mars Rover
 */
public class RoverExploreActivity extends MarsBaseActivity implements
        RoverCategoryAdapter.CategoryClickListener, FullPhotoPagerFragment.FullPhotoPagerInteraction,
        SolSearchDialogFragment.SearchDialogInteraction, SolDatePickerDialogFragment.DateDialogInteraction {


    private RoverCategoryAdapter mAdapter;
    private int mRoverIndex;
    private boolean isTwoPane;
    private boolean isSw600;
    private boolean isLandNotSw600;
    private RoverManifestViewModel mViewModel;
    private static boolean hasDownloadedManifests = false;

    @BindView(R.id.rover_options_recycler)
    RecyclerView mCategoryRecycler;
    @BindView(R.id.explore_master_title_text)
    TextView mTitleText;
    @BindView(R.id.mission_status_text)
    TextView mMissionStatusTv;
    @BindView(R.id.launch_date_text)
    TextView mLaunchTv;
    @BindView(R.id.landing_date_text)
    TextView mLandingTv;
    @BindView(R.id.sol_range_text)
    TextView mSolRangeTv;
    @BindView(R.id.manifest_loading)
    ProgressBar mManifestProgress;
    @BindView(R.id.explore_detail_coordinatorlayout)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.mission_status_label)
    TextView mMissionStatusLabel;
    @BindView(R.id.launch_date_label)
    TextView mLaunchLabel;
    @BindView(R.id.landing_date_label)
    TextView mLandLabel;
    @BindView(R.id.sol_range_label)
    TextView mSolRangeLabel;
    @BindView(R.id.sol_info_clickbox)
    View mSolClickbox;
    @BindView(R.id.sol_range_info_imageview)
    ImageView mSolRangeInfoIv;

    @BindView(R.id.rover_explore_root_constraintlayout)
    ConstraintLayout mConstraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_explore);
        ButterKnife.bind(this);

        isTwoPane = (findViewById(R.id.rover_explore_sw600land) != null);
        isSw600 = getResources().getBoolean(R.bool.is_sw600);
        isLandNotSw600 = getResources().getBoolean(R.bool.hide_manifest);
        setManifestViewVisibility();
        showManifestProgress();

        mAdapter = new RoverCategoryAdapter(this);
        setupRecyclerView();

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent == null) {
                finish();
            } else {
                mRoverIndex = intent.getIntExtra(getString(R.string.explore_extra),
                        HelperUtils.CURIOSITY_ROVER_INDEX);
            }
        } else {
            mRoverIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key),
                    HelperUtils.CURIOSITY_ROVER_INDEX);
        }


        RoverManifestVMFactory factory = new RoverManifestVMFactory(mRoverIndex, getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(RoverManifestViewModel.class);
        mViewModel.getManifest().observe(this, new Observer<RoverManifest>() {
            @Override
            public void onChanged(@Nullable RoverManifest roverManifest) {
                populateManifestUI();
            }
        });
        populateUI(mRoverIndex);

        if (!hasDownloadedManifests) {
            //don't download new manifest every time. Data doesn't change often.
            mViewModel.downloadNewManifests(getApplicationContext());
            hasDownloadedManifests = true;
        }


        if (isLandNotSw600) {
            hideManifestVisibilityOnSoftKeyboard();
        } else {
            showManifestVisibilityOnSoftKeyboard();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.rover_index_saved_key), mRoverIndex);
    }

    /**
     * Set up RecyclerView and add PagerSnapHelper with page indicators.
     */
    private void setupRecyclerView() {
        mCategoryRecycler.setLayoutManager(getLayoutManger());
        mCategoryRecycler.setAdapter(mAdapter);
        //add page indicators and SnapHelper if using horizontal layout
        if (getLayoutManger().getOrientation() == LinearLayoutManager.HORIZONTAL) {
            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(mCategoryRecycler);
            mCategoryRecycler.addItemDecoration(new CirclePagerIndicatorDecoration());
        }
    }

    /**
     * Get a layout manager for Recyclerview based on the screen density and orientation.
     * Use a vertical layout if sw600 only. If sw600-land then still use horizontal.
     *
     * @return LinearLayoutManager
     */
    private LinearLayoutManager getLayoutManger() {

        if (isSw600 && !isTwoPane) {
            return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        } else {
            return new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        }
        //isSw600 ? new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //      : new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    }

    /**
     * Set title text and set data in Adapter
     *
     * @param roverIndex use rover index to get rover name and List of ExploreCategory objects
     */
    private void populateUI(int roverIndex) {
        String roverName = HelperUtils.getRoverNameByIndex(this, roverIndex);
        String titleString = roverName + " " + getString(R.string.rover_string);
        mTitleText.setText(titleString);
        List<ExploreCategory> roverExploreCategories = HelperUtils.getRoverCategories(this, roverIndex);
        mAdapter.setData(roverExploreCategories);

    }


    /**
     * Handle category click
     *
     * @param catIndex index of clicked category
     */
    @Override
    public void onCategoryClick(int catIndex) {
        //if two pane then create fragment for detail layout
        if (isTwoPane) {
            Fragment fragment;
            switch (catIndex) {
                case HelperUtils.ROVER_SCIENCE_CAT_INDEX:
                case HelperUtils.ROVER_INFO_CAT_INDEX:
                    fragment = RoverScienceFragment.newInstance(this, mRoverIndex, catIndex);
                    break;
                case HelperUtils.MARS_FAVORITES_CAT_INDEX:
                    fragment = FavoritePhotosFragment.newInstance();
                    break;
                case HelperUtils.ROVER_TWEETS_CAT_INDEX:
                    fragment = TweetsFragment.newInstance();
                    break;
                default:
                    return;
            }
            startDetailFragment(fragment);

            //if not two pane then start ExploreDetail Activity
        } else {
            switch (catIndex) {
                // don't allow click on photo category,   only the sol buttons
                case HelperUtils.ROVER_PICTURES_CAT_INDEX:
                    return;
                default:
                    //Send explore index and rover index to ExploreActivity
                    Intent intent = new Intent(getApplicationContext(), ExploreDetailActivity.class);
                    intent.putExtra(getString(R.string.explore_index_extra_key), catIndex);
                    intent.putExtra(getString(R.string.rover_index_extra), mRoverIndex);
                    intent.putExtra(getString(R.string.parent_activity_tag_extra), RoverExploreActivity.class.getSimpleName());
                    startActivity(intent);
            }
        }
    }

    @Override
    public void onCalendarSolClick(int catIndex) {
        SolDatePickerDialogFragment dialogFragment = SolDatePickerDialogFragment.newInstance(this, mRoverIndex);
        dialogFragment.show(getSupportFragmentManager(), SolDatePickerDialogFragment.class.getSimpleName());

    }

    @Override
    public void onDialogDateSelect(String date) {
        if (isTwoPane) {
            RoverPhotosFragment roverPhotosFragment = RoverPhotosFragment.newInstance(getApplicationContext(),
                    mRoverIndex, null, RoverPhotosFragment.SEARCH_BY_DATE, date);
            startDetailFragment(roverPhotosFragment);
        } else {
            startExploreDetailActivity(null,
                    HelperUtils.ROVER_PICTURES_CAT_INDEX, RoverPhotosFragment.SEARCH_BY_DATE, date);
        }

    }

    @Override
    public void onDialogSearchClick(String solInput) {
        int catIndex = HelperUtils.ROVER_PICTURES_CAT_INDEX;
        startSolSearch(solInput, catIndex);
    }

    /**
     * Handle sol search button click
     *
     * @param catIndex explore category index
     */
    @Override
    public void onSolSearchClick(int catIndex) {
        //Get sol range, Then show dialog for user to enter Sol
        RoverManifest roverManifest = mViewModel.getManifest().getValue();
        final String solRange;
        if (roverManifest == null) {
            solRange = "";
        } else {
            solRange = roverManifest.getSolRange();
        }

        SolSearchDialogFragment dialogFragment = SolSearchDialogFragment.newInstance(getApplicationContext(), solRange);
        dialogFragment.show(getSupportFragmentManager(), SolSearchDialogFragment.class.getSimpleName());


    }

    /**
     * Start {@link RoverPhotosFragment} and search the Sol entered
     *
     * @param inputSol Sol input to search
     * @param catIndex Explore category
     */
    private void startSolSearch(String inputSol, int catIndex) {
        String validatedSol = mViewModel.validateSolInRange(inputSol);
        if (isTwoPane) {
            RoverPhotosFragment photosFragment = RoverPhotosFragment
                    .newInstance(this, mRoverIndex, validatedSol, RoverPhotosFragment.SEARCH_BY_SOL, null);
            startDetailFragment(photosFragment);
        } else {
            startExploreDetailActivity(validatedSol, catIndex, RoverPhotosFragment.SEARCH_BY_SOL, null);
        }
    }

    /**
     * Handle random sol button click.
     *
     * @param catIndex explore category index.
     */
    @Override
    public void onRandomSolClick(int catIndex) {
        if (isTwoPane) {
            RoverPhotosFragment photosFragment = RoverPhotosFragment
                    .newInstance(this, mRoverIndex, mViewModel.getRandomSol(), RoverPhotosFragment.SEARCH_BY_SOL, null);
            startDetailFragment(photosFragment);
        } else {
            int searchType = RoverPhotosFragment.SEARCH_BY_SOL;
            startExploreDetailActivity(mViewModel.getRandomSol(), catIndex, searchType, null);
        }
    }

    /**
     * Starts the Fragment in explore_detail_container
     *
     * @param fragment Fragment to start
     */
    private void startDetailFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.explore_detail_container, fragment).commit();
    }

    /**
     * Create Intent with Extras to start {@link ExploreDetailActivity}
     *
     * @param solNumber sol number that is currently being searched. Either from user input or
     *                  randomly generated.
     * @param catIndex  explore category index. Used by {@link ExploreDetailActivity} to start the
     *                  correct Fragment
     */
    private void startExploreDetailActivity(String solNumber, int catIndex, int searchType, String date) {
        if (isNetworkAvailable()) {
            Intent intent = new Intent(getApplicationContext(), ExploreDetailActivity.class);
            intent.putExtra(getString(R.string.explore_index_extra_key), catIndex);
            intent.putExtra(getString(R.string.rover_index_extra), mRoverIndex);
            intent.putExtra(getString(R.string.sol_number_extra_key), solNumber);
            intent.putExtra(getString(R.string.photo_search_type), searchType);
            intent.putExtra(getString(R.string.date_extra), date);
            //add extra so ExploreDetailActivity knows it's parent and can enable up navigation
            intent.putExtra(getString(R.string.parent_activity_tag_extra), this.getClass().getSimpleName());
            startActivity(intent);
        }
    }

    /**
     * Handle clicks for sol information. Shows {@link InfoDialogFragment} with info about the sol range
     */
    @OnClick({R.id.sol_info_clickbox, R.id.sol_range_label})
    public void solInfo() {
        InfoDialogFragment infoDialogFragment = InfoDialogFragment.newInstance(this, InformationUtils.SOL_RANGE_INFO);
        infoDialogFragment.show(getSupportFragmentManager(), InfoDialogFragment.class.getSimpleName());
    }

    /**
     * Shows rover manifest details in UI. Get RoverManifest from {@link RoverManifestViewModel} and
     * update TextViews with details.
     */
    private void populateManifestUI() {
        RoverManifest roverManifest = mViewModel.getManifest().getValue();
        if (roverManifest == null) return;
        hideManifestProgress();
        mMissionStatusTv.setText(HelperUtils.capitalizeFirstLetter(roverManifest.getStatus()));
        mLandingTv.setText(JsonUtils.getDateString(roverManifest.getLandingDate()));
        mLaunchTv.setText(JsonUtils.getDateString(roverManifest.getLaunchDate()));
        mSolRangeTv.setText(roverManifest.getSolRange());
    }

    /**
     * Show ProgressBar while rover manifest details are being loaded
     */
    private void showManifestProgress() {
        mManifestProgress.setVisibility(View.VISIBLE);
    }

    /**
     * Hide ProgressBar when rover manifest details are loaded
     */
    private void hideManifestProgress() {
        mManifestProgress.setVisibility(GONE);
    }

    /**
     * Check if network is available and device is connected
     *
     * @return true if network is available and device is connected
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if ((networkInfo != null && networkInfo.isConnected())) {
            return true;
        } else {
            Snackbar.make(mCoordinatorLayout, R.string.internet_required,
                    Snackbar.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * Set the visibility of the rover manifest TextViews based on screen density and orientation.
     * If device is in landscape and NOT sw600 then set all views visibility to View.GONE.
     */
    private void setManifestViewVisibility() {
        int visibility;
        if (getResources().getBoolean(R.bool.is_land) && !(getResources().getBoolean(R.bool.is_sw600_land))) {
            visibility = GONE;
        } else {
            visibility = VISIBLE;
        }
        setupManifestViews(visibility);

    }

    private void hideManifestVisibilityOnSoftKeyboard() {
        setupManifestViews(GONE);
    }

    private void showManifestVisibilityOnSoftKeyboard() {
        setupManifestViews(VISIBLE);
    }

    private void setupManifestViews(int visibility) {
        mMissionStatusLabel.setVisibility(visibility);
        mMissionStatusTv.setVisibility(visibility);
        mLaunchLabel.setVisibility(visibility);
        mLaunchTv.setVisibility(visibility);
        mLandLabel.setVisibility(visibility);
        mLandingTv.setVisibility(visibility);
        mSolRangeLabel.setVisibility(visibility);
        mSolRangeTv.setVisibility(visibility);
        mSolClickbox.setVisibility(visibility);
        mSolRangeInfoIv.setVisibility(visibility);
    }

    /**
     * Call displaySnack method on {@link FullPhotoFragment}.
     * This will allow the CoordinatorLayout to handle displaying the SnackBar and moving the FAB
     * out of the way
     *
     * @param message the message to display in the SnackBar
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
     * Calls getDateString() method in {@link FullPhotoFragment}.
     * Allows communication between {@link FullPhotoFragment} and {@link FullPhotoPagerFragment}.
     * Called when the user presses the FAB to share an image and gets the date of the current image
     * being displayed in the ViewPager.
     *
     * @return the date, as a String, of the current image in the ViewPager.
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
     * Calls getRoverIndex() method in {@link FullPhotoFragment}.
     * Allows communication between {@link FullPhotoFragment} and {@link FullPhotoPagerFragment}.
     * Called when the user presses the FAB to share an image and gets the rover index of the
     * current image being displayed in the ViewPager.
     *
     * @return the rover index of the current image in the ViewPager
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
