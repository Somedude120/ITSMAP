package com.example.assignment_1;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;


public class SyncService extends Service
{
    private static List<Movie> movies = new ArrayList<>();
    private static SyncServiceSupportImpl syncServiceImpl;
    private static NotificationManager nManager;
    private static Timer timer;

    //Get a title in the notification
    @Override
    public void onCreate() {
        super.onCreate();

        //init service
        syncServiceImpl = new SyncServiceSupportImpl(SyncService.this);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        intent.getParcelableArrayExtra("Movie");
        Intent notificationIntent = new Intent(this, OverviewActivity.class);
        String r = "Random";
        r = watchRandomMovie(r);
        Log.d(TAG, "Random Movie: " + r);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, getString(R.string.movieChannel))
                .setSmallIcon(R.drawable.popcornbackground)
                .setContentTitle("MovieApp")
                .setContentText("You haven't watched this movie: " + r)
                .setContentIntent(pendingIntent);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                String random = "----";
                random = watchRandomMovie(random);

                if (nManager == null)
                    nManager = (NotificationManager)  getSystemService(NOTIFICATION_SERVICE);

                else
                    {
                        if(random.equals("----"))
                        {
                            notification.setContentText("You're out of movies");
                            nManager.notify(1, notification.build());
                        }
                        else
                        {
                            notification.setContentText("You haven't watched this movie: " + random);
                            nManager.notify(1, notification.build());
                        }
                }
            }

        },60000 * 5,60000 * 5); // 60000 milliseconds = 1 minute

        startForeground(1, notification.build());//This start the notification service
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private String watchRandomMovie(String random)
    {
        ArrayList<String> movies = new ArrayList<>();
        for (int i = 0; i < syncServiceImpl.getMovies().size(); i++) {

            if(!syncServiceImpl.getMovies().get(i).Watched)
                movies.add(syncServiceImpl.getMovies().get(i).Title);
        }
        Random rand = new Random();
        if(movies.size()!=0)
        {
            int n = rand.nextInt(movies.size());
            random = movies.get(n);
        }

        return random;
    }

}
