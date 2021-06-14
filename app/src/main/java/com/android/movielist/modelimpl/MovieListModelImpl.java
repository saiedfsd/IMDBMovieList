package com.android.movielist.modelimpl;

import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.android.movielist.MovieDataSource;
import com.android.movielist.db.dao.GenreDao;
import com.android.movielist.db.dao.ImagesDao;
import com.android.movielist.db.dao.MovieDao;
import com.android.movielist.db.dao.PosterDao;
import com.android.movielist.models.MovieListModel;
import com.android.movielist.mvpcontracts.MovieListActivityContract;
import com.android.movielist.webservice.apiservices.MovieApiService;
import com.android.movielist.webservice.responsemodels.GenreModel;
import com.android.movielist.webservice.responsemodels.MovieBaseModel;
import com.android.movielist.webservice.responsemodels.MovieModel;
import com.android.movielist.webservice.responsemodels.MoviePageListModel;

import java.util.List;

import javax.inject.Inject;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScope;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListModelImpl implements MovieListActivityContract.Model {

    public GenreDao genreDao;
    public ImagesDao ImagesDao;
    public MovieDao MovieDao;
    public PosterDao PosterDao;
    public MovieApiService movieApiService;

    @Inject
    public MovieListModelImpl(GenreDao genreDao,
                              ImagesDao imagesDao,
                              MovieDao movieDao,
                              PosterDao posterDao,
                              MovieApiService movieApiService) {

        this.genreDao = genreDao;
        this.ImagesDao = imagesDao;
        this.MovieDao = movieDao;
        this.PosterDao = posterDao;
        this.movieApiService = movieApiService;
    }


    @Override
    public Observable<MoviePageListModel> getMovies(int pageNumber) {
        /*return movieApiService.getMoviesListByPage(pageNumber)
                .concatMap(new Function<MoviePageListModel, Observable<MoviePageListModel>>() {
                    @Override
                    public Observable<MoviePageListModel> apply(MoviePageListModel moviePageListModel) throws Throwable {
                        if (Integer.parseInt(moviePageListModel.getMetadata().getCurrentPage()) == moviePageListModel.getMetadata().getPageCount()) {
                            return Observable.just(moviePageListModel);
                        }
                        return Observable.just(moviePageListModel)
                                .concatWith(getMovies(Integer.parseInt(moviePageListModel.getMetadata().getCurrentPage()) + 1));
                    }
                });
        Call<MoviePageListModel> call = movieApiService.getMoviesListByPage(pageNumber);
        Observable<MoviePageListModel> observable = Observable
                .create(new ObservableOnSubscribe<MoviePageListModel>() {
            @Override
            public void subscribe(ObservableEmitter<MoviePageListModel> e) throws Exception {
                call.enqueue(new Callback<MoviePageListModel>() {
                    @Override
                    public void onResponse(Call<MoviePageListModel> call, Response<MoviePageListModel> response) {
                        if (!e.isDisposed()){
                            if (response.isSuccessful() && response.code() == 200){
                                e.onNext(response.body());
                            }else{
                                e.onError(new Throwable());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviePageListModel> call, Throwable t) {
                        if (!e.isDisposed()){
                            e.onError(t);
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        return observable;*/

        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(viewModel);
        Pager<Integer, MovieBaseModel> pager = new Pager<Integer, MovieBaseModel>(
                new PagingConfig(10),
                () -> new MovieDataSource(movieApiService));

        Observable<PagingData<MovieBaseModel>> flowable = PagingRx.getObservable(pager);
        PagingRx.cachedIn(flowable, viewModelScope);
    }

    @Override
    public Observable<MoviePageListModel> getMovies(int pageNumber,String s) {
        Call<MoviePageListModel> call = movieApiService.searchInMoviesByKeyword(pageNumber,s);
        Observable<MoviePageListModel> observable = Observable
                .create(new ObservableOnSubscribe<MoviePageListModel>() {
                    @Override
                    public void subscribe(ObservableEmitter<MoviePageListModel> e) throws Exception {
                        call.enqueue(new Callback<MoviePageListModel>() {
                            @Override
                            public void onResponse(Call<MoviePageListModel> call, Response<MoviePageListModel> response) {
                                if (!e.isDisposed()){
                                    if (response.isSuccessful() && response.code() == 200){
                                        e.onNext(response.body());
                                    }else{
                                        e.onError(new Throwable());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<MoviePageListModel> call, Throwable t) {
                                if (!e.isDisposed()){
                                    e.onError(t);
                                }
                            }
                        });
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        return observable;
    }

    @Override
    public Observable<List<GenreModel>> getGenres() {
        return null;
    }

    @Override
    public Observable<MovieModel> getMovie(int movieId) {
        return null;
    }
}
