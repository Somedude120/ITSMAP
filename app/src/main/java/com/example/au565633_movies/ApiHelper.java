package com.example.au565633_movies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class ApiHelper {
    private Context Context;
    private String Url;
    private Intent rIntent;
    private MyReceiver receiver;
    private Movie movie;
    private SyncServiceSupportImpl serviceImpl;
    private CustomListView customListView;
    private MovieRepository repo;
    ApiHelper(Context con, String url, Intent intent, MyReceiver myReceiver, SyncServiceSupportImpl serviceimpl, CustomListView customlistview)
    {
        Context = con;
        serviceImpl = serviceimpl;
        Url = url;
        rIntent = intent;
        receiver = myReceiver;
        customListView = customlistview;
        customlistview.notifyDataSetChanged();
        repo = new MovieRepository(con);
        getData();
        customlistview.notifyDataSetChanged();
    }

    public Movie getaMovie() {
        return movie;
    }

    private void getData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(Context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest strReq = new StringRequest(Url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response)
            {
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String title = jsonObject.optString("Title");
                        String rating = jsonObject.optString("imdbRating");
                        String genre = jsonObject.optString("Genre");
                        String plot = jsonObject.optString("Plot");
                        String res = jsonObject.optString("Response");
                        Log.d(TAG, "Volley onResponse: " + title);


                        if(!res.equals("True"))
                        {
                            rIntent.setAction("com.example.au565633_movies.broadcastreceiver.FAILED_TO_ADD");
                        }
                        else
                        {
                            //Movie movie = new Movie();
                            movie = new Movie();

                            movie.setTitle(title);
                            movie.setRating(rating);
                            movie.setGenre(genre);
                            movie.setPlot(plot);
                            movie.setMyRating("0");
                            movie.setWatched(false);
                            movie.setComments("N/A");

                            GenreSplitter splitter = new GenreSplitter(movie);
                            movie.Icon = splitter.MainGenre();
                            Log.d(TAG, "volley movie: " + movie.Title);

                            //Check for a match
                            if(serviceImpl.getMovie(movie.Title)!= null)
                            {
                                rIntent.setAction("com.example.au565633_movies.broadcastreceiver.OWNED_MOVIE");
                            }
                            else
                            {
                                serviceImpl.insert(movie); //Dangerous to add in another thread data
                                rIntent.setAction("com.example.au565633_movies.broadcastreceiver.ADDED_MOVIE");
                            }

                        }


                        receiver.onReceive(Context,rIntent);

                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                Log.d(TAG, "Volley: Good Response : " + response);

                customListView.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Context);
        requestQueue.add(strReq);
    }
    public Intent getBroadcastIntent()
    {
        return rIntent;
    }
}

