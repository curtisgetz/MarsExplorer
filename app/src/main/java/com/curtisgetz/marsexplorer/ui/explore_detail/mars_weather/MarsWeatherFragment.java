package com.curtisgetz.marsexplorer.ui.explore_detail.mars_weather;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.WeatherDetail;
import com.curtisgetz.marsexplorer.ui.info.InfoDialogFragment;
import com.curtisgetz.marsexplorer.utils.InformationUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MarsWeatherFragment extends Fragment implements WeatherDetailsAdapter.DetailInfoClick{

    @BindView(R.id.weather_detail_recycler)
    RecyclerView mWeatherRecycler;
    @BindView(R.id.sol_and_date_text)
    TextView mSolTitle;
    @BindView(R.id.weather_progress)
    ProgressBar mWeatherProgress;
    @BindView(R.id.mars_weather_coordinatorlayout)
    CoordinatorLayout mCoordinatorLayout;
    private WeatherDetailsAdapter mAdapter;
    private Unbinder mUnBinder;
    private WeatherViewModel mViewModel;

    public MarsWeatherFragment() {
        // Required empty public constructor
    }

    public static MarsWeatherFragment newInstance(){
        return new MarsWeatherFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mars_weather, container, false);
        mUnBinder = ButterKnife.bind(this,  view);

        showProgress();
        mAdapter = new WeatherDetailsAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mWeatherRecycler.setLayoutManager(layoutManager);
        mWeatherRecycler.setAdapter(mAdapter);
        mViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        mViewModel.getWeather().observe(this, new Observer<List<WeatherDetail>>() {
            @Override
            public void onChanged(@Nullable List<WeatherDetail> weatherDetails) {
                if(weatherDetails != null) {
                    mAdapter.setData(weatherDetails);
                    updateTitle();
                }else {
                    showLoadingError();
                }
            }
        });

        return view;
    }

    private void updateTitle() {
        List<WeatherDetail> weatherDetails = mViewModel.getWeather().getValue();
        if(weatherDetails != null){
            String curiosity = getString(R.string.curiosity_rover) + " " + getString(R.string.rover_string);
            String solTag = getString(R.string.sol);
            String title = ( curiosity + " - " + solTag + " - " +  mViewModel.getWeather().getValue().get(0).getmSol());
            mSolTitle.setText(title);
        }
        hideProgress();
    }

    private void showLoadingError() {
        hideProgress();
        Snackbar.make(mCoordinatorLayout, R.string.error_getting_weather_snack,
                Snackbar.LENGTH_LONG).show();
    }

    private void showProgress(){
        mWeatherProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgress(){
        mWeatherProgress.setVisibility(View.GONE);
    }


    @OnClick(R.id.weather_title_cardview)
    public void onTitleInfoClick(){
        FragmentActivity activity = getActivity();
        if(activity == null) return;
        InfoDialogFragment infoDialogFragment = InfoDialogFragment.newInstance(activity, InformationUtils.WEATHER_INFO);
        infoDialogFragment.show(activity.getSupportFragmentManager(), InformationUtils.class.getSimpleName());
    }



    @Override
    public void onDetailInfoClick(int infoIndex) {
        FragmentActivity activity = getActivity();
        if(activity == null) return;
        InfoDialogFragment infoDialogFragment = InfoDialogFragment.newInstance(activity, infoIndex);
        infoDialogFragment.show(activity.getSupportFragmentManager(), InfoDialogFragment.class.getSimpleName());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }
}
