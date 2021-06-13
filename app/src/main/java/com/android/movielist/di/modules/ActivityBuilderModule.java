package com.android.movielist.di.modules;

import com.android.movielist.activities.MovieListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {MovieListModule.class,MovieListViewModule.class})
    abstract MovieListActivity bindMovieListActivity ();

}
