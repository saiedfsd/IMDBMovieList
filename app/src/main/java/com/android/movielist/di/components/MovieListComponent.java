package com.android.movielist.di.components;

import android.content.Context;

import com.android.movielist.App;
import com.android.movielist.activities.MovieListActivity;
import com.android.movielist.di.modules.ApplicationModule;
import com.android.movielist.di.modules.DataBaseModule;
import com.android.movielist.di.modules.MovieListModule;
import com.android.movielist.di.modules.NetworkModule;
import com.android.movielist.di.scops.AppScope;
import com.android.movielist.di.scops.MovieListActivityScope;

import javax.inject.Singleton;

import dagger.Component;

@MovieListActivityScope
@Component(dependencies = AppComponent.class, modules = {MovieListModule.class})
public interface MovieListComponent extends AppComponent {


  /*  @Component.Factory
    interface Factory {
        MovieListComponent create(AppComponent appComponent);
    }*/

    void injectActivity(MovieListActivity activity);
}
