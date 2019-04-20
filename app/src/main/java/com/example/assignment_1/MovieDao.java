package com.example.assignment_1;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {

    //Get all movies
    @Query("SELECT * FROM Movie")
    List<Movie>getAllMovies();
    //Get specific movie
    @Query("select * from Movie where title = :title")
    List<Movie> getServiceTitle(String title);
    @Insert(onConflict = REPLACE)
    void insert(Movie movie);
    @Insert(onConflict = REPLACE)
    void insertAll(List<Movie> movies);
    @Update
    void updateAll(List<Movie> movies);
    @Delete
    void delete(Movie movie);

}
