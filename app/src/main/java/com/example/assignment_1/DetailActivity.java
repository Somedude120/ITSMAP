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
        Button btn_Delete = findViewById(R.id.btn_delete);
        CheckBox checkBoxDetail = findViewById(R.id.chckbox_Detail);
        Movie movie = null;
        Movie editMovie = null;



        movie = overViewActivityIntent.getParcelableExtra("Movie");



        ImageView img_movie = findViewById(R.id.img_Detail);

        //Lots of textviews
        TextView txtTitleDetail = findViewById(R.id.txt_TitleDetail);
        TextView txtDescription = findViewById(R.id.txt_description_detail);
        TextView txtComments = findViewById(R.id.txt_Comment);
        TextView txtGenre = findViewById(R.id.txt_Genre);
        TextView txtRating = findViewById(R.id.txt_Rating);
        TextView txtMyRating = findViewById(R.id.txt_uRating);

        //Lots of editviews
        final TextView editTitleDetail = findViewById(R.id.edit_Title);
        final TextView editDescription = findViewById(R.id.edit_description_detail);
        final TextView editComments = findViewById(R.id.edit_Comment);
        final TextView editGenre = findViewById(R.id.edit_Genre);
        final TextView editRating = findViewById(R.id.edit_Rating);
        final TextView editMyRating = findViewById(R.id.edit_uRating);
        int flag = overViewActivityIntent.getIntExtra("editFlag",0);

        if(overViewActivityIntent.getIntExtra("editFlag",0) != 0)
        {
            txtTitleDetail.setVisibility(View.GONE);
            txtDescription.setVisibility(View.GONE);
            txtComments.setVisibility(View.GONE);
            txtGenre.setVisibility(View.GONE);
            txtRating.setVisibility(View.GONE);
            txtMyRating.setVisibility(View.GONE);

            //Add movie details
        }
        else
        {
            editTitleDetail.setVisibility(View.GONE);
            editDescription.setVisibility(View.GONE);
            editComments.setVisibility(View.GONE);
            editGenre.setVisibility(View.GONE);
            editRating.setVisibility(View.GONE);
            editMyRating.setVisibility(View.GONE);

            txtTitleDetail.setText(movie.Title);
            txtDescription.setText(Html.fromHtml("<b>"+getResources().getString(R.string.plot) + ":</b> " + movie.Plot));
            txtComments.setText(Html.fromHtml("<b>"+getString(R.string.usercomment)+":</b> " + movie.Comments));
            txtGenre.setText(Html.fromHtml("<b>"+getString(R.string.genre)+":</b> " + movie.Genre));
            txtRating.setText(Html.fromHtml("<b>"+getString(R.string.rating)+":</b> " + movie.Rating));
            txtMyRating.setText(Html.fromHtml("<b>"+getString(R.string.userrating)+":</b> " + movie.MyRating));
            checkBoxDetail.setChecked(movie.Watched);
            img_movie.setImageResource(movie.Icon);

        }



        final MovieRepository repo = new MovieRepository(this);


        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie movie = new Movie();
                //Todo: Add insert with new movie here
                movie.Title = editTitleDetail.getText().toString();
                movie.Plot = editDescription.getText().toString();
                movie.Comments = editComments.getText().toString();
                movie.Genre = editGenre.getText().toString();
                movie.Rating = editRating.getText().toString(); //TODO: make this to the db rating
                movie.MyRating = editMyRating.getText().toString(); //TODO: Should this be my rating or zero?
                repo.insert(movie);
            }
        });
        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Make Remove here
                finish();
            }
        });



    }

}
