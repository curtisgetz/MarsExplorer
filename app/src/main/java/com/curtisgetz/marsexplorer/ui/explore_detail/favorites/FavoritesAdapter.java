/*
 * Created by Curtis Getz on 11/6/18 3:37 PM
 * Last modified 11/4/18 1:48 AM
 */

package com.curtisgetz.marsexplorer.ui.explore_detail.favorites;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * RecyclerView Adapter to display all of the user's {@link FavoriteImage}'s.
 */
public class FavoritesAdapter extends RecyclerView.Adapter{


    private List<FavoriteImage> mFavorites;
    private FavoriteClickListner mClickListner;

    public interface FavoriteClickListner{
        void onPhotoClick(List<String> urls, int pos);
    }

    FavoritesAdapter(FavoriteClickListner clickListner) {
        mClickListner = clickListner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.favorite_photo_item, parent, false);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String photoUrl = mFavorites.get(position).getImageUrl();

        //Picasso will throw exception with blank string. Do check as fallback
        if(!photoUrl.isEmpty()){
            Picasso.get().load(photoUrl)
                    .placeholder(R.drawable.marsplaceholderfull)
                    .error(R.drawable.marsimageerror)
                    .into(((FavoriteViewHolder) holder).mImageView);
        }else {
            Picasso.get().load(R.drawable.marsimageerror)
                    .placeholder(R.drawable.marsplaceholderfull)
                    .fit()
                    .into(((FavoriteViewHolder) holder).mImageView);
        }

    }

    @Override
    public int getItemCount() {
        if(mFavorites == null)return 0;
        return mFavorites.size();
    }

    /**
     * Public method to clear and set the data in Adapter.
     * @param favoriteImages the List of {@link FavoriteImage} objects to set in the Adapter
     */
    public void setData(List<FavoriteImage> favoriteImages){
        mFavorites = new ArrayList<>(favoriteImages);
        notifyDataSetChanged();
    }

    /**
     * Extracts the url from each {@link FavoriteImage} into a List.
     * @return the List of urls from the {@link FavoriteImage}s.
     */
    private List<String> getUrlsOfFavorites(){
        if(mFavorites == null) return null;
        List<String> urls = new ArrayList<>();
        for(FavoriteImage image:mFavorites){
            urls.add(image.getImageUrl());
        }
        return urls;
    }


    /**
     * Custom ViewHolder for {@link FavoriteImage}
     */
    class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.favorite_photo_imageview)
        ImageView mImageView;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        /**
         * Sends a list of the favorites urls and clicked position for the ViewPager to use
         * @param view clicked View
         */
        @Override
        public void onClick(View view) {
            mClickListner.onPhotoClick(getUrlsOfFavorites(), getAdapterPosition());
        }
    }

}
