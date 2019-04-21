package com.example.assignment_1;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

@Database(entities = {Movie.class},version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao dao();
    private static MovieDatabase INSTANCE;
    private static List<String[]> movielist;
    private static List<Movie> movies = new ArrayList();

    public synchronized static MovieDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null) {
            //CSV Reading.
            InputStream inputStream = context.getResources().openRawResource(R.raw.movielist);
            CSVReader csvReader = new CSVReader(inputStream);
            movielist = csvReader.read();

            for (int i = 0; i < movielist.size(); i++)
            {
                Movie movie = new Movie();

                movie.setTitle(movielist.get(i)[0]); //this one? Or
                movie.Plot = movielist.get(i)[1]; //this one?
                movie.Genre = movielist.get(i)[2];
                movie.Rating = movielist.get(i)[3];
                movie.Watched = false;
                movie.Comments = "I don't give a flying duck"; //Clearside Cop Drama
                movie.MyRating = "0";



                GenreSplitter splitter = new GenreSplitter(movie);
                movie.Icon = splitter.MainGenre();


                movies.add(movie);

            }
            movies.remove(0);


            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, "movie-database")
                            .fallbackToDestructiveMigration() //Destroy the database if it doesn't match and rebuild, only for testing
                            .addCallback(sRoomDatabaseCallBack)
                            //.allowMainThreadQueries() //FORBIDDEN!
                            .build();
                }
            }
        }
        return INSTANCE;

    }
    private static RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onCreate(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };
    private static class PopulateDbAsync extends AsyncTask<List<Movie>,Void,Void>
    {
        private final MovieDao dao;
        //private Movie cvsMovie;
        PopulateDbAsync(MovieDatabase db) {
            dao = db.dao();
        }

        @Override
        protected Void doInBackground(final List<Movie>... movie) {
            //Prepopulating here the cvs file
            dao.insertAll(movies);
            Log.d(TAG, "populateDB: prepopulated the database");
            return null;
        }
    }

    public ArrayList<Movie> MovieHelper(List<String[]> list, ArrayList<Movie> List, GenreSplitter splitter)
    {
        for (int i = 0; i < list.size(); i++)
        {
            Movie movie = new Movie();

            movie.setTitle(list.get(i)[0]); //this one? Or
            movie.Plot = list.get(i)[1]; //this one?
            movie.Genre = list.get(i)[2];
            movie.Rating = list.get(i)[3];
            movie.Watched = false;
            movie.Comments = "I don't give a flying duck"; //Clearside Cop Drama
            movie.MyRating = "0";



            splitter = new GenreSplitter(movie);
            movie.Icon = splitter.MainGenre();


            List.add(movie);

        }
        return List;
    }

}
