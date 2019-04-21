package com.example.assignment_1;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiHelper {
    private Context Context;
    private String Url;
    private CustomListView Adapter;
    private SyncServiceSupportImpl serviceImpl;
    ApiHelper(Context con, String url, CustomListView adapter, SyncServiceSupportImpl serviceimpl)
    {
        Context = con;
        Url = url;
        Adapter = adapter;
        serviceImpl = serviceimpl;
        getData();

    }
    private void getData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(Context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,Url,null, new Response.Listener<JSONObject>() {
        StringRequest strReq = new StringRequest(Url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response)
            {
                //for (int i = 0; i < response.length(); i++)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        //String img_url = jsonObject.getString("Poster");
                        String title = jsonObject.optString("Title");
                        String rating = jsonObject.optString("imdbRating");
                        String genre = jsonObject.optString("Genre");
                        String plot = jsonObject.optString("Plot");
                        String res = jsonObject.optString("Response");



                        if(!res.equals("True"))
                        {
                            //TODO: Insert broadcaster here
                        }
                        else
                        {
                            Movie movie = new Movie();

                            movie.setTitle(title);
                            movie.setRating(rating);
                            movie.setGenre(genre);
                            movie.setPlot(plot);
                            movie.setMyRating("0");
                            movie.setWatched(false);
                            movie.setComments("N/A");

                            GenreSplitter splitter = new GenreSplitter(movie);
                            movie.Icon = splitter.MainGenre();
                            serviceImpl.insert(movie);
                        }



                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                Adapter.notifyDataSetChanged();
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
}

