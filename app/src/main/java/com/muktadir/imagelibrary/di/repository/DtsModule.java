package com.muktadir.imagelibrary.di.repository;

import com.muktadir.imagelibrary.repository.local.dts.EditedImageDts;
import com.muktadir.imagelibrary.repository.local.dts.UnSaveImageDts;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DtsModule {
    @Singleton
    @Provides
    static EditedImageDts provideEditedImageDts(){
        return new EditedImageDts();
    }

    @Singleton
    @Provides
    static UnSaveImageDts provideUnSaveImageDts(){
        return new UnSaveImageDts();
    }
}
