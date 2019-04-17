package com.example.assignment_1;

import android.app.Service;
import android.arch.persistence.room.Database;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

public class SyncService extends Service
{
    private static List<Movie> movies = new ArrayList<>();
    private static SyncServiceSupportImpl syncService;

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
            protected void onPostExecute(Void agentCount)
            {

            }
        }.execute(title);
    }
    @Override
    public void onCreate() {
        super.onCreate();

        //init service
        syncService = new SyncServiceSupportImpl(SyncService.this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getFromDB();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
