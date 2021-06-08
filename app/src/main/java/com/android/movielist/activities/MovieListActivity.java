package com.android.movielist.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.movielist.App;
import com.android.movielist.presenters.IMovieListPresenter;
import com.android.movielist.views.MovieListView;
import com.android.movielist.R;
import com.android.movielist.adapters.GenreListAdapter;
import com.android.movielist.adapters.MoviesRecyclerAdapter;
import com.android.movielist.db.ImdbMoviesDB;
import com.android.movielist.interfaces.IMovieItemActionListener;
import com.android.movielist.webservice.apiservices.MovieApiService;
import com.android.movielist.webservice.responsemodels.GenreModel;
import com.android.movielist.webservice.responsemodels.MovieBaseModel;

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

public class MovieListActivity extends AppCompatActivity implements MovieListView {


    @Inject
    public IMovieListPresenter movieListPresenter;

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
    private GridLayoutManager movieRecyclerLayoutManager;

    private PublishSubject<MovieBaseModel> sharePublishSubject = PublishSubject.create();
    private PublishSubject<MovieBaseModel> favoritePublishSubject = PublishSubject.create();
    private PublishSubject<MovieBaseModel> movieItemPublishSubject = PublishSubject.create();

    private CompositeDisposable disposables = new CompositeDisposable();

    Observable<String> observableQueryText ;

    private IMovieItemActionListener movieItemActionListener = new IMovieItemActionListener() {
        @Override
        public void onShareMovieItem(MovieBaseModel movieModel) {
            sharePublishSubject.onNext(movieModel);
        }

        @Override
        public void onFavoriteMovieItem(MovieBaseModel movieModel, int position) {
            favoritePublishSubject.onNext(movieModel);
        }

        @Override
        public void onMovieItemClickListener(MovieBaseModel movieModel, int position) {
            movieItemPublishSubject.onNext(movieModel);
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

        favoritePublishSubject.switchMap(new Function<MovieBaseModel, ObservableSource<MovieBaseModel>>() {
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

        movieItemPublishSubject.switchMap(new Function<MovieBaseModel, ObservableSource<MovieBaseModel>>() {
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

        movieListPresenter.onCreate();
    }

    private void initMoviesRecyclerView(){
        if (movieRecyclerLayoutManager == null)
            movieRecyclerLayoutManager = new GridLayoutManager(this,
                    (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)?3:6);

        if (recyclerViewMovie.getLayoutManager() == null)
            recyclerViewMovie.setLayoutManager(movieRecyclerLayoutManager);
    }

    private void showGenresDialog(List<GenreModel> genreModels){
        AlertDialog genreDialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_genres)
                .setCancelable(true)
                .create();

        genreDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                TextView txtAllGenres = genreDialog.findViewById(R.id.txt_all_genres);
                RecyclerView recyclerGenres = genreDialog.findViewById(R.id.recycler_genres);
                ImageButton btnClose = genreDialog.findViewById(R.id.btn_close_dialog);
                GridLayoutManager genresRecyclerLayoutManager = new GridLayoutManager(MovieListActivity.this,2);;
                genresRecyclerLayoutManager.setSpanCount(2);
                recyclerGenres.setLayoutManager(genresRecyclerLayoutManager);
                GenreListAdapter listAdapter = new GenreListAdapter(genreDialog.getContext(), new GenreListAdapter.IGenreItemSelectionListener() {
                    @Override
                    public void onGenreItemSelect(GenreModel genreModel) {

                    }
                });
                recyclerGenres.setAdapter(listAdapter);
                listAdapter.loadGenres(genreModels);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        genreDialog.dismiss();
                    }
                });

                txtAllGenres.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        });

        genreDialog.show();
    }

    private void showSortDialog(){
        AlertDialog sortDialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_sort_filters)
                .setCancelable(true)
                .create();

        sortDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                TextView sortByMostPopular = sortDialog.findViewById(R.id.txt_most_popular);
                TextView sortByMostVote = sortDialog.findViewById(R.id.txt_most_vote);
                TextView sortByMostRate = sortDialog.findViewById(R.id.txt_most_rate);
                TextView sortByMostScore = sortDialog.findViewById(R.id.txt_meta_score);
                TextView sortByYear = sortDialog.findViewById(R.id.txt_year);
                TextView clearSort = sortDialog.findViewById(R.id.txt_clear_sort);

                sortByMostPopular.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                sortByMostVote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                sortByMostRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                sortByMostScore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                sortByYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                clearSort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });

        sortDialog.show();
    }

    public void shareMovie(Intent shareIntent){
        try {
           startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Log.e("fsd", ex.getMessage());
        }
    }

    @Override
    protected void onResume() {
        movieListPresenter.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        movieListPresenter.onDestroy();
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

    @Override
    public void addMoviesToList(List<MovieBaseModel> movieModels) {
        initMoviesRecyclerView();
        if (moviesRecyclerAdapter == null) {
            moviesRecyclerAdapter = new MoviesRecyclerAdapter(movieItemActionListener, this);
        }

        if (recyclerViewMovie.getAdapter() == null)
            recyclerViewMovie.setAdapter(moviesRecyclerAdapter);

        moviesRecyclerAdapter.loadMovies(movieModels);

    }

    @Override
    public void loadGenres(List<GenreModel> genreModels){
        showGenresDialog(genreModels);
    }

    @Override
    public void reloadMovieList(List<MovieBaseModel> movieModels) {
        initMoviesRecyclerView();
        if (moviesRecyclerAdapter == null){
            moviesRecyclerAdapter = new MoviesRecyclerAdapter(movieItemActionListener,this);
        }else
            moviesRecyclerAdapter.removeMovieItems();

        if (recyclerViewMovie.getAdapter() == null)
            recyclerViewMovie.setAdapter(moviesRecyclerAdapter);

        moviesRecyclerAdapter.loadMovies(movieModels);
    }
}