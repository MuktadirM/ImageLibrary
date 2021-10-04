package com.muktadir.imagelibrary.di.repository;

import com.muktadir.imagelibrary.repository.local.LocalDatabase;
import com.muktadir.imagelibrary.repository.local.dao.EditedImageDao;
import com.muktadir.imagelibrary.repository.local.dao.UnSaveImageDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDatabaseModule {

    @Singleton
    @Provides
    static EditedImageDao providesEditedImageDao(LocalDatabase localDatabase) {
        return localDatabase.getEditedImageDao();
    }

    @Singleton
    @Provides
    static UnSaveImageDao providesUnSaveImageDao(LocalDatabase localDatabase) {
        return localDatabase.getUnSaveImageDao();
    }

}
