package com.android.movielist.presentersimpl;

import android.util.Log;

import com.android.movielist.mvpcontracts.MovieListActivityContract;
import com.android.movielist.webservice.responsemodels.GenreModel;
import com.android.movielist.webservice.responsemodels.MetaDataModel;
import com.android.movielist.webservice.responsemodels.MovieBaseModel;
import com.android.movielist.webservice.responsemodels.MoviePageListModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieListPresenterImpl implements MovieListActivityContract.Presenter {

    private MovieListActivityContract.View movieListView;
    private MovieListActivityContract.Model movieListModel;
    private CompositeDisposable compositeDisposable;
    private MetaDataModel lastReceivedMoviePageMetaData;
    private List<MovieBaseModel> allMovies;

    @Inject
    public MovieListPresenterImpl(MovieListActivityContract.View movieListView,
                                  MovieListActivityContract.Model movieListModel) {
        this.movieListView = movieListView;
        this.movieListModel = movieListModel;
        compositeDisposable = new CompositeDisposable();
        allMovies = new ArrayList<>();
    }

    @Override
    public void getGenres() {

    }

    @Override
    public void getMovies() {
        int pageNumber = 0;
        if(lastReceivedMoviePageMetaData != null)
            if (Integer.parseInt(lastReceivedMoviePageMetaData.getCurrentPage()) < lastReceivedMoviePageMetaData.getPageCount())
                pageNumber = Integer.parseInt(lastReceivedMoviePageMetaData.getCurrentPage()) + 1;
            else
                return;

        movieListModel.getMovies(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MoviePageListModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull MoviePageListModel moviePageListModel) {
                        lastReceivedMoviePageMetaData = moviePageListModel.getMetadata();

                        if (allMovies.size() > 0)
                            movieListView.addMoviesToList(moviePageListModel.getData());
                        else
                            movieListView.reloadMovieList(moviePageListModel.getData());

                        allMovies.addAll(moviePageListModel.getData());

                        movieListView.showMovieList();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("fsd",e.getMessage());
                        movieListView.showEmptyListMessage();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

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

    /*@Override
    public void getMoreMovies() {

    }*/

    @Override
    public void changeFavoriteState(MovieBaseModel value) {

        movieListView.updateMovieFavoriteUI(value);
    }

    @Override
    public void onCreate() {
        getMovies();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}
