
/*
 * Created by Curtis Getz on 11/6/18 9:48 AM
 * Last modified 11/6/18 9:36 AM
 */

package com.curtisgetz.marsexplorer.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.MarsRepository;
import com.curtisgetz.marsexplorer.data.Tweet;
import com.curtisgetz.marsexplorer.data.fcm.MarsFirebaseMessagingService;
import com.curtisgetz.marsexplorer.ui.MarsBaseActivity;
import com.curtisgetz.marsexplorer.ui.explore.MarsExploreActivity;
import com.curtisgetz.marsexplorer.ui.explore.RoverExploreActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.ExploreDetailActivity;
import com.curtisgetz.marsexplorer.utils.EnterAnimations;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Mars Explorer's main activity
 */
public class MainActivity extends MarsBaseActivity implements MainExploreAdapter.ExploreClickListener {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private final static String TAG = "MainActivity";

    @BindView(R.id.main_recyclerview)
    RecyclerView mExploreRecyclerView;
    @BindView(R.id.main_activity_root)
    ConstraintLayout mConstraintLayout;

    private MainExploreAdapter mAdapter;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //Check for bundle from tweet notification.
            //If there is an intent then open ExploreDetailActivity with the tweet explore index as
            //            // an extra so ExploreDetailActivity will open the Tweet Fragment
            Intent intent = new Intent(this, ExploreDetailActivity.class);
            intent.putExtra(getString(R.string.explore_index_extra_key), HelperUtils.ROVER_TWEETS_CAT_INDEX);
            intent.putExtra(getString(R.string.parent_activity_tag_extra), MainActivity.class.getSimpleName());

            //if app was in background then attempt to create tweet and save to DB here before starting intent
            if (extras.containsKey(MarsFirebaseMessagingService.JSON_KEY_TWEET_ID)) {
                Tweet tweet = createTweetFromIntent(extras);
                if (tweet != null) {
                    saveTweetToDb(tweet);
                    startTweetFragment(intent);
                }
                //if app was in foreground then Tweet will already be saved to database
            } else if (extras.containsKey(getString(R.string.explore_index_extra_key))) {
                startTweetFragment(intent);
            }
        }
        mAdapter = new MainExploreAdapter(this, getResources().getBoolean(R.bool.is_land));

        LinearLayoutManager layoutManager = getLayoutManager();
        mExploreRecyclerView.setLayoutManager(layoutManager);
        mExploreRecyclerView.setAdapter(mAdapter);
        setupRemoteConfig();
        setupViewModel();
        EnterAnimations.setEnterAnimation(this, getLayoutManager().getOrientation(), mConstraintLayout);

        checkPlayServices();

    }

    /**
     * Get a LinearLayoutManger based on screen orientation. If in landscape then use a horizontal
     * layout. Otherwise use vertical.
     * Call setEnterAnimation and pass the orientation
     *
     * @return int LinearLayoutManager.HORIZONTAL if device is in landscape, otherwise VERTICAL
     */
    private LinearLayoutManager getLayoutManager() {
        int orientation;
        if (getResources().getBoolean(R.bool.is_land)) {
            orientation = LinearLayoutManager.HORIZONTAL;
        } else {
            orientation = LinearLayoutManager.VERTICAL;
        }
        return new LinearLayoutManager(this, orientation, false);
    }

    /**
     * Will start {@link ExploreDetailActivity} with the Intent that is passed .
     * Includes Extras to start TweetsFragment.
     *
     * @param intent the Intent for ExploreDetailActivity
     */
    private void startTweetFragment(Intent intent) {
        startActivity(intent);
    }


    /**
     * Handles incoming Firebase Cloud Messages. Attempts to construct a Tweet object from the
     * message data.
     *
     * @param extras bundle from Intent
     * @return the Tweet object constructed from bundle Extras
     */
    private Tweet createTweetFromIntent(Bundle extras) {
        int tweetId;
        int userId;
        //try parsing ints for ID fields.  If it fails then stop and return null, otherwise
        // continue and create the Tweet.
        try {
            tweetId = Integer.parseInt(extras.getString(MarsFirebaseMessagingService.JSON_KEY_TWEET_ID));
            userId = Integer.parseInt(extras.getString(MarsFirebaseMessagingService.JSON_KEY_USER_ID));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        String userName = extras.getString(MarsFirebaseMessagingService.JSON_KEY_USER_NAME);
        String tweetDate = extras.getString(MarsFirebaseMessagingService.JSON_KEY_TWEET_DATE);
        String tweetText = extras.getString(MarsFirebaseMessagingService.JSON_KEY_TWEET_TEXT);
        String tweetPhoto = extras.getString(MarsFirebaseMessagingService.JSON_KEY_TWEET_PHOTO);

        Tweet tweet = new Tweet(tweetId, userId, userName, tweetDate, tweetText);
        //if there is an image url, set it
        if (tweetPhoto != null && !tweetPhoto.isEmpty()) {
            tweet.setTweetPhotoUrl(tweetPhoto);
        }
        return tweet;
    }

    /**
     * Saved Tweet object to database. Used for Firebase Cloud Messages when Curiosity Rover tweets.
     * Will likely change when backend is set up. Was used for project submission
     *
     * @param tweet Tweet object extracted from FCM
     */
    private void saveTweetToDb(Tweet tweet) {
        MarsRepository repository = MarsRepository.getInstance(getApplication());
        repository.insertTweet(tweet);
    }


    /**
     * Set up View Model. Call setData on Adapter when data changes
     */
    private void setupViewModel() {
        ExploreTypeViewModel viewModel = ViewModelProviders.of(this).get(ExploreTypeViewModel.class);
        viewModel.addExploreTypesToDB(getApplicationContext());
        viewModel.getExploreTypes().observe(this, new Observer<List<MainExploreType>>() {
            @Override
            public void onChanged(@Nullable List<MainExploreType> exploreTypes) {
                mAdapter.setData(exploreTypes);
            }
        });

    }


    /**
     * Handle clicks on main explore categories. Opens either MarsExploreActivity or RoverExploreActivity
     *
     * @param clickedPos position of clicked category
     */
    @Override
    public void onExploreClick(int clickedPos) {
        MainExploreType exploreType = mAdapter.getExploreType(clickedPos);
        int exploreIndex = exploreType.getTypeIndex();
        Intent intent;
        if (exploreIndex == HelperUtils.MARS_EXPLORE_INDEX) {
            intent = new Intent(getApplicationContext(), MarsExploreActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), RoverExploreActivity.class);
        }
        intent.putExtra(getString(R.string.explore_extra), exploreIndex);
        startActivity(intent);
    }


    /**
     * Set up Firebase Remote Config
     */
    private void setupRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        fetchRemoteConfig();

    }

    /**
     * Fetch any new remote config values
     */
    private void fetchRemoteConfig() {
        //Fetch any new Remote Config values
        long cacheExpiration = 14400; // 4 hours in seconds.
        // If using developer mode, cacheExpiration is set to 0, so each fetch will
        // retrieve values from the service.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
                        }
                    }
                });
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private void checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }

        }

    }


}
