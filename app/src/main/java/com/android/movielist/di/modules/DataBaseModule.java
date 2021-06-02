package com.android.movielist.di.modules;

import android.content.Context;

import androidx.room.Room;

import com.android.movielist.db.ImdbMoviesDB;
import com.android.movielist.db.dao.GenreDao;
import com.android.movielist.db.dao.ImagesDao;
import com.android.movielist.db.dao.MovieDao;
import com.android.movielist.db.dao.PosterDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {

    @Singleton
    @Provides
    public ImdbMoviesDB provideRoomDataBase(Context context){
        return Room
                .databaseBuilder(context.getApplicationContext(), ImdbMoviesDB.class, "imdb.db")
                .build();
    }

    @Singleton
    @Provides
    public GenreDao provideGenreDao(ImdbMoviesDB database){
        return database.genreDao();
    }

    @Singleton
    @Provides
    public ImagesDao provideImagesDao(ImdbMoviesDB database){
        return database.imagesDao();
    }

    @Singleton
    @Provides
    public MovieDao provideMovieDao(ImdbMoviesDB database){
        return database.movieDao();
    }

    @Singleton
    @Provides
    public PosterDao providePosterDao(ImdbMoviesDB database){
        return database.posterDao();
    }
}
