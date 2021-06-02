package com.android.movielist.di.components;

import android.content.Context;

import com.android.movielist.App;
import com.android.movielist.activities.MovieListActivity;
import com.android.movielist.di.modules.ApplicationModule;
import com.android.movielist.di.modules.DataBaseModule;
import com.android.movielist.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ApplicationModule.class, DataBaseModule.class})
public interface AppComponent /*extends AndroidInjector<App> */{

    Context provideContext();
    void inject(App app);
    void injectActivity(MovieListActivity activity);

}
