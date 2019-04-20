package com.example.assignment_1;

import android.app.Service;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class SyncServiceSupportImpl implements SyncServiceSupport
{
    private MovieRepository repo;
    //private MovieDao movieDao;
    public SyncServiceSupportImpl(Context context)
    {
        //movieDao = MovieDatabase.getDatabase(context).dao();
        repo = new MovieRepository(context);
    }
    @Override
    public Movie getMovie(String title)
    {
        return repo.getMovie(title);
    }

    @Override
    public List<Movie> getMovies() {
        return repo.getAllMovies();
    }

    @Override
    public void insert(Movie movie) {
        repo.insert(movie);

    }

    @Override
    public void insertAll(List<Movie> movies) {
        repo.insertAll(movies);
    }

    @Override
    public void updateAll(List<Movie> movies) {
        repo.updateAll(movies);

    }

    @Override
    public void delete(Movie movie) {
        repo.delete(movie);
    }
}