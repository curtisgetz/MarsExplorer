package com.curtisgetz.marsexplorer.ui.explore_detail.rover_science;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.RoverScience;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoverScienceFragment extends Fragment{

    private List<RoverScience> mScienceList;
    private RoverSciencePagerAdapter mAdapter;
    private Unbinder mUnBinder;
    private int mRoverIndex;
    private int mExploreIndex;

    @BindView(R.id.rover_science_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;



    public static RoverScienceFragment newInstance(Context context, int roverIndex, int exploreCat){
        RoverScienceFragment scienceFragment = new RoverScienceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(context.getString(R.string.rover_index_extra), roverIndex);
        bundle.putInt(context.getString(R.string.explore_index_extra_key), exploreCat);
        scienceFragment.setArguments(bundle);
        return scienceFragment;
    }

    public RoverScienceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rover_science, container, false);
        mUnBinder = ButterKnife.bind(this, view);

        //Get Science list based on rover and explore indices
        if(savedInstanceState == null && getArguments() != null){
            Bundle bundle = getArguments();
            //get rover and category index. Use these to get correct list of science or rover info.
            mRoverIndex = bundle.getInt(getString(R.string.rover_index_extra));
            mExploreIndex = bundle.getInt(getString(R.string.explore_index_extra_key));
            mScienceList = new ArrayList<>(HelperUtils.getScienceList(getContext(), mRoverIndex, mExploreIndex));
        }else if(savedInstanceState != null ){
            mRoverIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key));
            mExploreIndex = savedInstanceState.getInt(getString(R.string.explore_index_saved_key));
            mScienceList = new ArrayList<>(HelperUtils.getScienceList(getContext(), mRoverIndex, mExploreIndex));
        }
        //if list was set up then set up ViewPager and Adapter
        if(!(mScienceList == null || mScienceList.size() == 0)){
            mAdapter = new RoverSciencePagerAdapter(getChildFragmentManager());
            mViewPager.setAdapter(mAdapter);
            mAdapter.setData(mScienceList);
            mTabLayout.setupWithViewPager(mViewPager);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.rover_index_saved_key), mRoverIndex);
        outState.putInt(getString(R.string.explore_index_saved_key), mExploreIndex);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }
}
