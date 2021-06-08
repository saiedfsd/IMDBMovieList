package com.android.movielist.presenters;

import com.android.movielist.webservice.responsemodels.GenreModel;

public interface IMovieListPresenter extends IBasePresenter {

    void getGenres();
    void getMovies();
    void searchMovie(String keyword);
    void sortMovieList();
    void getMoviesByGenre(GenreModel genre);
    void getMoreMovies();

}
