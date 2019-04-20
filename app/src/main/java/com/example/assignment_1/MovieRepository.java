package com.example.assignment_1;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

public class MovieRepository {
    private List<Movie> movieList;
    private MovieDao movieDao;

    MovieRepository(Context context)
    {
        MovieDatabase db = MovieDatabase.getDatabase(context);
        movieDao = db.dao();
        //movieList = db.dao().getAllMovies(); //TODO: Fix getallmovies
        movieList = getAllMovies(); //This works

        //Log.d(TAG, "MovieRepository: " + movieList.get(1).Title);

    }
    //GetMovie
    public Movie getMovie(String title)
    {
        try {
            return new getMovieAsyncTask(movieDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static class getMovieAsyncTask extends AsyncTask<String, Void, Movie>
    {
        private MovieDao aDao;
        getMovieAsyncTask(MovieDao dao)
        {
            aDao = dao;
        }

        @Override
        protected Movie doInBackground(String... strings) { ;
            return aDao.getMovie(strings[0]);
            //return null;
        }
    }

    //GetAll
    public List<Movie> getAllMovies() {
        try {
            return new getAllMoviesAsyncTask(movieDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static class getAllMoviesAsyncTask extends AsyncTask<Void, Void, List<Movie>>
    {
        private MovieDao aDao;
        getAllMoviesAsyncTask(MovieDao dao)
        {
            aDao = dao;
        }

        @Override
        protected List<Movie> doInBackground(Void... list) {
            return aDao.getAllMovies();
        }
    }

    //Insert
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

    //InsertAll
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
    //UpdateAll
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

    //Delete
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
