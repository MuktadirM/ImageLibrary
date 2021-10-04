package com.muktadir.imagelibrary.repository.local;

import static com.muktadir.imagelibrary.utils.Constrains.DB_VERSION;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.muktadir.imagelibrary.repository.local.dao.EditedImageDao;
import com.muktadir.imagelibrary.repository.local.dao.UnSaveImageDao;
import com.muktadir.imagelibrary.repository.local.entities.EditedImageEntity;
import com.muktadir.imagelibrary.repository.local.entities.UnSaveImageEntity;

@Database(entities = {EditedImageEntity.class, UnSaveImageEntity.class}, version = DB_VERSION,exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract EditedImageDao getEditedImageDao();
    public abstract UnSaveImageDao getUnSaveImageDao();
}
