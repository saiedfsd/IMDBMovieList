package com.android.movielist.di.components;

import android.content.Context;

import com.android.movielist.App;
import com.android.movielist.activities.MovieListActivity;
import com.android.movielist.db.dao.GenreDao;
import com.android.movielist.db.dao.ImagesDao;
import com.android.movielist.db.dao.MovieDao;
import com.android.movielist.db.dao.PosterDao;
import com.android.movielist.di.modules.ApplicationModule;
import com.android.movielist.di.modules.DataBaseModule;
import com.android.movielist.di.modules.NetworkModule;
import com.android.movielist.di.scops.AppScope;
import com.android.movielist.utilities.NetworkStateReceiver;
import com.android.movielist.webservice.apiservices.MovieApiService;

import javax.inject.Singleton;

import dagger.Component;

@AppScope
@Component(modules = {NetworkModule.class, ApplicationModule.class, DataBaseModule.class})
public interface AppComponent {

    Context provideContext();
    GenreDao provideGenreDao();
    MovieDao provideMovieDao();
    PosterDao providePosterDao();
    ImagesDao provideImagesDao();
    MovieApiService provideMovieApiService();
    NetworkStateReceiver provideNetWorkStateReceiver();


    void inject(App app);

}
