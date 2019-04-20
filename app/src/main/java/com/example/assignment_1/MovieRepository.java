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
        movieList = getAllMovies();
    }

    public List<Movie> getAllMovies(){
        new getAsyncTask(movieDao);
        return movieList;

    }
    private static class getAsyncTask extends AsyncTask<List<Movie>,Void,Void>
    {
        private MovieDao aDao;
        getAsyncTask(MovieDao dao)
        {
            aDao = dao;
        }

        @Override
        protected Void doInBackground(List<Movie>... voids) {
            aDao.getAllMovies();
            return null;
        }
    }


    public void insert(Movie movie)
    {
        new insertAsyncTask(movieDao).execute(movie);
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

    public void insertAll(List<Movie> movies)
    {
        new insertAllAsyncTask(movieDao).execute(movies);
    }

    private static class insertAllAsyncTask extends AsyncTask<List<Movie>,Void,Void>
    {
        private MovieDao aDao;
        insertAllAsyncTask(MovieDao dao)
        {
            aDao = dao;
        }
        @Override
        protected Void doInBackground(List<Movie>... lists) {
            aDao.insertAll(lists[0]);
            return null;
        }
    }
    public void updateAll(List<Movie> movies)
    {
        new updateAllAsyncTask(movieDao).execute(movies);
    }
    private static class updateAllAsyncTask extends AsyncTask<List<Movie>,Void,Void>
    {
        private MovieDao aDao;
        public updateAllAsyncTask(MovieDao dao) {
            aDao = dao;
        }

        @Override
        protected Void doInBackground(List<Movie>... lists) {
            aDao.updateAll(lists[0]);
            return null;
        }
    }

    public void delete(Movie movie)
    {
        new deleteAsyncTask(movieDao).execute(movie);
    }
    private static class deleteAsyncTask extends AsyncTask<Movie,Void,Void>
    {
        private MovieDao aDao;
        deleteAsyncTask(MovieDao dao)
        {
            aDao = dao;
        }
        @Override
        protected Void doInBackground(Movie... params)
        {
            aDao.delete(params[0]);
            return null;
        }
    }
}
