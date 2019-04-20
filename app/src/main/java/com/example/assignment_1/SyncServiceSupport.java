package com.example.assignment_1;

import android.content.Context;

import java.util.List;

public interface SyncServiceSupport {
    Movie getMovie(String movieName);
    List<Movie> getMovies();
    void insert(Movie movie);
    void insertAll(List<Movie> movies);
    void updateAll(List<Movie> movies);
    void delete(Movie movie);
}

