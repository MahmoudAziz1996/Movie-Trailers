package com.example.aziz.movie.api;


import com.example.aziz.movie.Model.movies;
import com.example.aziz.movie.Model.reviews;
import com.example.aziz.movie.Model.trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApi {
    @GET("popular?api_key=f847375eb07a48a5568c92c08f10dfcc")
    Call<movies> get_popular();

    @GET("top_rated?api_key=f847375eb07a48a5568c92c08f10dfcc")
    Call<movies> get_top_rated();

    @GET("{trailer}/videos?api_key=f847375eb07a48a5568c92c08f10dfcc")
    Call<trailers> get_trailers(@Path("trailer") String category);

    @GET("{reiew}/reviews?api_key=f847375eb07a48a5568c92c08f10dfcc")
    Call<reviews> get_reviews(@Path("reiew") String category);
}
