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
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EditActivity extends AppCompatActivity {

    private String seekbarValue;

    private View editViewLayout;
    private View editViewLayoutLS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        int orientation = getResources().getConfiguration().orientation;
        final SyncServiceSupportImpl serviceImpl = new SyncServiceSupportImpl(this);

        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            // In landscape
            editViewLayoutLS = findViewById(R.id.layout_editls);
            editViewLayoutLS.setBackgroundColor(getResources().getColor(R.color.Turqois));
        }
        else
        {
            // In portrait
            editViewLayout = findViewById(R.id.layout_edit);
            editViewLayout.setBackgroundColor(getResources().getColor(R.color.Turqois));
        }

        final Intent overViewActivityIntent = getIntent();
        final int position = overViewActivityIntent.getIntExtra("Position",0);
        Movie movie = serviceImpl.getMovies().get(position);

        final SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(100);
        Double progressbar = Double.parseDouble(movie.MyRating) * 10;
        seekBar.setProgress(progressbar.intValue());


        final EditText userComment = findViewById(R.id.editText_UserComment);
        userComment.setText(movie.Comments);

        final TextView txt_SeekbarValue = findViewById(R.id.txt_SeekbarRating);
        TextView txt_Title = findViewById(R.id.txt_TitleEdit);
        txt_SeekbarValue.setText(Html.fromHtml("<b>" + getResources().getString(R.string.userrating) + ":</b> " + String.valueOf(movie.MyRating)));
        seekbarValue = String.valueOf(movie.MyRating);


        final CheckBox chck_box_watched = findViewById(R.id.chck_edit);
        chck_box_watched.setChecked(movie.Watched);


        Button btn_okEdit = findViewById(R.id.btn_OkEdit);
        Button btn_cancelEdit = findViewById(R.id.btn_cancelEdit);

        txt_Title.setText(movie.Title);

        //Cancel button
        btn_cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //OK button
        btn_okEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                serviceImpl.updateURating(seekbarValue,serviceImpl.getMovies().get(position).Title);
                serviceImpl.updateWatched(chck_box_watched.isChecked(),serviceImpl.getMovies().get(position).Title);
                serviceImpl.updateComment(userComment.getText().toString(),serviceImpl.getMovies().get(position).Title);
                Intent multipleIntent = new Intent();
                multipleIntent.putExtra("Position",position); //Send position back if it has been rotated


                setResult(Activity.RESULT_OK, multipleIntent);

                finish();
            }
        });

        //Seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                float progressD = (float) progress / 10;

                seekbarValue = String.valueOf(progressD);
                txt_SeekbarValue.setText(Html.fromHtml("<b>" + getResources().getString(R.string.userrating) + ":</b> " + String.valueOf(progressD)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
