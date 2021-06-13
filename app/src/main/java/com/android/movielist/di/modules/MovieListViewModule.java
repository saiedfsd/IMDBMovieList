package com.android.movielist.di.modules;

import com.android.movielist.activities.MovieListActivity;
import com.android.movielist.mvpcontracts.MovieListActivityContract;
import com.android.movielist.views.MovieListView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MovieListViewModule {

    @Binds
    abstract MovieListActivityContract.View provideMovieListView(MovieListActivity movieListActivity);
}
