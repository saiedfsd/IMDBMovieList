package com.android.movielist.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.movielist.App;
import com.android.movielist.R;
import com.android.movielist.adapters.MoviesRecyclerAdapter;
import com.android.movielist.db.ImdbMoviesDB;
import com.android.movielist.interfaces.IMovieItemActionListener;
import com.android.movielist.webservice.apiservices.MovieApiService;
import com.android.movielist.webservice.responsemodels.GenreModel;
import com.android.movielist.webservice.responsemodels.MovieBaseModel;
import com.android.movielist.webservice.responsemodels.MovieModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MovieListActivity extends AppCompatActivity {


    @BindView(R.id.btn_collapse_filters)
    public ImageButton btnFiltersCollapse;
    @BindView(R.id.btn_sort)
    public ImageButton btnSort;
    @BindView(R.id.btn_genres)
    public ImageButton btnGenres;
    @BindView(R.id.filters_parent)
    public ConstraintLayout filtersParent;
    @BindView(R.id.recycler_movies)
    public RecyclerView recyclerViewMovie;
    @BindView(R.id.edt_search)
    public androidx.appcompat.widget.SearchView edtSearchView;

    private MoviesRecyclerAdapter moviesRecyclerAdapter;

    private PublishSubject<MovieBaseModel> sharePublishSubject = PublishSubject.create();

    private CompositeDisposable disposables = new CompositeDisposable();

    Observable<String> observableQueryText ;

    private IMovieItemActionListener movieItemActionListener = new IMovieItemActionListener() {
        @Override
        public void onShareMovieItem(MovieBaseModel movieModel) {
            sharePublishSubject.onNext(movieModel);
        }

        @Override
        public void onFavoriteMovieItem(MovieBaseModel movieModel, int position) {

        }
    };

    @Inject
    public ImdbMoviesDB database;

    @Inject
    public MovieApiService movieApiService;

    private boolean filtersIsCollapsed = true;
    private long PERIOD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((App)getApplicationContext()).getAppComponent().injectActivity(this);
        super.onCreate(savedInstanceState);
//        AndroidInjection.inject(this);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        btnFiltersCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filtersIsCollapsed) {
                    filtersIsCollapsed = false;
                    btnFiltersCollapse.setImageResource(R.drawable.arrow_up_ico);
                    filtersParent.setVisibility(View.VISIBLE);
                }
                else {
                    filtersIsCollapsed = true;
                    btnFiltersCollapse.setImageResource(R.drawable.arrow_down_ico);
                    filtersParent.setVisibility(View.GONE);
                }
            }
        });

        observableQueryText = Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                        edtSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                if(!emitter.isDisposed()){
                                    emitter.onNext(newText);
                                }
                                return false;
                            }
                        });
                    }
                })
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io());

        observableQueryText.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }
            @Override
            public void onNext(String s) {
                Log.e("fsd",s);
//                sendRequestToServer(s);
            }
            @Override
            public void onError(Throwable e) {
                Log.e("fsd",e.getMessage());
            }
            @Override
            public void onComplete() {

            }
        });

        sharePublishSubject.switchMap(new Function<MovieBaseModel, ObservableSource<MovieBaseModel>>() {
            @Override
            public ObservableSource<MovieBaseModel> apply(MovieBaseModel movieBaseModel) throws Exception {
                return Observable
                        .interval(PERIOD, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .takeWhile(new Predicate<Long>() {

                            @Override
                            public boolean test(Long aLong) throws Exception {
                                return aLong <= (3000 / PERIOD);
                            }
                        })
                        .filter(new Predicate<Long>() {
                            @Override
                            public boolean test(Long aLong) throws Exception {
                                return aLong >= (3000 / PERIOD);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Function<Long, ObservableSource<MovieBaseModel>>() {
                            @Override
                            public ObservableSource<MovieBaseModel> apply(Long aLong) throws Exception {
                                return Observable.create(new ObservableOnSubscribe<MovieBaseModel>() {
                                    @Override
                                    public void subscribe(ObservableEmitter<MovieBaseModel> e) throws Exception {
                                        if (!e.isDisposed()){
                                            e.onNext(movieBaseModel);
                                        }
                                    }
                                });
                            }
                        });
            }
        })
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<MovieBaseModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(MovieBaseModel value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void initMoviesRecyclerView(){
        GridLayoutManager layoutManager = ((GridLayoutManager)recyclerViewMovie.getLayoutManager());
        if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            layoutManager.setSpanCount(3);
        else
            layoutManager.setSpanCount(6);

        recyclerViewMovie.setLayoutManager(layoutManager);
    }

    private void showGenresDialog(List<GenreModel> genreModels){

    }

    private void showSortDialog(){

    }

    public void loadGenres(List<GenreModel> genreModels){

    }

    public void addMoviesToList(List<MovieModel> movieModels){

    }

    public void shareMovie(Intent shareIntent){
        try {
           startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Log.e("fsd", ex.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }

    public int getScreenOrientation() {
        int rotation = getWindowManager().getDefaultDisplay()
                .getRotation();
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (rotation == Surface.ROTATION_0
                    || rotation == Surface.ROTATION_270) {
                return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            } else {
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
            }
        }
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (rotation == Surface.ROTATION_0
                    || rotation == Surface.ROTATION_90) {
                return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            } else {
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
            }
        }
        return ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }
}