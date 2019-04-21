package com.example.au565633_movies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("API123", "" + intent.getAction());

        if (intent.getAction().equals("com.example.au565633_movies.broadcastreceiver.ADDED_MOVIE"))
        {
            Toast.makeText(context, "ADDED MOVIE", Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals("com.example.au565633_movies.broadcastreceiver.FAILED_TO_ADD"))
        {
            Toast.makeText(context, "FAILED TO ADD MOVIE", Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals("com.example.au565633_movies.broadcastreceiver.OWNED_MOVIE"))
        {
            Toast.makeText(context, "YOU OWN THIS MOVIE", Toast.LENGTH_LONG).show();
        }

    }

}
