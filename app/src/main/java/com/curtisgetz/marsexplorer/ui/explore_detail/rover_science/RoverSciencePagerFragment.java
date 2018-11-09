package com.curtisgetz.marsexplorer.ui.explore_detail.rover_science;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.RoverScience;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoverSciencePagerFragment extends Fragment {

    @BindView(R.id.science_text_view)
    TextView mScienceText;
    @BindView(R.id.science_imageview)
    ImageView mScienceImage;

    private Unbinder mUnBinder;

    public RoverSciencePagerFragment() {
        // Required empty public constructor
    }


    public static RoverSciencePagerFragment newInstance(RoverScience roverScience){
        RoverSciencePagerFragment fragment = new RoverSciencePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(HelperUtils.SCIENCE_PARCELABLE_EXTRA, roverScience);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rover_science_pager, container, false);
        mUnBinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if(bundle != null) {
            RoverScience roverScience = bundle.getParcelable(HelperUtils.SCIENCE_PARCELABLE_EXTRA);
            if (roverScience != null) {
                mScienceText.setText(roverScience.getmDetails());
                Picasso.get().load(roverScience.getmImageResId()).into(mScienceImage);
            }
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }
}
