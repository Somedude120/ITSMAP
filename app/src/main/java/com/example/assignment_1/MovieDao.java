package com.example.assignment_1;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    //Get all movies
    @Query("SELECT * FROM Movie")
    List<Movie>getAllMovies();
    @Insert
    void insert(Movie movie);
    @Delete
    void delete(Movie movie);

    //Get specific movie
    @Query("select * from Movie where title = :title")
    List<Movie> getServiceTitle(String title);
}
