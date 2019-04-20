package com.example.assignment_1;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Update;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class SyncService extends Service
{
    private static List<Movie> movies = new ArrayList<>();
    private static SyncServiceSupportImpl syncService;
    private static MovieRepository repo;
    private static MovieDatabase db;

    //Get a title in the notification
    private static void getFromDB(Movie title)
    {
        new AsyncTask<Movie,Void,Void>()
        {
            @Override
            protected Void doInBackground(Movie... params)
            {
                //Get title
                movies = syncService.getMovie(params[0].Title);
                return null;
            }
            @Override
            protected void onPostExecute(Void param)
            {

            }
        }.execute(title);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Movie movie = new Movie();
        movie.Title = "MovieTest";
        //Insert db
        repo = new MovieRepository(this);
        repo.insert(movie);
        repo.delete(movie);
        //Very bad way to put in a new db.

        //init service
        syncService = new SyncServiceSupportImpl(SyncService.this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        intent.getParcelableArrayExtra("Movie");
        Intent notificationIntent = new Intent(this,OverviewActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this,getString(R.string.movieChannel))
                .setSmallIcon(R.drawable.animationicon)
                .setContentTitle("MovieApp")
                .setContentText("This is assignment 2")
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);

        //Todo: Add movies
        //Todo: Show newest added movie

        //Log.d(TAG, "onService: " + movies.size());
        //getFromDB(movies.get(0));
        //return START_NOT_STICKY;

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
