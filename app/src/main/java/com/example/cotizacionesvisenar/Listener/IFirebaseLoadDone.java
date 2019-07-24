package com.example.cotizacionesvisenar.Listener;

import com.example.cotizacionesvisenar.Model.Movie;

import java.util.List;

public interface IFirebaseLoadDone {
    void onFirebaseLoadSuccess(List<Movie> movieList);
    void onFirebaseLoadFailed(String message);
}
