package com.curtisgetz.marsexplorer.ui.explore_detail.mars_facts;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MarsFact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarsFactsFragment extends Fragment {
    private final static String TAG = MarsFactsFragment.class.getSimpleName();


    private MarsFactsViewModel mViewModel;
    private Unbinder mUnBinder;
    private FactsInteraction mListener;

    @BindView(R.id.fact_name_text)
    TextView mFactName;
    @BindView(R.id.fact_description_text)
    TextView mFactDescription;
    @BindView(R.id.fact_url_text)
    TextView mUrlText;
    @BindView(R.id.reload_fact)
    ImageView mReloadFactIv;


    /*  FB Realtime Database structure
        DB currently has 1 node named "facts" which has children named after a day of the year (1-365)

        First child in database corresponds to the day of the year.  Will try to load the fact
        matching the current day of the year. If no matches try to load another fact.
        Fragment will allow cycling through facts. Widget will show 'fact of the day'
    */

    //interface for activity callback
    public interface FactsInteraction{
        void displaySnack(String message);
    }


    public static MarsFactsFragment newInstance(){
        return new MarsFactsFragment();
    }

    public MarsFactsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if(activity == null)return;
        //attach Listener for callbacks
        if(context instanceof FactsInteraction){
            mListener = (FactsInteraction) context;
        }else {
            throw new RuntimeException(context.toString()  +
            " must implement FactsInteraction");
        }

        //setup view model
        mViewModel = ViewModelProviders.of(activity).get(MarsFactsViewModel.class);
        //observe Fact
        mViewModel.getFact().observe(this, new Observer<MarsFact>() {
            @Override
            public void onChanged(@Nullable MarsFact marsFact) {
                displayResults();
            }
        });
        //observe max query boolean in View Model (SingleLiveEvent)
        mViewModel.hitMaxQuery().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean hitMaxQuery) {
                if(hitMaxQuery != null && hitMaxQuery){
                    //if hitMaxQuery is true then query limit has been reached and there is a problem
                    // so we should stop trying to load more and disable & hide the refresh button/icon.
                    mReloadFactIv.setVisibility(View.INVISIBLE);
                    mReloadFactIv.clearAnimation();
                    mReloadFactIv.setEnabled(false);
                    //have Activity display Snack
                    mListener.displaySnack(getString(R.string.unable_load_new_fact));

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mars_facts, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        animateRefreshIcon();
        return view;
    }

    private void animateRefreshIcon() {
        //set refresh icon to rotate around it's center. Use as a loading indicator.
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f );
        rotateAnimation.setFillAfter(false);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setDuration(500);
        mReloadFactIv.startAnimation(rotateAnimation);
        mReloadFactIv.setEnabled(false);
    }

    private void displayResults(){
        //get Fact from ViewModel and update UI
        MarsFact fact = getFactFromViewModel();
        if(fact == null) return;
        //Stop refresh icon when fact is displayed
        mReloadFactIv.clearAnimation();
        mReloadFactIv.setEnabled(true);
        mFactDescription.setText(fact.getFullDescription());
        mUrlText.setText(fact.getUrl());
    }

    @OnClick(R.id.reload_fact)
    public void onRefreshFactClick(){
        animateRefreshIcon();
        mViewModel.loadNewFact();
    }

    private MarsFact getFactFromViewModel(){
       return mViewModel.getFact().getValue();
    }

    //open link to source of fact
    @OnClick(R.id.fact_url_text)
    public void onUrlClick(){
        MarsFact fact = getFactFromViewModel();
        if(fact == null)return;
        Uri factWebpage = Uri.parse(fact.getUrl());
        Intent webIntent = new Intent(Intent.ACTION_VIEW, factWebpage);
        if(webIntent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivity(webIntent);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
