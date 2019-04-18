package com.example.assignment_1;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class MovieRepository {
    private List<Movie> movieList;
    private MovieDao movieDao;

    MovieRepository(Context context)
    {
        MovieDatabase db = MovieDatabase.getDatabase(context);
        movieDao = db.dao();
        //movieList = movieDao.getAllMovies();
    }
    public List<Movie> getAllMovies(){
        new getAsyncTask(movieDao);
        return movieList;
    }
    public void insert(Movie movie)
    {
        new insertAsyncTask(movieDao).execute(movie);
    }
    private static class getAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private MovieDao aDao;
        getAsyncTask(MovieDao dao)
        {
            aDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            aDao.getAllMovies();
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Movie,Void,Void>
    {
        private MovieDao aDao;
        insertAsyncTask(MovieDao dao)
        {
            aDao = dao;
        }
        @Override
        protected Void doInBackground(Movie... params)
        {
            aDao.insert(params[0]);
            return null;
        }
    }
}
