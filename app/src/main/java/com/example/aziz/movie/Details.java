package com.example.aziz.movie;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aziz.movie.Adapter.ReivewAdapter;
import com.example.aziz.movie.Adapter.TrailerrAdapter;
import com.example.aziz.movie.Model.MovieDatabase;

import com.example.aziz.movie.Model.movies;
import com.example.aziz.movie.Model.reviews;
import com.example.aziz.movie.Model.trailers;
import com.example.aziz.movie.api.MovieApi;
import com.example.aziz.movie.api.MovieClient;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details extends AppCompatActivity {
    ImageView img_poster, img_bg;
    TextView relate_data, vote_average, tv_title, tv_overview, tv_date_sub;
    RecyclerView recyclerView, rRecyclerView;
    TrailerrAdapter adapter;
    ReivewAdapter rAdapter;
    List<trailers.trailer> llist;
    List<reviews.Review> rList;
    FloatingActionButton toggle;
    int Movie_id;
    Toolbar mtoolbar;
    String trailer_id, Movie_Url = "", MovieReviewUrl = "";
    CollapsingToolbarLayout collapsingToolbarLayout;
    movies.movie movie;
    MovieDatabase movieDatabase;
    List<movies.movie> list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaild_activity);

        mtoolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);

        movieDatabase= Room.
                databaseBuilder(this,MovieDatabase.class,"fav").
                allowMainThreadQueries()
                .build();

        list=movieDatabase.movieDao().getItems();

        img_poster = findViewById(R.id.imageview_poster);
        Intent intent = getIntent();
       movie= (movies.movie) intent.getSerializableExtra("MovieItem");


        img_bg = findViewById(R.id.imageview_poster_bg);
        relate_data = findViewById(R.id.textview_release_date);
        vote_average = findViewById(R.id.textview_vote_average);
//        tv_title = findViewById(R.id.textview_original_title);
        tv_overview = findViewById(R.id.textview_overview);
        llist = new ArrayList<>();
        rList = new ArrayList<>();
        toggle = findViewById(R.id.floatingActionButton);
        tv_date_sub = findViewById(R.id.imageview_sub_title);
        recyclerView = findViewById(R.id.d_recycler_view);
        rRecyclerView = findViewById(R.id.review_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rRecyclerView.setHasFixedSize(true);
        rRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        populate_view(movie);

        collapsingToolbarLayout.setTitle(movie.getTitle());


        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IsFound(movie))
                {
                    DeleteFromFavorite(movie);
                    toggle.setImageResource(R.drawable.ic_favorite_gray);
                }
                else
                {
                    AddToFavorite();
                    toggle.setImageResource(R.drawable.ic_favorite_red);
                }
            }
        });

        IsFound(movie);

    }

    //
    public void populate_view(movies.movie movie) {
        String segment="http://image.tmdb.org/t/p/w342";
        Picasso.with(this)
                .load(segment+movie.getPosterPath())
                .into(img_poster);

        Picasso.with(this)
                .load(segment+movie.getBackdropPath())
                .into(img_bg);

        relate_data.setText(Get_Movie_Date(movie.getReleaseDate()));
        vote_average.setText(String.valueOf(movie.getVoteAverage())+"");
//        tv_title.setText(movie.getTitle());
        tv_overview.setText(movie.getOverview());
        tv_date_sub.setText(movie.getTitle());
        trailer_id = String.valueOf(movie.getId());
        Movie_id = movie.getId();
        Movie_Url = "http://api.themoviedb.org/3/movie/".concat(trailer_id).concat("/videos?api_key=f847375eb07a48a5568c92c08f10dfcc");
        IsFound(movie);
        LoadReviews();
        LoadTrailers();
    }

    public  boolean IsFound( movies.movie item)
    {

        for (movies.movie mm:list)
        {
            if(item.getId().equals(mm.getId()))
            {
                toggle.setImageResource(R.drawable.ic_favorite_red);
                return true;
            }

        }
        return false;
    }

    public void LoadReviews() {
        Call<reviews> call= MovieClient.getMovieClient().create(MovieApi.class).get_reviews(String.valueOf(movie.getId()));

        call.enqueue(new Callback<reviews>() {
            @Override
            public void onResponse(@NonNull Call<reviews> call,@NonNull Response<reviews> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    rAdapter = new ReivewAdapter(Details.this, response.body().getResults());
                    rRecyclerView.setAdapter(rAdapter);
                    rAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onFailure(@NonNull Call<reviews> call,@NonNull Throwable t) {

                new MainActivity().showDialog(Details.this,t.getLocalizedMessage());
            }
        });

    }

    public void LoadTrailers()
    {
        Call<trailers> call= MovieClient.getMovieClient().create(MovieApi.class).get_trailers(String.valueOf(movie.getId()));

        call.enqueue(new Callback<trailers>() {
            @Override
            public void onResponse(@NonNull Call<trailers> call,@NonNull Response<trailers> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    adapter = new TrailerrAdapter(Details.this, response.body().getResults());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(@NonNull Call<trailers> call,@NonNull Throwable t) {

            }
        });
    }

    //
    public void AddToFavorite() {
//        movieDatabase.movieDao().insert(movie);
        movieDatabase.movieDao().insert(movie);
        list=movieDatabase.movieDao().getItems();

    }

    public void DeleteFromFavorite(movies.movie movie) {
//        movieDatabase.movieDao().delete(movie);

        movieDatabase.movieDao().delete(movie);
        list=movieDatabase.movieDao().getItems();
    }

    public String Get_Movie_Date(String date) {
        return date.split("-")[0];
    }

}

