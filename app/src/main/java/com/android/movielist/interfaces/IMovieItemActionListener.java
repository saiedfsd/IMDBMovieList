package com.android.movielist.interfaces;

import com.android.movielist.webservice.responsemodels.MovieBaseModel;
import com.android.movielist.webservice.responsemodels.MovieModel;

public interface IMovieItemActionListener {

    void onShareMovieItem(MovieBaseModel movieModel);
    void onFavoriteMovieItem(MovieBaseModel movieModel, int position);

}
