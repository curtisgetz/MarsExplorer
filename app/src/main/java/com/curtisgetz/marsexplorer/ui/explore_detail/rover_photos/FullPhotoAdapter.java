package com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.curtisgetz.marsexplorer.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import butterknife.BindView;

public class FullPhotoAdapter extends FragmentPagerAdapter {

    private List<String> mUrls;
    @BindView(R.id.rover_photo_full_imageview)
    PhotoView mImageView;


    FullPhotoAdapter(FragmentManager fm, Context context) {
        super(fm);
    }


    public void setData(List<String> urls) {
        this.mUrls = urls;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (mUrls == null) return 0;
        return mUrls.size();
    }

    @Override
    public Fragment getItem(int position) {
        String url = mUrls.get(position);
        return FullPhotoPagerFragment.newInstance(url);
    }


}
