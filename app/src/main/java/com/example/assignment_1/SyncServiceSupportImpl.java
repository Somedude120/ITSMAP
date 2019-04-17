package com.example.assignment_1;

import android.app.Service;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class SyncServiceSupportImpl implements SyncServiceSupport
{
    private MovieDao movieDao;
    public SyncServiceSupportImpl(Context context)
    {
        movieDao = MovieDatabase.getDatabase(context).dao();
    }
    @Override
    public List<Movie> getMovie(String title)
    {
        return movieDao.getServiceTitle(title);

    }

}