package com.android.movielist;

import android.app.Application;

import com.android.movielist.di.components.AppComponent;
import com.android.movielist.di.components.DaggerAppComponent;
import com.android.movielist.di.modules.ApplicationModule;

public class App extends Application{

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
