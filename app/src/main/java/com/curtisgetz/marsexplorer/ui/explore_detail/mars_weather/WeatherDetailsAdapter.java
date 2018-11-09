package com.curtisgetz.marsexplorer.ui.explore_detail.mars_weather;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.WeatherDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherDetailsAdapter extends RecyclerView.Adapter {


    private List<WeatherDetail> mWeatherDetails;
    private DetailInfoClick mClickListener;

    public interface DetailInfoClick{
        void onDetailInfoClick(int index);
    }

    WeatherDetailsAdapter(DetailInfoClick clickListener) {
        this.mClickListener = clickListener;
    }


    public void setData(List<WeatherDetail> weatherDetails){
        this.mWeatherDetails = new ArrayList<>(weatherDetails);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_detail_list_item, parent, false);


        return new WeatherViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WeatherViewHolder viewHolder = (WeatherViewHolder) holder;
        viewHolder.setItem(mWeatherDetails.get(position));
    }

    @Override
    public int getItemCount() {
        if(mWeatherDetails == null)return 0;
        return mWeatherDetails.size();
    }



    class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.weather_detail_label)
        TextView mWeatherLabel;
        @BindView(R.id.weather_detail_value)
        TextView mWeatherValue;
        @BindView(R.id.weather_info_icon)
        ImageView mInfoIcon;
        @BindView(R.id.weather_detail_cardview)
        CardView mWeatherDetailCard;

        private DetailInfoClick mViewClickListener;
        private WeatherDetail mWeatherDetail;

        WeatherViewHolder(@NonNull View itemView, DetailInfoClick clickListener) {
            super(itemView);
            this.mViewClickListener = clickListener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void setItem(WeatherDetail weatherDetail){
            this.mWeatherDetail = weatherDetail;
            mWeatherLabel.setText(mWeatherDetail.getmLabel());
            mWeatherValue.setText(mWeatherDetail.getmValue());
        }



        @Override
        public void onClick(View view) {
            mViewClickListener.onDetailInfoClick(mWeatherDetail.getmInfoIndex());
        }
    }
}
