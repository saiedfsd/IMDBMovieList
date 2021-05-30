package com.android.movielist.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.movielist.db.dao.GenreDao;
import com.android.movielist.db.dao.ImagesDao;
import com.android.movielist.db.dao.MovieDao;
import com.android.movielist.db.dao.PosterDao;
import com.android.movielist.db.entities.GenreEntity;
import com.android.movielist.db.entities.ImagesEntity;
import com.android.movielist.db.entities.MovieEntity;
import com.android.movielist.db.entities.PosterEntity;

@Database(version = 1,entities = {GenreEntity.class, PosterEntity.class, ImagesEntity.class, MovieEntity.class})
public abstract class ImdbMoviesDB extends RoomDatabase {

    public abstract GenreDao genreDao();
    public abstract PosterDao posterDao();
    public abstract ImagesDao imagesDao();
    public abstract MovieDao movieDao();
}
