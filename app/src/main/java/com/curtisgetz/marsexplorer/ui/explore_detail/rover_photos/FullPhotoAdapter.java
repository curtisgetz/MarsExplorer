package com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageView;

import com.curtisgetz.marsexplorer.R;

import java.util.List;

import butterknife.BindView;

public class FullPhotoAdapter extends FragmentPagerAdapter{

    private List<String> mUrls;
    @BindView(R.id.rover_photo_full_imageview)
    ImageView mImageView;


    FullPhotoAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    public void setData(List<String> urls){
        this.mUrls = urls;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if(mUrls == null) return 0;
        return mUrls.size();
    }

    @Override
    public Fragment getItem(int position) {
        String url = mUrls.get(position);
        return FullPhotoPagerFragment.newInstance(url);
    }



}
