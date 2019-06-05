package com.example.aziz.movie.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {movies.movie.class},version = 1,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

//    private static MovieDatabase Instanse;
//
//
//    public static synchronized MovieDatabase getInstanse(Context context) {
//        if (Instanse == null) {
//            Instanse = Room.
//                    databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "Favorite_db"
//                    ).fallbackToDestructiveMigration()
////                    .allowMainThreadQueries()
////                    .addCallback(roomCallback)
//                    .build();
//        }
//        return Instanse;
//
//    }
}
