package com.example.au565633_movies;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListView extends BaseAdapter {
    private Activity context_;
    private SyncServiceSupportImpl serviceImpl;

    CustomListView(SyncServiceSupportImpl serviceimpl,Activity context)
    {
        serviceImpl = serviceimpl;
        context_ = context;

    }
    @Override
    public int getCount() {
        return serviceImpl.getMovies().size();
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

        txt_title.setText(serviceImpl.getMovies().get(position).Title);
        txt_rating.setText(Html.fromHtml("<b>"+context_.getResources().getString(R.string.rating) + ":</b> " + serviceImpl.getMovies().get(position).Rating));
        txt_myrating.setText(Html.fromHtml("<b>"+context_.getResources().getString(R.string.userrating) +":</b> " + serviceImpl.getMovies().get(position).MyRating));
        chck_watched.setChecked(serviceImpl.getMovies().get(position).Watched);
        img_movie.setImageResource(serviceImpl.getMovies().get(position).Icon);

        return convertView;
    }

}
