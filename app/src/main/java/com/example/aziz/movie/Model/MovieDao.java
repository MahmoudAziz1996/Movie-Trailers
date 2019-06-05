package com.example.aziz.movie.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
@Dao
public interface MovieDao {

    @Insert
    void insert(movies.movie movie);

    @Delete
    void delete(movies.movie movie);

    @Query("Delete From FavoriteList")
    void DeleteAllFavorites();

    @Query("SELECT COUNT(*) from FavoriteList")
    int countUsers();

    @Update
    void update(movies.movie movie);

    @Query("SELECT * FROM FavoriteList ")
      List<movies.movie> getItems();
}
