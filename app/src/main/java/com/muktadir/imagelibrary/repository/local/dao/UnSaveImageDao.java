package com.muktadir.imagelibrary.repository.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.muktadir.imagelibrary.repository.local.entities.EditedImageEntity;
import com.muktadir.imagelibrary.repository.local.entities.UnSaveImageEntity;

import java.util.List;

@Dao
public interface UnSaveImageDao {
    @Insert
    public void insertOne(UnSaveImageEntity entity);

    @Delete
    public void deleteOne(UnSaveImageEntity entity);

    @Query("SELECT * FROM un_save_images")
    public List<UnSaveImageEntity> getAll();

    @Query("SELECT * FROM un_save_images WHERE id == :id")
    public UnSaveImageEntity findOne(int id);

    @Update
    public void updateOne(UnSaveImageEntity entity);

    @Query("DELETE FROM un_save_images")
    public void deleteAll();
}
