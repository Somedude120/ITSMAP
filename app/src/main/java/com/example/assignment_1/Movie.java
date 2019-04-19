package com.example.assignment_1;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * This is my object based classes.
 */
@Entity
public class Movie implements Parcelable {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    public String Title;
    @ColumnInfo(name = "plot")
    public String Plot;
    @ColumnInfo(name = "watched")
    public boolean Watched;
    @ColumnInfo(name = "comments")
    public String Comments;
    @ColumnInfo(name = "rating")
    public String Rating;
    @ColumnInfo(name = "myrating")
    public String MyRating;

    public Movie()
    {}
    protected Movie(Parcel in) {
        super();
        Title = in.readString();
        Plot = in.readString();
        Watched = in.readByte() != 0;
        Comments = in.readString();
        Rating = in.readString();
        MyRating = in.readString();
        Genre = in.readString();
        Icon = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public int Icon;

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String Genre;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public boolean isWatched() {
        return Watched;
    }

    public void setWatched(boolean watched) {
        Watched = watched;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getMyRating() {
        return MyRating;
    }

    public void setMyRating(String myRating) {
        MyRating = myRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title);
        dest.writeString(Plot);
        dest.writeByte((byte) (Watched ? 1 : 0));
        dest.writeString(Comments);
        dest.writeString(Rating);
        dest.writeString(MyRating);
        dest.writeString(Genre);
        dest.writeInt(Icon);
    }
}