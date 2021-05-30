package com.android.movielist.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.movielist.db.entities.ImagesEntity;
import com.android.movielist.db.entities.PosterEntity;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface ImagesDao {

    @Query("SELECT * FROM imagesentity")
    Observable<ImagesEntity> loadAll();

    @Query("SELECT * FROM imagesentity WHERE id IN (:imageId)")
    Observable<ImagesEntity> getImageById(int... imageId);

    @Insert
    void insertAll(ImagesEntity... images);

    @Delete
    void delete(ImagesEntity image);
}
