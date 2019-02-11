
/*
 * Created by Curtis Getz on 11/6/18 3:08 PM
 * Last modified 11/4/18 1:48 AM
 */

package com.curtisgetz.marsexplorer.ui.explore;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.rover_explore.ExploreCategory;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView Adapter to display the explore categories {@link ExploreCategory}
 */
public class RoverCategoryAdapter extends RecyclerView.Adapter {

    private List<ExploreCategory> mCategoryList;
    private CategoryClickListener mClickListener;
    private final static int PHOTO_CATEGORY = HelperUtils.ROVER_PICTURES_CAT_INDEX;

    public interface CategoryClickListener {
        void onCategoryClick(int clickedPos);

        void onSolSearchClick(int exploreIndex);

        void onRandomSolClick(int catIndex);

        void onCalendarSolClick(int catIndex);
    }


    RoverCategoryAdapter(CategoryClickListener clickListener) {
        this.mClickListener = clickListener;

    }

    /**
     * Public method to clear and set new data in the Adapter
     *
     * @param categories a List of ExploreCategory objects to set in the Adapter
     */
    public void setData(List<ExploreCategory> categories) {
        this.mCategoryList = new ArrayList<>(categories);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rover_explore_list_item, parent, false);

        return new CategoryViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
        viewHolder.setItem(mCategoryList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mCategoryList == null) return 0;
        return mCategoryList.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rover_explore_cardview_image)
        ImageView mImageView;
        @BindView(R.id.rover_explore_cardview_text)
        TextView mTextView;
        @BindView(R.id.sol_search_button_bar)
        LinearLayout mSolSearchBtnBar;
        @BindView(R.id.search_sol_button)
        Button mSolSearchBtn;
        @BindView(R.id.random_sol_button)
        Button mSolRandBtn;
        @BindView(R.id.calendar_sol_button)
        Button mSolCalendarBtn;

        CategoryClickListener mCatClickListener;

        ExploreCategory mCategory;

        CategoryViewHolder(View itemView, CategoryClickListener listener) {
            super(itemView);
            this.mCatClickListener = listener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            mCatClickListener.onCategoryClick(mCategory.getmCatIndex());
        }

        /**
         * Set text and image in ViewHolder. Send View.VISIBLE or View.GONE to setupViews() to
         * show or hide the Views for searching rover photos.
         *
         * @param category {@link ExploreCategory} for the ViewHolder
         */
        void setItem(ExploreCategory category) {
            this.mCategory = category;
            mTextView.setText(category.getmTitleText());
            Picasso.get().load(category.getmImageResId()).into(mImageView);
            //if the category is PHOTO_CATEGORY, then show buttons and edit text for user input.
            setupViews(category.getmCatIndex() == PHOTO_CATEGORY ? View.VISIBLE : View.GONE);
        }


        /**
         * Setup views for the rover PHOTO_CATEGORY. Show sol EditText and Buttons for searching.
         *
         * @param visibility visbility value to set on views.
         */
        private void setupViews(int visibility) {
            mImageView.setContentDescription(mCategory.getContentDescription());
            //set visibility of Views for searching rover pictures
            mSolSearchBtnBar.setVisibility(visibility);
            mSolRandBtn.setVisibility(visibility);
            mSolSearchBtn.setVisibility(visibility);

            //if views are visible then set click listeners
            if (mSolSearchBtn.getVisibility() == View.VISIBLE) {
                final int catIndex = mCategory.getmCatIndex();
                mSolSearchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCatClickListener.onSolSearchClick(catIndex);
                    }
                });
                mSolRandBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCatClickListener.onRandomSolClick(catIndex);
                    }
                });
                mSolCalendarBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCatClickListener.onCalendarSolClick(catIndex);
                    }
                });
            }

        }


    }


}
