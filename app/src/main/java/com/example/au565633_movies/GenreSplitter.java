package com.example.au565633_movies;

public class GenreSplitter {
    private Movie movie_;
    String singleGenre_;
    GenreSplitter(Movie movie)
    {
        movie_ = movie;
        SplitGenre();
    }
    public void SplitGenre()
    {
        String[] singleGenre = movie_.Genre.split(",");
        singleGenre_ = singleGenre[0];
    }

    public int MainGenre()
    {
        switch (singleGenre_)
        {
            case "Action":
                return R.drawable.actionprettyicon;
            case "Music":
                return R.drawable.musicicon;
            case "Drama":
                return R.drawable.dramaprettyicon;
            case "Animation":
                return R.drawable.animationprettyicon;
            case "Biography":
                return R.drawable.biographyprettyicon;
            default:
                return R.drawable.ic_launcher_foreground;


        }
    }
}
