package com.muktadir.imagelibrary.repository.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.muktadir.imagelibrary.repository.local.entities.EditedImageEntity;

import java.util.List;

@Dao
public interface EditedImageDao {
    @Insert
    public void insertOne(EditedImageEntity entity);

    @Delete
    public void deleteOne(EditedImageEntity entity);

    @Query("SELECT * FROM edited_images")
    public List<EditedImageEntity> getAll();

    @Query("SELECT * FROM edited_images WHERE id == :id")
    public EditedImageEntity findOne(int id);

    @Update
    public void updateOne(EditedImageEntity entity);
}
