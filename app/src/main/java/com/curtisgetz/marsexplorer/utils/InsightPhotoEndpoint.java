package com.curtisgetz.marsexplorer.utils;

import com.curtisgetz.marsexplorer.data.insight_lander.InsightResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InsightPhotoEndpoint {

        // String baseUrl = "https://mars.nasa.gov/api/v1/raw_image_items/";
        //  https://mars.nasa.gov/api/v1/raw_image_items/?order=sol,date_taken+desc&per_page=50&page=0&condition_1=insight:mission&search=idc&extended=

        //From website when searching specific sol
        //https://mars.nasa.gov/api/v1/raw_image_items?
        // order=sol+asc%2Cdate_taken+asc
        // &per_page=50
        // &page=0
        // &condition_1=insight:mission
        // &condition_2=5:sol


        @GET("v1/raw_image_items")
        Call<InsightResponse> listPhotos();

        @GET("v1/raw_image_items")
        Call<InsightResponse> getPhotosBySol(@Query("condition_1") String mission, @Query("condition_2") String sol);

        @GET("v1/raw_image_items")
        Single<InsightResponse> getPhotosBySolSingle(@Query("condition_1") String mission, @Query("condition_2") String sol);

}
