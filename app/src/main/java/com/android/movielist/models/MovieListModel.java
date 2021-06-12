package com.android.movielist.models;

import com.android.movielist.webservice.responsemodels.GenreModel;
import com.android.movielist.webservice.responsemodels.MovieBaseModel;
import com.android.movielist.webservice.responsemodels.MovieModel;
import com.android.movielist.webservice.responsemodels.MoviePageListModel;

import java.util.List;

import io.reactivex.Observable;

public interface MovieListModel {

    Observable<MoviePageListModel> getMovies();
    Observable<MoviePageListModel> getMovies(String s);
    Observable<List<GenreModel>> getGetGenres();
    Observable<MovieModel> getMovie(int movieId);
}
