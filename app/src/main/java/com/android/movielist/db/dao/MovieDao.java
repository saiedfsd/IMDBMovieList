package com.android.movielist.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.android.movielist.db.entities.MovieEntity;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movieentity")
    Observable<MovieEntity> loadAll();

    @Query("SELECT * FROM movieentity WHERE id IN (:movieId)")
    Observable<MovieEntity> getMovieById(int... movieId);

    @Query("SELECT * FROM movieentity WHERE imdb_id IN (:movieImdbId)")
    Observable<MovieEntity> getMovieByImdbId(int... movieImdbId);

    @Insert
    void insertAll(MovieEntity... movies);

    @Delete
    void delete(MovieEntity movie);
}
