package com.example.assignment_1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class OverviewActivity extends AppCompatActivity {
    private static final String TAG = "OverViewActivity";
    private SyncServiceSupportImpl serviceImpl;
    private List<Movie> movieListCVS = new ArrayList();
    private int currentItem;
    private ListView myMoviesList;
    private View overViewLayout; //normal overview
    private View overViewLayoutLS; //landscape overview
    private ImageView pic1;
    private ImageView pic2;
    private String url = "http://www.omdbapi.com/?apikey=6d1d0b78&";
    private ApiHelper api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Button btn_Exit = findViewById(R.id.btn_Exit);
        Button btn_Add = findViewById(R.id.btn_Add);
        //Stetho plugin
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        //CSV Reading.
        InputStream inputStream = getResources().openRawResource(R.raw.movielist);
        CSVReader csvReader = new CSVReader(inputStream);
        final List<String[]> movielist = csvReader.read();
        //Notification
        createNotificationChannel();

        //Service
        Intent serviceIntent = new Intent(this,SyncService.class);
        ContextCompat.startForegroundService(this,serviceIntent);

        //Repo
        final MovieRepository repo = new MovieRepository(this);

        //Some orientation stuff
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            // In landscape
            overViewLayoutLS = findViewById(R.id.layout_overviewls);
            overViewLayoutLS.setBackgroundColor(getResources().getColor(R.color.Turqois));
        }
        else
        {
            // In portrait
            overViewLayout = findViewById(R.id.layout_overview);
            overViewLayout.setBackgroundColor(getResources().getColor(R.color.Turqois));

            pic1 = findViewById(R.id.img_overviewpic1);
            pic2 = findViewById(R.id.img_overviewpic2);

            pic1.setImageResource(R.drawable.specialbackground);
            pic2.setImageResource(R.drawable.specialbackground2);

        }
        serviceImpl = new SyncServiceSupportImpl(this);


        if(savedInstanceState != null && savedInstanceState.containsKey("savedMovieList"))
        {
            movieListCVS = savedInstanceState.getParcelableArrayList("savedMovieList");
        }

        //Todo: Add the list of movies from database here
        final CustomListView customListView = new CustomListView(serviceImpl,OverviewActivity.this);
        customListView.notifyDataSetChanged();
        myMoviesList = findViewById(R.id.list_Movie);
        myMoviesList.setAdapter(customListView);
        customListView.notifyDataSetChanged();

        btn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Sends the user to detail, where movie is created by the user.
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Add API CALL HERE
                api = new ApiHelper(OverviewActivity.this,url,customListView,serviceImpl);
                customListView.notifyDataSetChanged();
            }
        });

       //Normal click
       myMoviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent detailActivityIntent = new Intent(OverviewActivity.this,DetailActivity.class);
               currentItem = position; //Now I can follow the items
               detailActivityIntent.putExtra("Position",position);

               customListView.notifyDataSetChanged();
               startActivityForResult(detailActivityIntent,0);
           }
       });

       //Long click
       myMoviesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               //Send Title
               Intent editActivityIntent = new Intent(OverviewActivity.this,EditActivity.class);
               editActivityIntent.putExtra("Position",position);

               startActivityForResult(editActivityIntent,1);
               currentItem = position; //Now I can follow the items
               customListView.notifyDataSetChanged();
               return true;

           }
       });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null)//only do something if data has changed
        {
            currentItem = data.getIntExtra("Position",0);

            if(data.getIntExtra("DeleteFlag",0) != 0)
            {
                serviceImpl.delete(serviceImpl.getMovies().get(currentItem));
            }

            //This updates the listview
            final CustomListView customListView = new CustomListView(serviceImpl,OverviewActivity.this);
            myMoviesList = findViewById(R.id.list_Movie);
            myMoviesList.setAdapter(customListView);
            customListView.notifyDataSetChanged();
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = getString(R.string.movieChannel);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.movieChannel), name, importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("Position",currentItem);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        currentItem = savedInstanceState.getInt("Position");
    }

}
