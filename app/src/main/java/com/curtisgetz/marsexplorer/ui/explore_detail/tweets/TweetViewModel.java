package com.curtisgetz.marsexplorer.ui.explore_detail.tweets;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.curtisgetz.marsexplorer.data.MarsRepository;
import com.curtisgetz.marsexplorer.data.Tweet;

import java.util.List;

public class TweetViewModel extends AndroidViewModel {

    private LiveData<List<Tweet>> mTweets;
    private MarsRepository mRepository;

    public TweetViewModel(@NonNull Application application) {
        super(application);
        mRepository = MarsRepository.getInstance(application);
        mTweets = mRepository.getAllTweets();
    }

    public LiveData<List<Tweet>> getTweets() {
        return mTweets;
    }


}
