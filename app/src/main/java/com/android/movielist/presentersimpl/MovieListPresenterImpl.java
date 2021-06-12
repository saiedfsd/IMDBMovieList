package com.android.movielist.presentersimpl;

import com.android.movielist.models.MovieListModel;
import com.android.movielist.presenters.IMovieListPresenter;
import com.android.movielist.views.MovieListView;
import com.android.movielist.webservice.responsemodels.GenreModel;
import com.android.movielist.webservice.responsemodels.MovieBaseModel;

import javax.inject.Inject;

public class MovieListPresenterImpl implements IMovieListPresenter {

    private MovieListView movieListView;
    private MovieListModel movieListModel;

    @Inject
    public MovieListPresenterImpl(MovieListView movieListView,MovieListModel movieListModel) {
        this.movieListView = movieListView;
        this.movieListModel = movieListModel;
    }

    @Override
    public void getGenres() {

    }

    @Override
    public void getMovies() {

    }

    @Override
    public void searchMovie(String keyword) {

    }

    @Override
    public void sortMovieList() {

    }

    @Override
    public void getMoviesByGenre(GenreModel genre) {

    }

    @Override
    public void getMoreMovies() {

    }

    @Override
    public void changeFavoriteState(MovieBaseModel value) {

        movieListView.updateMovieFavoriteUI(value);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
