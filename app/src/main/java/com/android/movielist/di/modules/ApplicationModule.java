package com.android.movielist.di.modules;

import android.content.Context;

import com.android.movielist.App;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    public ApplicationModule(){
    }

    @Provides
    public Context provideContext(App application) {
        return application.getApplicationContext();
    }
}
