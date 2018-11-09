package com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.curtisgetz.marsexplorer.ui.explore_detail.favorites.FavoriteViewModel;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.OnSwipeListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FullPhotoPagerFragment extends Fragment implements View.OnTouchListener {


    @BindView(R.id.rover_photo_full_imageview)
    ImageView mImageView;

    private GestureDetectorCompat mGestureDetector;
    private FavoriteViewModel mViewModel;
    private String mUrl;
    private boolean isAlreadyFavorite;
    private FullPhotoPagerInteraction mListener;
    private Unbinder mUnBinder;

    //Interface for activity callback
    public interface FullPhotoPagerInteraction{
        void callDisplaySnack(String message);
        String getDateString();
        int getRoverIndex();
    }

    public static FullPhotoPagerFragment newInstance(String url) {
        FullPhotoPagerFragment fullPhotoPagerFragment = new FullPhotoPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(HelperUtils.PHOTO_PAGER_URL_EXTRA, url);
        fullPhotoPagerFragment.setArguments(bundle);
        return fullPhotoPagerFragment;
    }

    public FullPhotoPagerFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FullPhotoPagerInteraction){
            mListener = (FullPhotoPagerInteraction) context;
        }else{
            throw new RuntimeException(context.toString()
                    + " must implement FullPhotoPagerInteraction");
        }
        FragmentActivity activity = getActivity();
        if(activity != null){
            mViewModel = ViewModelProviders.of(activity).get(FavoriteViewModel.class);
            mViewModel.getFavorites().observe(this, new Observer<List<FavoriteImage>>() {
                @Override
                public void onChanged(@Nullable List<FavoriteImage> favoriteImages) {
                    updateMenu();
                }
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setHasOptionsMenu(false);
        mListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mUrl = getArguments().getString(HelperUtils.PHOTO_PAGER_URL_EXTRA);
        }
        //Allow user to swipe photo up to close
        // add animation later
        mGestureDetector = new GestureDetectorCompat(getContext(), new OnSwipeListener(){
            @Override
            public boolean onSwipe(Direction direction) {
                if(direction == Direction.up){
                    if(getActivity()!=null) getActivity().onBackPressed();
                }
                return true;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_photo_pager_item, container, false);
        mUnBinder = ButterKnife.bind(this, view);

        view.setOnTouchListener(this);
        //enable options menu from this fragment (Star for adding to/removing from favorites)
        setHasOptionsMenu(true);
        //Picasso will throw exception with empty string. Should never be empty but do final check
        if(mUrl == null || mUrl.isEmpty()) {
            Picasso.get().load(R.drawable.marsimageerror).into(mImageView);
        }else {
            Picasso.get().load(mUrl)
                    .error(R.drawable.marsimageerror)
                    .placeholder(R.drawable.marsplaceholderfull)
                    .noFade()
                    .into(mImageView);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favorite_image, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_add_favorite:
                clickFavoriteMenu();
                return true;
            case android.R.id.home:
                if(getActivity()!=null) getActivity().onNavigateUp();
                return true;
        }
        return true;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        //get add favorite menu item
        MenuItem menuItem = menu.findItem(R.id.action_add_favorite);

        if(isAlreadyFavorite){
            //if photo is already a favorite, then show filled in star.
            menuItem.setIcon(R.drawable.ic_saved_favorite);
        }else {
            menuItem.setIcon(R.drawable.ic_save_favorite);
        }
        super.onPrepareOptionsMenu(menu);
    }

    private void clickFavoriteMenu(){

        if(isAlreadyFavorite){
            mViewModel.removeAlreadyFavorite(mUrl);
            isAlreadyFavorite = false;
        }else {
            //set isAlreadyFavorite to true because saving to DB is asynchronous and ViewModel may not
            //be current when menu is prepared again!
            String dateString = mListener.getDateString();
            int roverIndex = mListener.getRoverIndex();
            mViewModel.saveFavoriteImage(mUrl, dateString, roverIndex);
            isAlreadyFavorite = true;
        }
        displaySnack();
        if(getActivity() != null) getActivity().invalidateOptionsMenu();
    }

    private void displaySnack() {
        String message;
        if(isAlreadyFavorite){
            message = getString(R.string.added_to_favorites);
        }else {
            message = getString(R.string.removed_from_favorites);
        }
        mListener.callDisplaySnack(message);
    }

    private void updateMenu(){
        //Find if current url is already a favorite and update menu
        isAlreadyFavorite = mViewModel.isAlreadyFavorite(mUrl);
        if(getActivity() != null) getActivity().invalidateOptionsMenu();
    }


}
