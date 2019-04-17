package com.example.assignment_1;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Movie.class},version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao dao();
    private static volatile MovieDatabase INSTANCE;

    public static MovieDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, "movie-database")
                            //.addCallback(sRoomDatabaseCallBack)
                            //.allowMainThreadQueries() //Dont do this
                            .build();
                }
            }
        }
        return INSTANCE;

    }
}
