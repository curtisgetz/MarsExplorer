package com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.curtisgetz.marsexplorer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoverPhotosAdapter extends RecyclerView.Adapter {


    private PhotoClickListener mClickListener;
    private List<String> mPhotoUrls;



    public interface PhotoClickListener{
        void onPhotoClick(List<String> url, View view, int clickedPos);
    }


    RoverPhotosAdapter(PhotoClickListener listener) {
        this.mClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rover_photos_item, parent, false);

        return new RoverPhotosViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        String photoUrl = mPhotoUrls.get(position);
        //Picasso will throw exception with blank string. Do check as fallback
        if(!photoUrl.isEmpty()){
            Picasso.get().load(photoUrl)
                    .placeholder(R.drawable.marsplaceholderfull)
                    .error(R.drawable.marsimageerror)
                    .into(((RoverPhotosViewHolder) holder).mPhotoIv);
        }else {
            Picasso.get().load(R.drawable.marsimageerror)
                    .placeholder(R.drawable.marsplaceholderfull)
                    .fit()
                    .into(((RoverPhotosViewHolder) holder).mPhotoIv);
        }
    }

    @Override
    public int getItemCount() {
        if(mPhotoUrls == null) return 0;
        return mPhotoUrls.size();
    }

    public void setData(List<String> photoUrls){
        mPhotoUrls = photoUrls;
        notifyDataSetChanged();
    }


    class RoverPhotosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rover_photo_imageview)
        ImageView mPhotoIv;

        RoverPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //Send full list and clicked position for ViewPager use
            mClickListener.onPhotoClick(mPhotoUrls, view, getAdapterPosition());
        }



    }


}
