package com.android.movielist.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    public Context context;

    public ApplicationModule(Context context){
        this.context = context;
    }

    @Provides
    public Context provideContext(){
        return context;
    }

    /*@ContributesAndroidInjector
    abstract MovieListActivity contributeActivityInjector();*/
}
