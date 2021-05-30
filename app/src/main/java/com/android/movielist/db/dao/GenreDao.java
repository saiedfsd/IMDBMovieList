package com.android.movielist.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.rxjava3.RxRoom;

import com.android.movielist.db.entities.GenreEntity;

import io.reactivex.rxjava3.core.Observable;


@Dao
public interface GenreDao {

    @Query("SELECT * FROM genreentity")
    Observable<GenreEntity> loadAll();

    @Query("SELECT * FROM genreentity WHERE id IN (:genreId)")
    Observable<GenreEntity> getGenreById(int... genreId);

    @Insert
    void insertAll(GenreEntity... genres);

    @Delete
    void delete(GenreEntity genre);
}
