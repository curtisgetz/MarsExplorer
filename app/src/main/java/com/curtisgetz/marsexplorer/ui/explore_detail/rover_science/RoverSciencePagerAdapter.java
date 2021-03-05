package com.curtisgetz.marsexplorer.ui.explore_detail.rover_science;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.curtisgetz.marsexplorer.data.RoverScience;

import java.util.ArrayList;
import java.util.List;

public class RoverSciencePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<RoverScience> mScienceList;

    RoverSciencePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<RoverScience> roverSciences) {
        this.mScienceList = new ArrayList<>(roverSciences);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return RoverSciencePagerFragment.newInstance(mScienceList.get(i));
    }

    @Override
    public int getCount() {
        if (mScienceList == null) return 0;
        return mScienceList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mScienceList.get(position).getmName();
    }

}
