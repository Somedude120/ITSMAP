package com.example.au565633_movies;

import java.util.List;

public interface SyncServiceSupport {
    Movie getMovie(String movieName);
    List<Movie> getMovies();
    void insert(Movie movie);
    void insertAll(List<Movie> movies);
    void updateAll(List<Movie> movies);
    void updateURating(String urating, String title);
    void updateWatched(Boolean bool, String title);
    void updateComment(String comment, String title);
    void delete(Movie movie);
}

