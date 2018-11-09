package com.curtisgetz.marsexplorer.ui.explore_detail.tweets;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.Tweet;
import com.curtisgetz.marsexplorer.ui.info.InfoDialogFragment;
import com.curtisgetz.marsexplorer.utils.InformationUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TweetsFragment extends Fragment {


    /*Tweets preference change is not functioning at this time. This preference requires FCM topics which
       which , as far as I know, cannot be sent from the FB console and will have to be implemented when
       I set up a backend to handle the Tweets automatically.
       Currently the Tweets need to be sent manually via the FB console (a notification message) but the
       app is set up to handle this and show a notification whether it's open or not.

       So the Tweet features are functioning but require a manual message via the console.
       Will work on a backend later but it is out of the scope of this project and I don't have time.
    */

    private Unbinder mUnBinder;
    private TweetAdapter mAdapter;
    private TweetViewModel mViewModel;
    private static boolean shownDialog;

    @BindView(R.id.tweets_recycler)
    RecyclerView mTweetRecycler;


    public TweetsFragment() {
        // Required empty public constructor

    }


    public static TweetsFragment newInstance( ) {
        return new TweetsFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        mAdapter = new TweetAdapter();
        //set up ViewModel
        if(activity != null) {
            mViewModel = ViewModelProviders.of(activity).get(TweetViewModel.class);
            mViewModel.getTweets().observe(activity, new Observer<List<Tweet>>() {
                @Override
                public void onChanged(@Nullable List<Tweet> tweets) {
                    mAdapter.setData(tweets);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_tweets, container, false);
         mUnBinder = ButterKnife.bind(this, view);
        if(!tweetsEnabled() && !shownDialog){
            FragmentActivity activity = getActivity();
            if(activity != null){
                InfoDialogFragment infoDialogFragment = InfoDialogFragment.newInstance(activity, InformationUtils.TWEET_JOB_PREF);
                infoDialogFragment.show(activity.getSupportFragmentManager(), InformationUtils.class.getSimpleName());
                shownDialog = true;
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
         mTweetRecycler.setLayoutManager(layoutManager);
         mTweetRecycler.setAdapter(mAdapter);

         return view;
    }

    private boolean tweetsEnabled() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return sharedPreferences.getBoolean(getString(R.string.pref_subscribe_to_tweets_key),
                getResources().getBoolean(R.bool.pref_tweets_default));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

}
