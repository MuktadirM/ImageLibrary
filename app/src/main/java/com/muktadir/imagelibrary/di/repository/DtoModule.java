package com.muktadir.imagelibrary.di.repository;

import com.muktadir.imagelibrary.repository.local.dto.EditedImageDto;
import com.muktadir.imagelibrary.repository.local.dto.UnSaveEditedImageDto;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DtoModule {
    @Singleton
    @Provides
    static EditedImageDto provideEditedImageDts(){
        return new EditedImageDto();
    }

    @Singleton
    @Provides
    static UnSaveEditedImageDto provideUnSaveEditedImageDts(){
        return new UnSaveEditedImageDto();
    }


}
