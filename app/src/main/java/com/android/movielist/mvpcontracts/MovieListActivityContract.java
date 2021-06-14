package com.android.movielist.mvpcontracts;

import com.android.movielist.presenters.IBasePresenter;
import com.android.movielist.webservice.responsemodels.GenreModel;
import com.android.movielist.webservice.responsemodels.MovieBaseModel;
import com.android.movielist.webservice.responsemodels.MovieModel;
import com.android.movielist.webservice.responsemodels.MoviePageListModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MovieListActivityContract {
    interface Model {

        Observable<MoviePageListModel> getMovies(int pageNumber);
        Observable<MoviePageListModel> getMovies(int pageNumber,String s);
        Observable<List<GenreModel>> getGenres();
        Observable<MovieModel> getMovie(int movieId);
    }

    interface Presenter extends IBasePresenter {

        void getGenres();
        void getMovies();
        void searchMovie(String keyword);
        void sortMovieList();
        void getMoviesByGenre(GenreModel genre);
//        void getMoreMovies();
        void changeFavoriteState(MovieBaseModel value);
    }

    interface View {

        void addMoviesToList(List<MovieBaseModel> movieModels);
        void loadGenres(List<GenreModel> genreModels);
        void reloadMovieList(List<MovieBaseModel> movieModels);

        void updateMovieFavoriteUI(MovieBaseModel value);
        void showEmptyListMessage();
        void showMovieList();
    }

}
