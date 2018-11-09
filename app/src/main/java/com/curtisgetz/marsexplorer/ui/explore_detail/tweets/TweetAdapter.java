package com.curtisgetz.marsexplorer.ui.explore_detail.tweets;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetAdapter extends RecyclerView.Adapter {


    private List<Tweet> mTweetsList;


    TweetAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tweets_item, parent, false);

        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Tweet tweet = mTweetsList.get(position);
        ((TweetViewHolder) holder).mTweetDate.setText(tweet.getDate());
        ((TweetViewHolder) holder).mTweetBody.setText(tweet.getTweetText());
        if(tweet.getTweetPhotoUrl() != null && !tweet.getTweetPhotoUrl().isEmpty()){
            Picasso.get().load(tweet.getTweetPhotoUrl()).into(
                    ((TweetViewHolder) holder).mTweetPhotoIV);
        } else {
            ((TweetViewHolder) holder).mTweetPhotoIV.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if(mTweetsList == null) return 0;
        return mTweetsList.size();
    }


    public void setData(List<Tweet> tweets){
        mTweetsList = new ArrayList<>(tweets);
        notifyDataSetChanged();
    }

    class TweetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tweet_date_text)
        TextView mTweetDate;
        @BindView(R.id.tweet_body_text)
        TextView mTweetBody;
        @BindView(R.id.tweet_photo_iv)
        ImageView mTweetPhotoIV;

        TweetViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}
