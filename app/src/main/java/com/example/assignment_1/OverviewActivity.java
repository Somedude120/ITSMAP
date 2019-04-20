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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class OverviewActivity extends AppCompatActivity {
    private static final String TAG = "OverViewActivity";
    private ArrayList<Movie> movieListCVS = new ArrayList();
    private int currentItem;
    private ListView myMoviesList;
    private ArrayList<Movie> savedMovieListCVS;
    private GenreSplitter genreSplitter;
    private View overViewLayout; //normal overview
    private View overViewLayoutLS; //landscape overview
    private ImageView pic1;
    private ImageView pic2;

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
        //startService(serviceIntent);

        //Lots of textviews
        TextView txtTitleDetail = findViewById(R.id.txt_TitleDetail);
        TextView txtDescription = findViewById(R.id.txt_description_detail);
        final TextView txtComments = findViewById(R.id.txt_Comment);
        TextView txtGenre = findViewById(R.id.txt_Genre);
        TextView txtRating = findViewById(R.id.txt_Rating);
        TextView txtMyRating = findViewById(R.id.txt_uRating);

        //Lots of editviews
        TextView editTitleDetail = findViewById(R.id.edit_Title);
        TextView editDescription = findViewById(R.id.edit_description_detail);
        TextView editComments = findViewById(R.id.edit_Comment);
        TextView editGenre = findViewById(R.id.edit_Genre);
        TextView editRating = findViewById(R.id.edit_Rating);
        TextView editMyRating = findViewById(R.id.edit_uRating);

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

        if(savedInstanceState != null && savedInstanceState.containsKey("savedMovieList"))
        {
            movieListCVS = savedInstanceState.getParcelableArrayList("savedMovieList");
        }
        else
        {
            savedMovieListCVS = new ArrayList();

            movieListCVS = MovieHelper(movielist,movieListCVS, genreSplitter);


            //Two ways of removing the extra movie thing
            movieListCVS.remove(0); //This removes the first or change the for loop to 1
            savedMovieListCVS = movieListCVS;
        }

        //Todo: Add the list of movies from database here
        final CustomListView customListView = new CustomListView(movieListCVS,OverviewActivity.this);
        myMoviesList = findViewById(R.id.list_Movie);
        myMoviesList.setAdapter(customListView);



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
                Intent detailActivityIntent = new Intent(OverviewActivity.this,DetailActivity.class);
                //repo.insert(movie);
                detailActivityIntent.putExtra("editFlag",1);
                startActivityForResult(detailActivityIntent,0);
            }
        });

       //Normal click
       myMoviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent detailActivityIntent = new Intent(OverviewActivity.this,DetailActivity.class);
               detailActivityIntent.putExtra("Movie",movieListCVS.get(position));
               currentItem = position; //Now I can follow the items


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
               editActivityIntent.putExtra("Movie",movieListCVS.get(position));
               editActivityIntent.putExtra("Position",position);

               startActivityForResult(editActivityIntent,1);
               currentItem = position; //Now I can follow the items
               customListView.notifyDataSetChanged();


               return true;

           }
       });

    }
    private ArrayList<Movie> MovieHelper(List<String[]> list, ArrayList<Movie> List, GenreSplitter splitter)
    {
        for (int i = 0; i < list.size(); i++)
        {
            Movie movie = new Movie();

            movie.setTitle(list.get(i)[0]); //this one? Or
            movie.Plot = list.get(i)[1]; //this one?
            movie.Genre = list.get(i)[2];
            movie.Rating = list.get(i)[3];
            movie.Watched = false;
            movie.Comments = "I don't give a flying duck"; //Clearside Cop Drama
            movie.MyRating = "0";



            splitter = new GenreSplitter(movie);
            movie.Icon = splitter.MainGenre();


            List.add(movie);

        }
        return List;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null)//only do something if data has changed
        {
            movieListCVS.get(currentItem).setWatched(data.getExtras().getBoolean("checkboxValue",false));
            movieListCVS.get(currentItem).setMyRating(data.getStringExtra("seekbarValue"));
            movieListCVS.get(currentItem).setComments(data.getStringExtra("userComment"));
            currentItem = data.getIntExtra("Position",0);

            //This updates the listview
            final CustomListView customListView = new CustomListView(movieListCVS,OverviewActivity.this);
            myMoviesList = findViewById(R.id.list_Movie);
            myMoviesList.setAdapter(customListView);
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
        outState.putParcelableArrayList("savedMovieList", movieListCVS);
        outState.putInt("Position",currentItem);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        movieListCVS = savedInstanceState.getParcelableArrayList("savedMovieList");
        currentItem = savedInstanceState.getInt("Position");
    }

}
