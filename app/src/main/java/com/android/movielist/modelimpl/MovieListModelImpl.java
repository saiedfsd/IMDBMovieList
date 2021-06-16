package com.android.movielist.modelimpl;

import com.android.movielist.db.dao.GenreDao;
import com.android.movielist.db.dao.ImagesDao;
import com.android.movielist.db.dao.MovieDao;
import com.android.movielist.db.dao.PosterDao;
import com.android.movielist.mvpcontracts.MovieListActivityContract;
import com.android.movielist.webservice.apiservices.MovieApiService;
import com.android.movielist.webservice.responsemodels.GenreModel;
import com.android.movielist.webservice.responsemodels.MovieModel;
import com.android.movielist.webservice.responsemodels.MoviePageListModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListModelImpl implements MovieListActivityContract.Model {

    public GenreDao genreDao;
    public ImagesDao ImagesDao;
    public MovieDao MovieDao;
    public PosterDao PosterDao;
    public MovieApiService movieApiService;
//    Pager<Integer, MovieBaseModel> pager;

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
        /*pager = new Pager<Integer, MovieBaseModel>(
                new PagingConfig(10),
                () -> new MovieDataSource(movieApiService));*/
    }


    @Override
    public Observable<MoviePageListModel> getMovies(int pageNumber) {
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

        return observable;

//        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(viewModel);
//        pager = new Pager<Integer, MovieBaseModel>(
//                new PagingConfig(10),
//                () -> new MovieDataSource(movieApiService));

//        Observable<PagingData<MovieBaseModel>> flowable = PagingRx.getObservable(pager);
//        PagingRx.cachedIn(flowable, viewModelScope);

//        return PagingRx.getObservable(pager);
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
        Call<List<GenreModel>> call = movieApiService.getGenresList();
        Observable<List<GenreModel>> observable = Observable
                .create(new ObservableOnSubscribe<List<GenreModel>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<GenreModel>> e) throws Exception {
                        call.enqueue(new Callback<List<GenreModel>>() {
                            @Override
                            public void onResponse(Call<List<GenreModel>> call, Response<List<GenreModel>> response) {
                                if (!e.isDisposed()){
                                    if (response.isSuccessful() && response.code() == 200){
                                        e.onNext(response.body());
                                    }else{
                                        e.onError(new Throwable());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<GenreModel>> call, Throwable t) {
                                if (!e.isDisposed()){
                                    e.onError(t);
                                }
                            }
                        });
                    }
                });

        return observable;
    }

    @Override
    public Observable<MovieModel> getMovie(int movieId) {
        return null;
    }
}
