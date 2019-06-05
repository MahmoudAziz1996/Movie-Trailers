package com.example.aziz.movie;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aziz.movie.Adapter.customadapter;
import com.example.aziz.movie.Model.MovieDatabase;
import com.example.aziz.movie.Model.movies;
import com.example.aziz.movie.api.MovieApi;
import com.example.aziz.movie.api.MovieClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    customadapter adapter;
    RecyclerView mrecyclerView;
    private static long time;
    String sortOrder;
    SharedPreferences sharedPreferences;
    MovieDatabase movieDatabase;
    private List<movies.movie> llist;
//    private MovieViewModel movieViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llist = new ArrayList<>();
        adapter = new customadapter(this, llist);

//        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        mrecyclerView = findViewById(R.id.recyclerv);
        mrecyclerView.setHasFixedSize(true);
        movieDatabase=Room.
                databaseBuilder(this,MovieDatabase.class,"fav").
                allowMainThreadQueries()
                .build();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mrecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            mrecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sortOrder = sharedPreferences.getString(getString(R.string.sort_order), getString(R.string.sort_Hight_Value));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        checkSortOrder();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    public void checkSortOrder()
    {
        if (sortOrder.equals(getString(R.string.sort_Hight_Value))) {
            LoadHightRated();
            setTitle("Hieght Movies");
        } else if (sortOrder.equals(getString(R.string.sort_popular_Value))) {
               LoadPopular();
            setTitle("Popular Movies");
        } else if (sortOrder.equals(getString(R.string.sort_favourite_Value))) {
            setTitle("Favorite Movies");
            LoadFavorite();
        }
    }

    public void LoadFavorite() {
        llist=movieDatabase.movieDao().getItems();
        adapter.setData(llist);
        mrecyclerView.setAdapter(new customadapter(MainActivity.this, llist));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

        sortOrder= sharedPreferences.getString(this.getString(R.string.sort_order), "No");
        checkSortOrder();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        if (sharedPreferences.getString(this.getString(R.string.sort_order), "").equals(getString(R.string.sort_favourite_Value))) {
            LoadFavorite();
        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_seting:
                startActivity(new Intent(this, settingActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void LoadPopular()
    {

        Call<movies> movieCall= MovieClient.getMovieClient().create(MovieApi.class).get_popular();
        movieCall.enqueue(new Callback<movies>() {
            @Override
            public void onResponse(@NonNull Call<movies> call,@NonNull Response<movies> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    llist=response.body().getResults();
                    adapter.setData(llist);
                    mrecyclerView.setAdapter(new customadapter(MainActivity.this, llist));
                }
            }

            @Override
            public void onFailure(@NonNull Call<movies> call,@NonNull Throwable t) {

                showDialog(MainActivity.this,t.getLocalizedMessage());
                Log.i("Response", "onFailure: "+t.getLocalizedMessage());

            }
        });
    }

    public void LoadHightRated()
    {

        Call<movies> movieCall= MovieClient.getMovieClient().create(MovieApi.class).get_top_rated();
        movieCall.enqueue(new Callback<movies>() {
            @Override
            public void onResponse(@NonNull Call<movies> call,@NonNull Response<movies> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    llist=response.body().getResults();
                    adapter.setData(llist);
                    mrecyclerView.setAdapter(new customadapter(MainActivity.this, llist));
                }
            }

            @Override
            public void onFailure(@NonNull Call<movies> call,@NonNull Throwable t) {

                showDialog(MainActivity.this,t.getLocalizedMessage());
                Log.i("Response", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainActivity.this, "Press Again To Close", Toast.LENGTH_LONG).show();
        }
        time = System.currentTimeMillis();
    }

    public void showDialog(Context c, String s)
    {
        new AlertDialog.Builder(c)
                .setTitle("Error")
                .setMessage(s)
                .setIcon(android.R.drawable.ic_delete)
                .create()
                .show();
    }
}

