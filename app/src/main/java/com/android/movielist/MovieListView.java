package com.android.movielist;

import com.android.movielist.webservice.responsemodels.GenreModel;
import com.android.movielist.webservice.responsemodels.MovieModel;

import java.util.List;

public interface MovieListView {

    void addMoviesToList(List<MovieModel> movieModels);
    void loadGenres(List<GenreModel> genreModels);
}
