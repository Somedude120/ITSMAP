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
    private static SyncServiceSupportImpl syncServiceImpl;
    private static MovieRepository repo;
    private static MovieDatabase db;

    //Get a title in the notification
    @Override
    public void onCreate() {
        super.onCreate();
        Movie movie = new Movie();
        movie.Title = "MovieTest";
        //Insert db in a bad way
        //TODO: Clean up
        repo = new MovieRepository(this);
        repo.insert(movie);
        repo.delete(movie);
        //Very bad way to put in a new db.

        //init service
        syncServiceImpl = new SyncServiceSupportImpl(SyncService.this);

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
        startForeground(1,notification);//This start the notification service

        //Todo: Show newest added movie, when setup with API
        //getFromDB(movies.get(intent.getIntExtra("Position",0)));




        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
