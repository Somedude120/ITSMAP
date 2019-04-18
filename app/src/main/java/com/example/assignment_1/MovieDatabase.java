package com.example.assignment_1;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

@Database(entities = {Movie.class},version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao dao();
    private static MovieDatabase INSTANCE;

    public synchronized static MovieDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, "movie-database")
                            .addCallback(sRoomDatabaseCallBack)
                            //.allowMainThreadQueries() //Dont do this
                            .build();
                }
            }
        }
        return INSTANCE;

    }
    private static RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback()
    {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db)
        {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };
    private static class PopulateDbAsync extends AsyncTask<List<Movie>,Void,Void>
    {
        private final MovieDao dao;
        PopulateDbAsync(MovieDatabase db) {
            dao = db.dao();
        }

        @Override
        protected Void doInBackground(final List<Movie>... taskObjects) {
            //Prepopulating here the cvs file
            dao.insert(new Movie());

            return null;
        }
    }
}
