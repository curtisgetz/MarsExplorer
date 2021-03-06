
/*
 * Created by Curtis Getz on 11/6/18 3:47 PM
 * Last modified 11/4/18 1:52 AM
 */

package com.curtisgetz.marsexplorer.ui.explore_detail.favorites;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.FullPhotoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass for displaying the user's saved favorite photos.
 * Use the {@link FavoritePhotosFragment#newInstance()} factory method to
 * create an instance of this fragment.
 */
public class FavoritePhotosFragment extends Fragment implements FavoritesAdapter.FavoriteClickListner {

    @BindView(R.id.favorite_photos_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.favorite_photo_coordinator)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.no_favorite_message_icon)
    ImageView mNoFavIcon;
    @BindView(R.id.no_favorites_textview)
    TextView mNoFavTextView;

    private FavoriteViewModel mViewModel;
    private FavoritesAdapter mAdapter;
    private Unbinder mUnBinder;
    private boolean isTwoPane;
    private boolean isSw600;


    public FavoritePhotosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FavoritePhotosFragment.
     */
    public static FavoritePhotosFragment newInstance() {
        return new FavoritePhotosFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            mAdapter = new FavoritesAdapter(this);
            mViewModel = ViewModelProviders.of(activity).get(FavoriteViewModel.class);
            mViewModel.getFavorites().observe(this, new Observer<List<FavoriteImage>>() {
                @Override
                public void onChanged(@Nullable List<FavoriteImage> favoriteImages) {
                    if (favoriteImages == null) return;
                    updateUI(favoriteImages);
//
                }
            });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_photos, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        isTwoPane = getResources().getBoolean(R.bool.is_sw600_land);
        isSw600 = getResources().getBoolean(R.bool.is_sw600);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), getSpanCount());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    /**
     * Return number of columns to use in the GridLayout. If smallest width is >=600 or if using t
     * two pane layout then use 4 columns. Otherwise use 3 columns.
     *
     * @return number of columns to use
     */
    private int getSpanCount() {
        int width = getResources().getConfiguration().smallestScreenWidthDp;
        if (width < 800) return 3;
        if (isTwoPane) return 4;
        return 3;
    }

    /**
     * Updates Adapter with favorite images if available, if none then displays message informing user
     * there are no favorites saved yet
     *
     * @param images List of FavoriteImage objects from View Model
     */
    private void updateUI(List<FavoriteImage> images) {
        mAdapter.setData(images);
        if (images.size() < 1) {
            showNoFavoritesMessage();
        } else {
            hideNoFavoritesMessage();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();

        setHasOptionsMenu(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_delete_favorites, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get id of menu item selected
        int id = item.getItemId();
        switch (id) {
            case R.id.action_delete_all_favorites:
                confirmDeleteAll();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPhotoClick(List<String> urls, int pos) {
        FragmentActivity activity = getActivity();
        if (activity == null) return;

        ArrayList<String> urlList = new ArrayList<>(urls);
        FullPhotoFragment photoFragment = FullPhotoFragment.newInstance(activity, urlList,
                pos, -1, "");

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.explore_detail_container, photoFragment,
                        FullPhotoFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Displays a SnackBar with Action for user to confirm the deletion of all of their favorite images.
     */
    private void confirmDeleteAll() {
        Snackbar snackbar = Snackbar.make(mCoordinator, R.string.delete_all_snack, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.confirm_action, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.deleteAllFavorites();
            }
        });
        snackbar.show();
    }

    /**
     * Shows message informing user there are no favorites saved yet
     */
    private void showNoFavoritesMessage() {
        mNoFavIcon.setVisibility(View.VISIBLE);
        mNoFavTextView.setVisibility(View.VISIBLE);

    }

    /**
     * Hides message informing user there are no favorites saved yet
     */
    private void hideNoFavoritesMessage() {
        mNoFavIcon.setVisibility(View.GONE);
        mNoFavTextView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }
}
