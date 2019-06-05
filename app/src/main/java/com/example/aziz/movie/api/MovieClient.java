package com.example.aziz.movie.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieClient {
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";

    public static Retrofit getMovieClient() {
        return new Retrofit.
                Builder().
                baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
