package com.android.movielist.views;

import com.android.movielist.webservice.responsemodels.GenreModel;
import com.android.movielist.webservice.responsemodels.MovieBaseModel;
import com.android.movielist.webservice.responsemodels.MovieModel;

import java.util.List;

public interface MovieListView {

    void addMoviesToList(List<MovieBaseModel> movieModels);
    void loadGenres(List<GenreModel> genreModels);
    void reloadMovieList(List<MovieBaseModel> movieModels);
}
