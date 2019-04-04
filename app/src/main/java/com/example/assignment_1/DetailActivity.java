package com.example.assignment_1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private View detailViewLayout;
    private View detailViewLayoutLS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            // In landscape
            detailViewLayoutLS = findViewById(R.id.layout_detaills);
            detailViewLayoutLS.setBackgroundColor(getResources().getColor(R.color.Turqois));
        }
        else
        {
            // In portrait
            detailViewLayout = findViewById(R.id.layout_detail);
            detailViewLayout.setBackgroundColor(getResources().getColor(R.color.Turqois));
        }

        final Intent overViewActivityIntent = getIntent();

        Button btn_Ok = findViewById(R.id.btn_Ok1);
        CheckBox checkBoxDetail = findViewById(R.id.chckbox_Detail);

        Movie movie = overViewActivityIntent.getParcelableExtra("Movie");
        /*String title = overViewActivityIntent.getStringExtra("Title");
        String description = overViewActivityIntent.getStringExtra("Description");
        String comments = overViewActivityIntent.getStringExtra("Comments");
        String genre = overViewActivityIntent.getStringExtra("Genre");
        String ratings = overViewActivityIntent.getStringExtra("Rating");
        String myRatings = overViewActivityIntent.getStringExtra("MyRating");
        boolean watched = overViewActivityIntent.getBooleanExtra("Watched",false);*/

        ImageView img_movie = findViewById(R.id.img_Detail);

        TextView txtTitleDetail = findViewById(R.id.txt_TitleDetail);
        TextView txtDescription = findViewById(R.id.txt_description_detail);
        TextView txtComments = findViewById(R.id.txt_Comment);
        TextView txtGenre = findViewById(R.id.txt_Genre);
        TextView txtRating = findViewById(R.id.txt_Rating);
        TextView txtMyRating = findViewById(R.id.txt_uRating);

        txtTitleDetail.setText(movie.Title);
        txtDescription.setText(Html.fromHtml("<b>"+getResources().getString(R.string.plot) + ":</b> " + movie.Plot));
        txtComments.setText(Html.fromHtml("<b>"+getString(R.string.usercomment)+":</b> " + movie.Comments));
        txtGenre.setText(Html.fromHtml("<b>"+getString(R.string.genre)+":</b> " + movie.Genre));
        txtRating.setText(Html.fromHtml("<b>"+getString(R.string.rating)+":</b> " + movie.Rating));
        txtMyRating.setText(Html.fromHtml("<b>"+getString(R.string.userrating)+":</b> " + movie.MyRating));


        checkBoxDetail.setChecked(movie.Watched);
        img_movie.setImageResource(movie.Icon);
        /*txtTitleDetail.setText(title);
        txtDescription.setText(description);
        txtComments.setText(comments);
        txtGenre.setText(genre);
        txtRating.setText(ratings);
        txtMyRating.setText(myRatings);

        checkBoxDetail.setChecked(watched);*/

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

}
