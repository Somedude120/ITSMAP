package com.example.assignment_1;

import android.content.Context;

import java.util.List;

public interface SyncServiceSupport {
    List<Movie> getMovie(String movieName);
}

