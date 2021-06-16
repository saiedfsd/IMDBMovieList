package com.android.movielist.presentersimpl;

import android.util.Log;

import androidx.paging.PagingData;

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
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieListPresenterImpl implements MovieListActivityContract.Presenter {

    private MovieListActivityContract.View movieListView;
    private MovieListActivityContract.Model movieListModel;
    private CompositeDisposable compositeDisposable;
    private MetaDataModel lastReceivedMoviePageMetaData;
    private List<MovieBaseModel> allMovies;
    private boolean isLoading;
    private String lastKeyword = "";

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
        movieListModel.getGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GenreModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<GenreModel> genreModels) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getMovies(String keyword) {
        if (isLoading)
            return;

        if (!keyword.equals(lastKeyword)) {
            lastKeyword = keyword;
            lastReceivedMoviePageMetaData = null;
            allMovies.clear();
        }

        int pageNumber = 0;
        if(lastReceivedMoviePageMetaData != null)
            if (Integer.parseInt(lastReceivedMoviePageMetaData.getCurrentPage()) < lastReceivedMoviePageMetaData.getPageCount())
                pageNumber = Integer.parseInt(lastReceivedMoviePageMetaData.getCurrentPage()) + 1;
            else
                return;

        isLoading = true;

        movieListModel.getMovies(pageNumber,lastKeyword)
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
                        else {
                            movieListView.reloadMovieList(moviePageListModel.getData());
                        }
                        allMovies.addAll(moviePageListModel.getData());

                        movieListView.showMovieList();
                        isLoading = false;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("fsd",e.getMessage());
                        movieListView.showEmptyListMessage();
                        isLoading = false;
                    }

                    @Override
                    public void onComplete() {
                        isLoading = false;
                    }
                });
    }

    @Override
    public void searchMovie(String keyword) {
        if (isLoading)
            return;

        int pageNumber = 0;
        if(lastReceivedMoviePageMetaData != null)
            if (Integer.parseInt(lastReceivedMoviePageMetaData.getCurrentPage()) < lastReceivedMoviePageMetaData.getPageCount())
                pageNumber = Integer.parseInt(lastReceivedMoviePageMetaData.getCurrentPage()) + 1;
            else
                return;

        isLoading = true;

        movieListModel.getMovies(pageNumber,keyword)
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
                        else {
                            movieListView.reloadMovieList(moviePageListModel.getData());
                        }
                        allMovies.addAll(moviePageListModel.getData());

                        movieListView.showMovieList();
                        isLoading = false;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("fsd",e.getMessage());
                        movieListView.showEmptyListMessage();
                        isLoading = false;
                    }

                    @Override
                    public void onComplete() {
                        isLoading = false;
                    }
                });

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

        getMovies(lastKeyword);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}
