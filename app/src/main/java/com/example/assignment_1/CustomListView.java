package com.example.assignment_1;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListView extends BaseAdapter {
    private List<Movie> movieListCVS_;
    private Activity context_;

    CustomListView(List<Movie> movieListCVS,Activity context)
    {
        movieListCVS_ = movieListCVS;
        context_ = context;

    }
    @Override
    public int getCount() {
        return movieListCVS_.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context_);
        convertView = inflater.inflate(R.layout.listview, null);


        ImageView img_movie = (ImageView)convertView.findViewById(R.id.img_Overview);
        TextView txt_title = convertView.findViewById(R.id.txt_title);
        TextView txt_rating = convertView.findViewById(R.id.txt_rating);
        TextView txt_myrating = convertView.findViewById(R.id.txt_myrating);
        CheckBox chck_watched = convertView.findViewById(R.id.chck_ListBox);

        txt_title.setText(movieListCVS_.get(position).Title);
        txt_rating.setText(Html.fromHtml("<b>"+context_.getResources().getString(R.string.rating) + ":</b> " + movieListCVS_.get(position).Rating));
        txt_myrating.setText(Html.fromHtml("<b>"+context_.getResources().getString(R.string.userrating) +":</b> " + movieListCVS_.get(position).MyRating));
        chck_watched.setChecked(movieListCVS_.get(position).Watched);
        img_movie.setImageResource(movieListCVS_.get(position).Icon);

        return convertView;
    }

}
