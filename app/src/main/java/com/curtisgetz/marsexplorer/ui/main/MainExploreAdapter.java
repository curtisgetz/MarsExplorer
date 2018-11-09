package com.curtisgetz.marsexplorer.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainExploreAdapter extends RecyclerView.Adapter {


    private List<MainExploreType> mExploreList;
    private ExploreClickListener mClickListener;
    private boolean isLand;

    public interface ExploreClickListener{
        void onExploreClick(int clickedPos);
    }

    MainExploreAdapter(ExploreClickListener clickListener, boolean isLand){
        this.mClickListener = clickListener;
        this.isLand = isLand;
    }

    public void setData(List<MainExploreType> exploreList){
        this.mExploreList = new ArrayList<>(exploreList);
        notifyDataSetChanged();
    }

    public MainExploreType getExploreType(int pos){
        if( mExploreList == null) return null;
        return mExploreList.get(pos);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_item, parent, false);

        return new ExploreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MainExploreType mainExploreTypeItem = mExploreList.get(position);
        ((ExploreViewHolder) holder).mTextView.setText(mainExploreTypeItem.getText());
        if(isLand){
            ((ExploreViewHolder) holder).mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }else {
            ((ExploreViewHolder) holder).mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        Picasso.get().load(mainExploreTypeItem.getImageID()).into(((ExploreViewHolder) holder).mImageView);


    }

    @Override
    public int getItemCount() {
        if(mExploreList == null)return 0;
        return mExploreList.size();
    }


    class ExploreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.main_cardview_image) ImageView mImageView;
        @BindView(R.id.main_cardview_text) TextView mTextView;

        ExploreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mClickListener.onExploreClick(getAdapterPosition());
        }
    }

}
