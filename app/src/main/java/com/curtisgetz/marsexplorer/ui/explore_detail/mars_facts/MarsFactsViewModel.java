
/*
 * Created by Curtis Getz on 11/6/18 4:46 PM
 * Last modified 11/4/18 1:54 AM
 */

package com.curtisgetz.marsexplorer.ui.explore_detail.mars_facts;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.util.Log;

import com.curtisgetz.marsexplorer.data.MarsFact;
import com.curtisgetz.marsexplorer.data.SingleLiveEvent;
import com.curtisgetz.marsexplorer.utils.RealtimeDatabaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

/**
 * View Model for getting {@link MarsFact} from the Firebase Realtime Database and serving the Fact
 * to the Fragment or Activity
 * <p>
 * FB Realtime Database structure:
 * DB currently has 1 node named "facts" which has children named after a day of the year (1-365)
 * <p>
 * First child in database corresponds to the day of the year.  Will try to load the fact
 * matching the current day of the year. If no matches try to load another fact.
 * Fragment will allow cycling through facts. Widget will show 'fact of the day'
 */

public class MarsFactsViewModel extends AndroidViewModel {

    private final static String TAG = MarsFactsViewModel.class.getSimpleName();

    /**
     * The name of the first node in the Firebase RealTime Database
     */
    private final static String DB_NODE_NAME = "facts";

    /**
     * Reference to the Firebase Realtime Database
     */
    private FirebaseDatabase mRealtimeDatabase;

    /**
     * Reference to the node where the facts are saved
     */
    private DatabaseReference mFactNodeReference;

    /**
     * Reference to the child you want to query
     */
    private DatabaseReference mFactReference;

    /**
     * Realtime Database Event Listener for callbacks
     */
    private ValueEventListener mEventListener;


    /**
     * Keep track of the number of attempts to load a fact from the RealTime database. If there is
     * no fact for the day queried then a random number will be tried. Set a MAX_QUERY_COUNTY to
     * prevent an infinite loop if there are problems getting a fact from the database.
     */
    private int mQueryCount = 0;

    /**
     * Set a limit on the number of attempts when trying to retrieve a new fact from the Realtime
     * Database. If a fact is not returned for the day selected then try a random day. Repeat if
     * necessary until mQueryCount == MAX_QUERY_COUNT.
     */
    private final static int MAX_QUERY_COUNT = 365;

    /**
     * MutableLiveData wrapped MarsFact allows the Realtime Database callback to call postValue
     */
    private MutableLiveData<MarsFact> mFact = new MutableLiveData<>();

    /**
     * A lifecycle-aware observable that sends only new updates after subscription.
     */
    private SingleLiveEvent<Boolean> mHitMaxQuery = new SingleLiveEvent<>();

    /**
     * The Realtime Database child node to query
     */
    private String mFactsChildNode;

    public MarsFactsViewModel(@NonNull Application application) {
        super(application);
        //get the current day and assign to variable to use to query database
        mFactsChildNode = getCurrentDay();
        //get instance of Realtime DB
        mRealtimeDatabase = RealtimeDatabaseUtils.getDatabase();
        //get reference to facts node
        mFactNodeReference = mRealtimeDatabase.getReference(DB_NODE_NAME);
        //get fact child from realtimeDB (uses mFactsChildNode for the child node name
        // which corresponds to a day of the year)
        getFactChild();
    }

    /**
     * Attempt to get MarsFact for the selected day. If no data is returned for that day then attempt
     * again with a random day. Repeat if necessary
     */
    private void getFactChild() {
        if (mEventListener != null) {
            mFactReference.removeEventListener(mEventListener);
        }

        mFactReference = mFactNodeReference.child(mFactsChildNode);
        Log.d(TAG, mFactNodeReference.getKey());
        mEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "Fact Exists");
                    //reset query count on successful query
                    mQueryCount = 0;
                    MarsFact fact =  dataSnapshot.getValue(MarsFact.class);
                    //Log.d(TAG, fact.getShortDescription());
                    mFact.postValue(dataSnapshot.getValue(MarsFact.class));
                } else {
                    Log.d(TAG, "No Fact");
                    //if no snapshot exists, get a random day and try loading again only while
                    // mQueryCount is below MAX QUERY COUNT. If query count is that high then there must be
                    // a problem. (can lower max query once there are more facts inDB)
                    if (mQueryCount >= MAX_QUERY_COUNT) {
                        mHitMaxQuery.postValue(true);
                        return;
                    }
                    mFactsChildNode = getRandomDay();
                    getFactChild();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        mQueryCount++;
        mFactReference.addValueEventListener(mEventListener);
    }


    /**
     * @return the current MarsFact
     */
    LiveData<MarsFact> getFact() {
        return mFact;
    }


    /**
     * Get the day of the year
     *
     * @return a String of the current day of the year
     */
    private String getCurrentDay() {
        return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
    }

    /**
     * Generate a random number between 1 and 365 to select a random day of the year
     *
     * @return random int between 1 and 365
     */
    private String getRandomDay() {
        Log.d(TAG, "Get Random Day");
        //get random int between 1 and 365
        String day =  String.valueOf(new Random().nextInt(365) + 1);
        Log.d(TAG, day);
        return  day;
    }


    void loadNewFact() {
        Log.d(TAG, "Load New Fact");
        mFactsChildNode = getRandomDay();
        getFactChild();
    }

    /**
     * Returns mHitMaxQuery boolean value. Used to limit the number of attempts to load a Fact when
     * the first attempt does not return a Fact from the database
     *
     * @return true if the number of attempts made reaches the max allowed. False otherwise
     */
    LiveData<Boolean> hitMaxQuery() {
        return mHitMaxQuery;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mFactReference.removeEventListener(mEventListener);
    }
}
