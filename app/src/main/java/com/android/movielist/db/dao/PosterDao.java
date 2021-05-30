package com.android.movielist.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.android.movielist.db.entities.PosterEntity;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface PosterDao {

    @Query("SELECT * FROM posterentity")
    Observable<PosterEntity> loadAll();

    @Query("SELECT * FROM posterentity WHERE id IN (:posterId)")
    Observable<PosterEntity> getPoster(int... posterId);

    @Insert
    void insertAll(PosterEntity... posters);

    @Delete
    void delete(PosterEntity poster);
}
