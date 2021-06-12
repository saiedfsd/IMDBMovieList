package com.android.movielist.di.modules;

import android.content.Context;

import androidx.room.Room;

import com.android.movielist.db.ImdbMoviesDB;
import com.android.movielist.db.dao.GenreDao;
import com.android.movielist.db.dao.ImagesDao;
import com.android.movielist.db.dao.MovieDao;
import com.android.movielist.db.dao.PosterDao;
import com.android.movielist.di.scops.AppScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {

    @AppScope
    @Provides
    public ImdbMoviesDB provideRoomDataBase(Context context){
        return Room
                .databaseBuilder(context.getApplicationContext(), ImdbMoviesDB.class, "imdb.db")
                .build();
    }

    @AppScope
    @Provides
    public GenreDao provideGenreDao(ImdbMoviesDB database){
        return database.genreDao();
    }

    @AppScope
    @Provides
    public ImagesDao provideImagesDao(ImdbMoviesDB database){
        return database.imagesDao();
    }

    @AppScope
    @Provides
    public MovieDao provideMovieDao(ImdbMoviesDB database){
        return database.movieDao();
    }

    @AppScope
    @Provides
    public PosterDao providePosterDao(ImdbMoviesDB database){
        return database.posterDao();
    }
}
