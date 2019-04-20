package com.example.assignment_1;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

    }
    private void getData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(Context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Movie movie = new Movie();
                        movie.setTitle(jsonObject.getString("Title"));
                        movie.setRating(jsonObject.getString("imdbRating"));
                        movie.setGenre(jsonObject.getString("Genre"));
                        movie.setPlot(jsonObject.getString("Plot"));


                        serviceImpl.insert(movie);
                    } catch (JSONException e) {
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
        requestQueue.add(jsonArrayRequest);
    }
}

