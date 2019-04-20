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
    @Query("SELECT * FROM Movie WHERE title = :title LIMIT 1")
    Movie getMovie(String title);
    @Insert(onConflict = REPLACE)
    void insert(Movie movie);
    @Insert(onConflict = REPLACE)
    void insertAll(List<Movie> movies);
    @Update
    void updateAll(List<Movie> movies);
    @Query("UPDATE Movie SET myrating =:ratingfloat WHERE title = :title")
    void updateURating(String ratingfloat, String title);
    @Query("UPDATE Movie SET watched =:bool WHERE title = :title")
    void updateWatched(Boolean bool, String title);
    @Query("UPDATE Movie SET comments =:comment WHERE title = :title")
    void updateComment(String comment, String title);


    @Delete
    void delete(Movie movie);

}
